package org.tzi.use.parser.testsuite;

import org.antlr.runtime.Token;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.sys.testsuite.MAssert;
import org.tzi.use.uml.sys.testsuite.MAssertOclExpression;

public class ASTAssertOclExpression extends ASTAssert {
	private ASTExpression expression;
	
	public ASTAssertOclExpression(Token start, Token end, boolean shouldBeValid, ASTExpression exp) {
		super(start, end, shouldBeValid);
		this.expression = exp;
	}
	
	public MAssert gen(Context ctx) throws SemanticException {
		Expression exp = expression.gen(ctx);
		
		if (!exp.type().equals(TypeFactory.mkBoolean())) {
			throw new SemanticException(expression.getStartToken(), "Assert statement does not result in a boolean value!");
		}
		
		return new MAssertOclExpression(getPosition(), getExpressionString(), getMessage(), shouldBeValid(), exp);
	}
}
