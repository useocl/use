package org.tzi.use.uml.sys.testsuite;

import org.tzi.use.parser.SrcPos;
import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.Value;

public class MAssertOclExpression extends MAssert {
	private Expression expression;
	
	public MAssertOclExpression(SrcPos position, String expressionString, String message, boolean shouldBeValid, Expression expression) {
		super(position, expressionString, message, shouldBeValid);
		this.expression = expression;
	}
	
	protected boolean doEval(EvalContext ctx)
	{
		Value v = this.expression.eval(ctx);
		
		if (v.isBoolean())
			return ((BooleanValue)v).value();
		else
			return false;
	}
}
