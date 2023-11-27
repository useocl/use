package org.tzi.use.uml.sys.testsuite;

import org.tzi.use.output.VoidUserOutput;
import org.tzi.use.parser.SrcPos;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.ocl.expr.EvalContext;

import java.util.ArrayList;
import java.util.List;

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
		
		return  ctx.postState().check(VoidUserOutput.getInstance(),
				  false, 
				  false,
				  false, invs);
	}

}
