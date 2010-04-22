package org.tzi.use.parser.testsuite;

import org.antlr.runtime.Token;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.cmd.ASTCmd;
import org.tzi.use.uml.sys.MCmd;
import org.tzi.use.uml.sys.testsuite.MVariationEnd;

public class ASTVariationEnd extends ASTCmd {
	
	public ASTVariationEnd(Token start) {
		super(start);
	}

	@Override
	public MCmd gen(Context ctx) throws SemanticException {
		return new MVariationEnd(getPosition(), ctx.systemState().system());
	}
}
