package org.tzi.use.uml.ocl.expr.operations;

import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.util.MultiMap;

public class StandardOperationsAny {
	public static void registerTypeOperations(MultiMap<String, OpGeneric> opmap) {
		// generic operations on all types
		OpGeneric.registerOperation(new Op_equal(), opmap);
		OpGeneric.registerOperation(new Op_notequal(), opmap);
		OpGeneric.registerOperation(new Op_isDefined(), opmap);
		OpGeneric.registerOperation(new Op_isUndefined(), opmap);
	}
}

// --------------------------------------------------------
//
// Generic operations on all types.
//
// --------------------------------------------------------

/* = : T1 x T2 -> Boolean, with T2 <= T1 or T1 <= T2 */
final class Op_equal extends OpGeneric {
	public String name() {
		return "=";
	}

	public int kind() {
		return SPECIAL;
	}

	public boolean isInfixOrPrefix() {
		return true;
	}

	public Type matches(Type params[]) {
		if (params.length == 2
				&& params[0].getLeastCommonSupertype(params[1]) != null)
			return TypeFactory.mkBoolean();
		else
			return null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		boolean res;

		if (args[0].isUndefined())
			return BooleanValue.get(args[1].isUndefined());
		
		if (args[1].type().isSubtypeOf(args[0].type()))
			res = args[0].equals(args[1]);
		else if (args[0].type().isSubtypeOf(args[1].type()))
			res = args[1].equals(args[0]);
		else
			res = false;

		return BooleanValue.get(res);
	}
}

// --------------------------------------------------------

/* <> : T1 x T2 -> Boolean, with T2 <= T1 or T1 <= T2 */
final class Op_notequal extends OpGeneric {
	public String name() {
		return "<>";
	}

	public int kind() {
		return SPECIAL;
	}

	public boolean isInfixOrPrefix() {
		return true;
	}

	public Type matches(Type params[]) {
		if (params.length == 2 && params[0].getLeastCommonSupertype(params[1]) != null)
			return TypeFactory.mkBoolean();
		else
			return null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		if (args[0].isUndefined())
			return BooleanValue.get(!args[1].isUndefined());
		
		boolean res = !args[0].equals(args[1]);
		return BooleanValue.get(res);
	}
}

// --------------------------------------------------------

/* isDefined : T -> Boolean */
final class Op_isDefined extends OpGeneric {
	public String name() {
		return "isDefined";
	}

	public int kind() {
		return SPECIAL;
	}

	public boolean isInfixOrPrefix() {
		return false;
	}

	public Type matches(Type params[]) {
		return (params.length == 1) ? TypeFactory.mkBoolean() : null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		boolean res = !args[0].isUndefined();
		return BooleanValue.get(res);
	}
}

// --------------------------------------------------------

/* isUndefined : T -> Boolean */
final class Op_isUndefined extends OpGeneric {
	public String name() {
		return "isUndefined";
	}

	public int kind() {
		return SPECIAL;
	}

	public boolean isInfixOrPrefix() {
		return false;
	}

	public Type matches(Type params[]) {
		return (params.length == 1) ? TypeFactory.mkBoolean() : null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		boolean res = args[0].isUndefined();
		return BooleanValue.get(res);
	}
}
