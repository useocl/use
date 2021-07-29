package org.tzi.use.parser.testsuite;

import org.antlr.runtime.Token;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.sys.testsuite.MAssert;
import org.tzi.use.uml.sys.testsuite.MAssertGlobalInvariants;

public class ASTAssertGlobalInvariants extends ASTAssert {
 
	public ASTAssertGlobalInvariants(Token start, Token end, boolean shouldBeValid) {
		super(start, end, shouldBeValid);
	}

	@Override
	public MAssert gen(Context ctx) throws SemanticException {
		return new MAssertGlobalInvariants(getPosition(), getExpressionString(), getMessage(), shouldBeValid());
	}

}
