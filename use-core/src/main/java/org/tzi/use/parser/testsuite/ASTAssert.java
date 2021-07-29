package org.tzi.use.parser.testsuite;

import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.Token;
import org.tzi.use.parser.AST;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.SrcPos;
import org.tzi.use.uml.sys.testsuite.MAssert;

public abstract class ASTAssert extends AST {
	private Token start;
	private Token end;
	
	private SrcPos position;
	private String expressionString;
	private String message;
	
	private boolean shouldBeValid;
	
	public ASTAssert(Token start, Token end, boolean shouldBeValid) {
		this.position = new SrcPos(start);
		this.start = start;
		this.end = end;
		this.shouldBeValid = shouldBeValid;
		setExpressionString();
	}

	private void setExpressionString() {
		CommonToken startToken = (CommonToken)this.start;
		CommonToken endToken = (CommonToken)this.end;
		
		if (this.start != null && this.end != null) {
			CharStream inputStream = startToken.getInputStream();
			expressionString = inputStream.substring(startToken.getStartIndex(), endToken.getStopIndex());
		}
	}
	
	public boolean shouldBeValid() {
		return this.shouldBeValid;
	}
	
	public SrcPos getPosition() {
		return this.position;
	}
	
	public boolean hasMessage() {
		return this.message != null && !this.message.isEmpty();
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public void setMessage(Token msg) {
		this.message = (msg == null ? null : msg.getText());
	}
	
	public String getExpressionString() {
		return this.expressionString;
	}
	
	public void setEnd(Token end) {
		this.end = end;
		setExpressionString();
	}
	
	public abstract MAssert gen(Context ctx) throws SemanticException;
}
