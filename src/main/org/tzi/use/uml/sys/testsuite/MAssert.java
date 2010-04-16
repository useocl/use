package org.tzi.use.uml.sys.testsuite;

import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.Value;

public class MAssert {
	private Expression expression;
	private String expressionString;
	
	private int line;
	
	public MAssert(int line, String expressionString, Expression exp) {
		this.expression = exp;
		this.line = line;
		this.expressionString = expressionString;
	}
	
	public String getExpressionString() {
		return expressionString;
	}
	
	public int getLine() {
		return this.line;
	}
	
	public boolean eval(EvalContext ctx) {
		Value v = this.expression.eval(ctx);
		
		if (v.isBoolean())
			return ((BooleanValue)v).value();
		else
			return false;
	}
}
