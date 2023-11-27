package org.tzi.use.uml.ocl.extension;

import com.ximpleware.NavException;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;
import org.tzi.use.output.HighlightAs;
import org.tzi.use.output.OutputLevel;
import org.tzi.use.output.UserOutput;
import org.tzi.use.parser.ocl.OCLCompiler;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.ModelFactory;
import org.tzi.use.uml.ocl.expr.ExpStdOp;
import org.tzi.use.uml.ocl.expr.operations.OpGeneric;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.util.StringUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExtensionManager {
	private static ExtensionManager INSTANCE = null;
	public static String EXTENSIONS_FOLDER = "oclextensions";
	
	/***
	 * An empty model is needed for retrieving build-in types
	 */
	private MModel emptyModel = null;
	private UserOutput output = null;
	private final List<OpGeneric> addedOperations = new ArrayList<>();

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
	public void loadExtensions(UserOutput out) {
		File extensionDir = new File(EXTENSIONS_FOLDER);
		if (!extensionDir.isDirectory()) {
			out.printWarn("Invalid extension directory ");
			out.printHighlightedWarn(extensionDir.toString(), HighlightAs.FILE);
			out.printlnWarn(".");
			return;
		}
		this.output = out;

		ModelFactory modelFactory = new ModelFactory();
		this.emptyModel = modelFactory.createModel("Extension Model");

		for (File f : extensionDir.listFiles((dir, name) -> name.toLowerCase().endsWith("xml"))) {
			out.printlnVerbose("Loading extensions from file '" + f.getName() + "'");
			loadExtensions(f, out);
		}

		// release helper
		this.emptyModel = null;

		File rubyLib = new File(EXTENSIONS_FOLDER + "/RubyMethodCall.lib");
	    StringBuilder contents = new StringBuilder();
	    
		try (BufferedReader input = new BufferedReader(new FileReader(rubyLib))) {
			String line;
			while ((line = input.readLine()) != null) {
				contents.append(line);
				contents.append(System.getProperty("line.separator"));
			}
		} catch (IOException ex) {
			this.output.printlnError("Loading of Ruby helper library %s/%s failed!"
					                  .formatted(EXTENSIONS_FOLDER, "RubyMethodCall.lib"));

			ex.printStackTrace(this.output.getPrintStream(OutputLevel.ERROR));
		}

	    rubyMethodCallLibrary = contents.toString();
	}
	
	private void loadExtensions(File f, UserOutput out) {
		
		VTDGen vg = new VTDGen();
		if (!vg.parseFile(f.getAbsolutePath(), false)) {
			out.printlnError("Could not parse extension file " + StringUtil.inQuotes(f.toString()));
			return;
		}
		
		VTDNav vn = vg.getNav();
		
		try {
			vn.toElement(VTDNav.ROOT, "*");
			if (!vn.toElement(VTDNav.FIRST_CHILD))
				return;
			
			while (true) {
				ExtensionOperation op = loadOperation(vn);
				ExpStdOp.addOperation(op);
				this.addedOperations.add(op);
				
				out.printlnTrace("Added extension operation '" + op.name() + "'");
				if (!vn.toElement(VTDNav.NEXT_SIBLING))
					break;
			}
		} catch (NavException e) {
			out.printlnError("Invalid extension file structure " + StringUtil.inQuotes(f.toString()));
		}
	}
	
	/***
	 * Unloads all previously loaded extensions
	 */
	public void unloadExtensions() {
		ExpStdOp.removeAllOperations(this.addedOperations);
		this.addedOperations.clear();
	}

	private ExtensionOperation loadOperation(VTDNav vn) throws NavException {
		String name, source, returnType;
		String body = "";
		int res;

		res = vn.getAttrVal("name");
		name = vn.toNormalizedString(res);
		
		res = vn.getAttrVal("source");
		source = vn.toNormalizedString(res);
		
		res = vn.getAttrVal("returnType");
		returnType = vn.toNormalizedString(res);
		
		vn.toElement(VTDNav.FIRST_CHILD, "body");
		res = vn.getText(); // get the index of the text (char data or CDATA)
        if (res != -1) {
        	body = vn.toNormalizedString(res);
        }
		vn.toElement(VTDNav.PARENT);
        
		ExtensionOperation op = new ExtensionOperation(source, name, returnType, body);
		
		if (vn.toElement(VTDNav.FIRST_CHILD, "parameter")) {
			if (vn.toElement(VTDNav.FIRST_CHILD, "par")) {
				String parName, parType;
				
				while(true) {
					res = vn.getAttrVal("name");
					parName = vn.toNormalizedString(res);

					res = vn.getAttrVal("type");
					parType = vn.toNormalizedString(res);
					
					op.addParameter(parName, parType);
					if (!vn.toElement(VTDNav.NEXT_SIBLING))
						break;
				}
				vn.toElement(VTDNav.PARENT);
			}
			
			vn.toElement(VTDNav.PARENT);
		}
		
		op.initialize();
		return op;
	}
	
	protected Type getType(String typeName) {
		return OCLCompiler.compileType(emptyModel, typeName, "Extension type", this.output);
	}
}
