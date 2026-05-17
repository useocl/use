package org.tzi.use.uml.sys.testsuite;

import org.tzi.use.uml.sys.MSystemState;

import java.io.PrintWriter;

import org.tzi.use.util.SrcPos;
import org.tzi.use.uml.mm.expr.EvalContext;
import org.tzi.use.util.NullWriter;

public class MAssertGlobalInvariants extends MAssert {

	public MAssertGlobalInvariants(SrcPos position, String expressionString,
			String message, boolean shouldBeValid) {
		super(position, expressionString, message, shouldBeValid);
	}

	@Override
	protected boolean doEval(EvalContext ctx) {
		return  ((MSystemState) ctx.postState()).check(new PrintWriter(new NullWriter()), 
									  false, 
									  false,
									  true, null);
	}

}
