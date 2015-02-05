package org.tzi.use.parser.testsuite;

import org.antlr.runtime.Token;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.sys.testsuite.MAssert;
import org.tzi.use.uml.sys.testsuite.MAssertClassInvariants;

public class ASTAssertClassInvariants extends ASTAssert {

	private Token classname;
	
	public ASTAssertClassInvariants(Token start, Token end, boolean shouldBeValid, Token classname) {
		super(start, end, shouldBeValid);
		this.classname = classname;
	}

	@Override
	public MAssert gen(Context ctx) throws SemanticException {
		MClass cls = ctx.model().getClass(classname.getText());
		
		if (cls == null) {
			throw new SemanticException(classname, "Unknown class `" + classname.getText() + "'");
		}
		
		return new MAssertClassInvariants(getPosition(), getExpressionString(), getMessage(), shouldBeValid(), cls);
	}

}
