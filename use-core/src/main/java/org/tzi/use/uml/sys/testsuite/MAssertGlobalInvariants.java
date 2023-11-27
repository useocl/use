package org.tzi.use.uml.sys.testsuite;

import org.tzi.use.output.VoidUserOutput;
import org.tzi.use.parser.SrcPos;
import org.tzi.use.uml.ocl.expr.EvalContext;

public class MAssertGlobalInvariants extends MAssert {

	public MAssertGlobalInvariants(SrcPos position, String expressionString,
			String message, boolean shouldBeValid) {
		super(position, expressionString, message, shouldBeValid);
	}

	@Override
	protected boolean doEval(EvalContext ctx) {
		return  ctx.postState().check(VoidUserOutput.getInstance(),
									  false, 
									  false,
									  true, null);
	}

}
