package org.tzi.use.uml.sys.testsuite;

import java.util.ArrayList;
import java.util.List;

import org.tzi.use.parser.SrcPos;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.mm.MPrePostCondition;
import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.uml.sys.MOperationCall;
import org.tzi.use.uml.sys.MSystemException;

public class MAssertPost extends MAssert {

	private MPrePostCondition singleCondition = null;
	
	public MAssertPost(SrcPos position, String expressionString,
			String message, boolean shouldBeValid) {
		super(position, expressionString, message, shouldBeValid);
		
	}

	public void setCondition(MPrePostCondition condition) {
		this.singleCondition = condition;
	}
	
	@Override
	protected boolean doEval(EvalContext ctx) throws MSystemException {
		MOperationCall opcall = ctx.postState().system().getLastOperationCall();
        if (opcall == null )
            throw new MSystemException("Call stack is empty.");

        MOperation op = opcall.getOperation();

        // bind result value to result variable
        VarBindings vb = opcall.getVarBindings();
        if (op.hasResultType() ) {
            vb.push("result", opcall.getResultValue());
        }

        // check postconditions
        boolean postOk = true;
        List<MPrePostCondition> postconds;
        
        if (this.singleCondition == null) {
        	postconds = op.postConditions();
        } else {
        	postconds = new ArrayList<MPrePostCondition>();
        	postconds.add(this.singleCondition);
        }

        for (MPrePostCondition ppc : postconds) {
            Expression expr = ppc.expression();
            Evaluator evaluator = new Evaluator();
            // evaluate in scope local to operation
            Value v = evaluator.eval(expr, opcall.getPreState(), ctx.postState(), vb, null);
            boolean ok = v.isDefined() && ((BooleanValue) v).isTrue();
            
            if (! ok ) {
                postOk = false;
            }
        }
        
        return postOk;
	}

}
