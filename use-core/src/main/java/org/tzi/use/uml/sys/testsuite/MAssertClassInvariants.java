package org.tzi.use.uml.sys.testsuite;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.tzi.use.parser.SrcPos;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.util.NullWriter;

public class MAssertClassInvariants extends MAssert {

	private MClass cls;
	
	public MAssertClassInvariants(SrcPos position, String expressionString, String message, boolean shouldBeValid, MClass cls) {
		super(position, expressionString, message, shouldBeValid);
		this.cls = cls;
	}

	public MClass getMClass() {
		return cls;
	}
	
	@Override
	protected boolean doEval(EvalContext ctx) {
		List<String> invs = new ArrayList<String>();
	
		for (MClassInvariant inv : cls.model().allClassInvariants(cls)) {
			invs.add(inv.toString());
		}
		
		return  ctx.postState().check(new PrintWriter(new NullWriter()), 
				  false, 
				  false,
				  false, invs);
	}

}
