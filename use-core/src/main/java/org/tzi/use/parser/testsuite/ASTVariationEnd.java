package org.tzi.use.parser.testsuite;

import org.antlr.runtime.Token;
import org.tzi.use.parser.Context;
import org.tzi.use.uml.sys.testsuite.MVariation;
import org.tzi.use.uml.sys.testsuite.MVariationEnd;

public class ASTVariationEnd extends ASTVariation {
		
	public ASTVariationEnd(Token start) {
		super(start);
	}

	@Override
	public MVariation gen(Context ctx) {
		return new MVariationEnd(position, ctx.systemState().system());
	}
}
