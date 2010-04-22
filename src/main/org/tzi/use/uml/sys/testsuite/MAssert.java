package org.tzi.use.uml.sys.testsuite;

import org.tzi.use.parser.SrcPos;
import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.uml.sys.MSystemException;

public abstract class MAssert {
	private String expressionString;
	private String message;
	private SrcPos position;
	private boolean shouldBeValid;
	
	public MAssert(SrcPos position, String expressionString, String message, boolean shouldBeValid) {
		this.position = position;
		this.expressionString = expressionString;
		this.message = (message == null ? null : message.substring(1, message.length() - 1));
		this.shouldBeValid = shouldBeValid;
	}
	
	public String getExpressionString() {
		return expressionString;
	}
	
	public SrcPos getPosition() {
		return this.position;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public boolean shouldBeValid() {
		return this.shouldBeValid;
	}
	
	protected abstract boolean doEval(EvalContext ctx) throws MSystemException;
	
	public boolean eval(EvalContext ctx) throws MSystemException {
		return shouldBeValid == doEval(ctx);
	}
}
