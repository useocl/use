package org.tzi.use.parser.testsuite;

import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.Token;
import org.tzi.use.parser.AST;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.sys.testsuite.MAssert;

public class ASTAssert extends AST {
	private ASTExpression expression;
	private String assertExpression;
	
	public ASTAssert(ASTExpression exp, Token end) {
		this.expression = exp;
		
		CommonToken startToken = (CommonToken)exp.getStartToken();
		CommonToken endToken = (CommonToken)end;
		
		CharStream inputStream = startToken.getInputStream();
				
		assertExpression = inputStream.substring(startToken.getStartIndex(), endToken.getStopIndex());
	}
	
	public int getLine() {
		return this.expression.getStartToken().getLine();
	}
	
	public MAssert gen(Context ctx) throws SemanticException {
		Expression exp = expression.gen(ctx);
		
		if (!exp.type().equals(TypeFactory.mkBoolean())) {
			throw new SemanticException(expression.getStartToken(), "Assert statement does not result in a boolean value!");
		}
		
		return new MAssert(expression.getStartToken().getLine(), assertExpression, exp);
	}
}
