package org.tzi.use.parser.cmd;

import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.sys.MCmd;
import org.tzi.use.uml.sys.MShowHideCropCmd;
import org.tzi.use.uml.sys.MShowHideCropCmd.Mode;

public class ASTShowHideAllCmd extends ASTCmd {

	private Mode mode;
	
	public ASTShowHideAllCmd(Mode mode) {
		this.mode = mode;
	}
	
	@Override
	public MCmd gen(Context ctx) throws SemanticException {
		return new MShowHideCropCmd(mode);
	}

}
