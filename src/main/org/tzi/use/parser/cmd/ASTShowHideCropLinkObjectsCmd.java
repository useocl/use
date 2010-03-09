package org.tzi.use.parser.cmd;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.antlr.runtime.Token;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.sys.MCmd;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MShowHideCropCmd;
import org.tzi.use.uml.sys.MShowHideCropCmd.Mode;

public class ASTShowHideCropLinkObjectsCmd extends ASTCmd {

	private Mode mode;
	private Token associationName;
	private List<Token> objectNames;
		
	public ASTShowHideCropLinkObjectsCmd(Mode mode, Token associationName, List<Token> objectNames) {
		this.associationName = associationName;
		this.objectNames = objectNames;
		this.mode = mode;
	}
	
	@Override
	public MCmd gen(Context ctx) throws SemanticException {
		// Check if association exists
		MAssociation ass = ctx.model().getAssociation(associationName.getText());
		if (ass == null) {
			throw new SemanticException(associationName, "Association `" + associationName.getText() + "' does not exist!");
		}

		// Check if link with given objects exist
		List<MObject> objects = new ArrayList<MObject>(objectNames.size());
		MObject obj;
		
		for (Token oName : objectNames) {
			 obj = ctx.systemState().objectByName(oName.getText());
			 
			 if (obj == null) {
				 throw new SemanticException(oName, "Object `" + oName.getText() + "' does not exist!");
			 }
			 
			 objects.add(obj);
		}
		
		if (ctx.systemState().linkBetweenObjects(ass, new HashSet<MObject>(objects)) == null) {
			throw new SemanticException(associationName, "The specified link does not exist");
		}
		
		return new MShowHideCropCmd(mode, ass, objects);
	}
}
