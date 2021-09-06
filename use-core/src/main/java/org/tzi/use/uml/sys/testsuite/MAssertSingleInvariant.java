package org.tzi.use.uml.sys.testsuite;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.tzi.use.parser.SrcPos;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.util.NullWriter;

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
		
		return  ctx.postState().check(new PrintWriter(new NullWriter()), 
				  false, 
				  false,
				  false, invs);
	}

}
