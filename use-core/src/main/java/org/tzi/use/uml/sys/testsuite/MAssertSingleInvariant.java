package org.tzi.use.uml.sys.testsuite;

import org.tzi.use.output.VoidUserOutput;
import org.tzi.use.parser.SrcPos;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.ocl.expr.EvalContext;

import java.util.ArrayList;
import java.util.List;

public class MAssertSingleInvariant extends MAssert {

	private MClassInvariant invariant;
	
	public MAssertSingleInvariant(SrcPos position, String expressionString, String message, boolean shouldBeValid, MClassInvariant inv) {
		super(position, expressionString, message, shouldBeValid);
		this.invariant = inv;
	}

	@Override
	protected boolean doEval(EvalContext ctx) {
		
		List<String> invs = new ArrayList<String>();
		invs.add(invariant.toString());
		
		return  ctx.postState().check(VoidUserOutput.getInstance(),
				  false, 
				  false,
				  false, invs);
	}

}
