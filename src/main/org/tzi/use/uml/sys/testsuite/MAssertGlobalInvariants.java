package org.tzi.use.uml.sys.testsuite;

import java.io.PrintWriter;

import org.tzi.use.parser.SrcPos;
import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.util.NullWriter;

public class MAssertGlobalInvariants extends MAssert {

	public MAssertGlobalInvariants(SrcPos position, String expressionString,
			String message, boolean shouldBeValid) {
		super(position, expressionString, message, shouldBeValid);
	}

	@Override
	protected boolean doEval(EvalContext ctx) {
		return  ctx.postState().check(new PrintWriter(new NullWriter()), 
									  false, 
									  false,
									  true, null);
	}

}
