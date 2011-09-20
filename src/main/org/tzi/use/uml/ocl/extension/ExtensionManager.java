package org.tzi.use.uml.ocl.extension;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.tzi.use.parser.ocl.OCLCompiler;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.ModelFactory;
import org.tzi.use.uml.ocl.expr.ExpStdOp;
import org.tzi.use.uml.ocl.expr.operations.OpGeneric;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.util.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;

public class ExtensionManager {
	private static ExtensionManager INSTANCE = null;
	public static String EXTENSIONS_FOLDER = "oclextensions";
	
	/***
	 * An empty model is needed for retrieving buildin types
	 */
	private MModel emptyModel = null;
	private PrintWriter error = null;
	private List<OpGeneric> addedOperations = new ArrayList<OpGeneric>();
	
	/**
	 * The required header for ruby method calls.
	 * Provides extensions for accessing USE objects
	 */
	private String rubyMethodCallLibrary = null;
	
	private ExtensionManager() {}
	
	public static ExtensionManager getInstance() {
		if (ExtensionManager.INSTANCE == null) {
			ExtensionManager.INSTANCE = new ExtensionManager();
		}
		
		return ExtensionManager.INSTANCE;
	}
	
	public String getRubyMethodCallLibrary() {
		return rubyMethodCallLibrary;
	}
	
	/***
	 * Loads the extensions specified in EXTENSION_FOLDER
	 */
	public void loadExtensions() {
		File extensionDir = new File(EXTENSIONS_FOLDER);
		if (!extensionDir.isDirectory()) {
			Log.warn("Invalid extension directory '" + ExtensionManager.EXTENSIONS_FOLDER + "'");
			return;
		}
		
		// initialize helper
		this.error = new PrintWriter(System.err);
		ModelFactory modelFactory = new ModelFactory();
		this.emptyModel = modelFactory.createModel("Extension Model");
		
		FilenameFilter xmlFiles = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith("xml");
			}
		};
		
		for (File f : extensionDir.listFiles(xmlFiles)) {
			Log.debug("Loading extensions from file '" + f.getName() + "'");
			loadExtensions(f);
		}

		// release helper
		this.error = null;
		this.emptyModel = null;
		
		File rubyLib = new File(EXTENSIONS_FOLDER + "/RubyMethodCall.lib");
	    StringBuilder contents = new StringBuilder();
	    
	    try {
	      BufferedReader input =  new BufferedReader(new FileReader(rubyLib));
	      try {
	        String line = null;
	        while (( line = input.readLine()) != null) {
	          contents.append(line);
	          contents.append(System.getProperty("line.separator"));
	        }
	      }
	      finally {
	        input.close();
	      }
	    }
	    catch (IOException ex){
	      ex.printStackTrace();
	    }
	    
	    rubyMethodCallLibrary = contents.toString();
	}
	
	private void loadExtensions(File f) {
		DOMParser p = new DOMParser();
		
		try {
			p.parse(f.getAbsolutePath());
		} catch (SAXException e) {
			Log.error("Could not parse extension files. " + e.getMessage());
			return;
		} catch (IOException e) {
			Log.error("Could not parse extension files. " + e.getMessage());
			return;
		}
		
		Document doc = p.getDocument();
		Element root = doc.getDocumentElement();
		
		NodeList operations = root.getElementsByTagName("operation");
		
		for (int index = 0; index < operations.getLength(); index++) {
			ExtensionOperation op = loadOperation((Element)operations.item(index));
			
			ExpStdOp.addOperation(op);
			this.addedOperations.add(op);
			
			Log.debug("Added extension operation '" + op.name() + "'");
		}
	}
	
	/***
	 * Unloads all previously loaded extensions
	 */
	public void unloadExtensions() {
		ExpStdOp.removeAllOperations(this.addedOperations);
		this.addedOperations.clear();
	}

	private ExtensionOperation loadOperation(Element opNode) {
		String name, source, returnType, body;
		
		name = opNode.getAttributes().getNamedItem("name").getNodeValue();
		source = opNode.getAttributes().getNamedItem("source").getNodeValue();
		returnType = opNode.getAttributes().getNamedItem("returnType").getNodeValue();
		body = opNode.getElementsByTagName("body").item(0).getFirstChild().getNodeValue();
		
		ExtensionOperation op = new ExtensionOperation(source, name, returnType, body);
		
		if (opNode.getElementsByTagName("parameter").getLength() > 0) {
			Element parameter = (Element)opNode.getElementsByTagName("parameter").item(0);
			NodeList parameterList = parameter.getElementsByTagName("par");
			
			for (int i = 0; i < parameterList.getLength(); i++) {
				String parName, parType;
				parName = parameterList.item(i).getAttributes().getNamedItem("name").getNodeValue();
				parType = parameterList.item(i).getAttributes().getNamedItem("type").getNodeValue();
				
				op.addParameter(parName, parType);
			}
		}
		
		op.initialize();
		return op;
	}
	
	protected Type getType(String typeName) {
		return OCLCompiler.compileType(emptyModel, typeName, "Extension type", error);
	}
}
