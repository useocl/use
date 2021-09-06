package org.tzi.use.parser.testsuite;

import org.antlr.runtime.Token;
import org.tzi.use.parser.Context;
import org.tzi.use.uml.sys.testsuite.MVariation;
import org.tzi.use.uml.sys.testsuite.MVariationStart;

public class ASTVariationStart extends ASTVariation {	
	public ASTVariationStart(Token start) {
		super(start);
	}

	@Override
	public MVariation gen(Context ctx) {
		return new MVariationStart(getPosition(), ctx.systemState().system());
	}
}
