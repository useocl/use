package org.tzi.use.uml.ocl.expr.operations;

import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.value.Value;

/**
 * This class is the base class for boolean operations. Boolean operations need
 * special treatment of undefined arguments. Also, short-circuit evaluation may
 * be used to speed up the evaluation process.
 */
public abstract class BooleanOperation extends OpGeneric {
	public int kind() {
		return SPECIAL;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		throw new RuntimeException("Use evalWithArgs");
	}

	public boolean isBooleanOperation() {
    	return true;
    }
	
	public abstract Value evalWithArgs(EvalContext ctx, Expression args[]);
}
