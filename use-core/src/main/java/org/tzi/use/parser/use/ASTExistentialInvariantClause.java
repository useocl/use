package org.tzi.use.parser.use;

import java.util.List;

import org.antlr.runtime.Token;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MClassifier;
import org.tzi.use.uml.ocl.expr.ExpInvalidException;
import org.tzi.use.uml.ocl.expr.Expression;

public class ASTExistentialInvariantClause extends ASTInvariantClause {

	public ASTExistentialInvariantClause(Token name, ASTExpression e) {
		super(name, e);
	}

	@Override
	protected MClassInvariant onCreateMClassInvariant(Context ctx, MClassifier cf,
			List<String> varNames, Expression expr, String invName)
			throws ExpInvalidException {
        return ctx.modelFactory().createClassInvariant(invName, varNames, cf, expr, true);
	}
}
