package org.tzi.use.parser.testsuite;

import org.antlr.runtime.Token;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.sys.testsuite.MAssert;
import org.tzi.use.uml.sys.testsuite.MAssertSingleInvariant;

public class ASTAssertSingleInvariant extends ASTAssert {

	private Token className;
	private Token invName;
	
	public ASTAssertSingleInvariant(Token start, Token end, boolean shouldBeValid, Token className, Token invName) {
		super(start, end, shouldBeValid);
		this.className = className;
		this.invName = invName;
	}

	@Override
	public MAssert gen(Context ctx) throws SemanticException {
		MClass cls = ctx.model().getClass(className.getText());
		
		if (cls == null) {
			throw new SemanticException(className, "Unknown class `" + className.getText() + "'");
		}
		
		String invariantName = className.getText() + "::" + invName.getText();
		MClassInvariant inv = ctx.model().getClassInvariant(invariantName);
		
		if (inv == null) {
			throw new SemanticException(className, "Unknown class invariant `" + invariantName + "'");
		}
		
		return new MAssertSingleInvariant(getPosition(), getExpressionString(), getMessage(), shouldBeValid(), inv);
	}
}
