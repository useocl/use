package org.tzi.use.parser.cmd;

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.Token;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.sys.MCmd;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MShowHideCropCmd;
import org.tzi.use.uml.sys.MShowHideCropCmd.Mode;

public class ASTShowHideCropObjectsCmd extends ASTCmd {

	private List<Token> objectNames;
	private Token className;
	private Mode mode;
	
	public ASTShowHideCropObjectsCmd(Mode mode, List<Token> objectNames, Token className) {
		this.objectNames = objectNames;
		this.mode = mode;
		this.className = className;
	}
	
	@Override
	public MCmd gen(Context ctx) throws SemanticException {
		// Check if link with given objects exist
		MClass cls = null;
		if (className != null) {
			cls = ctx.model().getClass(className.getText());
			
			if (cls == null) {
				throw new SemanticException(className, "Class `" + className.getText() + "' does not exist!");
			}
		}

		List<MObject> objects = new ArrayList<MObject>(objectNames.size());
		MObject obj;
		
		for (Token oName : objectNames) {
			 obj = ctx.systemState().objectByName(oName.getText());
			 
			 if (obj == null) {
				 throw new SemanticException(oName, "Object `" + oName.getText() + "' does not exist!");
			 } else if (cls != null && !obj.cls().equals(cls)) {
				 throw new SemanticException(oName, "The class of the object `" + oName.getText() + "' is not `" + cls.name() + "'!");
			 }
			 
			 objects.add(obj);
		}
		
		return new MShowHideCropCmd(mode, objects);
	}

}
