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
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.util.soil.VariableEnvironment;

public class MAssertPre extends MAssert {

	private Expression objectExpr;
	private MOperation operation;
	private Expression[] args;
	private MPrePostCondition singleCondition;
	
	public MAssertPre(SrcPos position, String expressionString, String message,
					 boolean shouldBeValid, Expression objectExpr, MOperation operation, Expression[] args) {
		super(position, expressionString, message, shouldBeValid);
		
		this.objectExpr = objectExpr;
		this.operation = operation;
		this.args = args;
	}

	public void setCondition(MPrePostCondition cnd) {
		this.singleCondition = cnd;
	}
	
	@Override
	protected boolean doEval(EvalContext ctx) {
		// check preconditions
        boolean preOk = true;
        MOperation op = this.operation;
        List<MPrePostCondition> preconds;
        
        if (this.singleCondition == null) { 
        	preconds = op.preConditions();
        } else {
        	preconds = new ArrayList<MPrePostCondition>();
        	preconds.add(this.singleCondition);
        }
        
		VariableEnvironment e = ctx.preState().system().getVariableEnvironment();
		e.pushFrame(false);
		ObjectValue self = (ObjectValue)objectExpr.eval(ctx);
        e.assign("self", self);
        Value[] argsValues = new Value[args.length];
        for (int i = 0; i < args.length; ++i) {
        	argsValues[i] = args[i].eval(ctx);
			e.assign(this.operation.paramNames().get(i), argsValues[i]);
        }
        VarBindings b = e.constructVarBindings();
        
        for (MPrePostCondition ppc : preconds) {
            Expression expr = ppc.expression();
            Evaluator evaluator = new Evaluator();
            // evaluate in scope local to operation
            Value v = evaluator.eval(expr, ctx.postState(), b);
            boolean ok = v.isDefined() && ((BooleanValue) v).isTrue();
            
            if (! ok )
                preOk = false;
        }
        e.popFrame();
        return preOk;
	}

}
