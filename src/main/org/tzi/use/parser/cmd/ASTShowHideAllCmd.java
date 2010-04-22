package org.tzi.use.parser.cmd;

import org.antlr.runtime.Token;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.sys.MCmd;
import org.tzi.use.uml.sys.MCmdShowHideCrop;
import org.tzi.use.uml.sys.MCmdShowHideCrop.Mode;

public class ASTShowHideAllCmd extends ASTCmd {

	private Mode mode;
	
	public ASTShowHideAllCmd(Token start, Mode mode) {
		super(start);
		this.mode = mode;
	}
	
	@Override
	public MCmd gen(Context ctx) throws SemanticException {
		return new MCmdShowHideCrop(getPosition(), mode);
	}

	@Override
	public String toString() {
		return (this.mode == Mode.HIDE ? "hide" : "show") + " all";
	}
}
