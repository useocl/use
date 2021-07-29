package org.tzi.use.uml.ocl.expr.operations;

import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.util.StringUtil;

import com.google.common.collect.Multimap;

public class StandardOperationsAny {
	public static void registerTypeOperations(Multimap<String, OpGeneric> opmap) {
		// generic operations on all types
		OpGeneric.registerOperation(new Op_equal(), opmap);
		OpGeneric.registerOperation(new Op_notequal(), opmap);
		OpGeneric.registerOperation(new Op_isDefined(), opmap);
		OpGeneric op = new Op_isUndefined();
		OpGeneric.registerOperation(op, opmap);
		OpGeneric.registerOperation("oclIsUndefined", op, opmap);
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
		if (params.length == 2 && params[0].getLeastCommonSupertype(params[1]) != null)
			return TypeFactory.mkBoolean();
		else
			return null;
	}

	@Override
	public String checkWarningUnrelatedTypes(Expression args[]) {
		Type lcst = args[0].type().getLeastCommonSupertype(args[1].type());
		
		if ((!(args[0].type().isTypeOfOclAny() || args[1].type().isTypeOfOclAny()) && lcst.isTypeOfOclAny()) ||
				(!(args[0].type().isTypeOfCollection() || args[1].type().isTypeOfCollection()) && lcst.isTypeOfCollection())) {
			return "Expression " + StringUtil.inQuotes(this.stringRep(args, "")) + 
					 " can never evaluate to true because " + StringUtil.inQuotes(args[0].type()) + 
					 " and " + StringUtil.inQuotes(args[1].type()) + " are unrelated.";
		}
		
		return null;
	}
	
	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		boolean res;

		if (args[0].isUndefined())
			return BooleanValue.get(args[1].isUndefined());
		
		if (args[1].type().conformsTo(args[0].type()))
			res = args[0].equals(args[1]);
		else if (args[0].type().conformsTo(args[1].type()))
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
	
	@Override
	public String checkWarningUnrelatedTypes(Expression args[]) {
		Type lcst = args[0].type().getLeastCommonSupertype(args[1].type());
		
		if ((!(args[0].type().isTypeOfOclAny() || args[1].type().isTypeOfOclAny()) && lcst.isTypeOfOclAny()) ||
				(!(args[0].type().isTypeOfCollection() || args[1].type().isTypeOfCollection()) && lcst.isTypeOfCollection())) {
			return "Expression " + StringUtil.inQuotes(this.stringRep(args, "")) + 
					 " can never evaluate to false because " + StringUtil.inQuotes(args[0].type()) + 
					 " and " + StringUtil.inQuotes(args[1].type()) + " are unrelated.";
		}
		
		return null;
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
	
	@Override
	public String checkWarningUnrelatedTypes(Expression args[]) {
		if (args[0].type().isTypeOfVoidType()) {
			return "Expression " + StringUtil.inQuotes(this.stringRep(args, "")) + 
					 " can never evaluate to true because " + StringUtil.inQuotes(args[0].type()) + 
					 " is always undefined";
		}
		
		return null;
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
	
	@Override
	public String checkWarningUnrelatedTypes(Expression args[]) {
		if (args[0].type().isTypeOfVoidType()) {
			return "Expression " + StringUtil.inQuotes(this.stringRep(args, "")) + 
					 " can never evaluate to false because " + StringUtil.inQuotes(args[0].type()) + 
					 " is always undefined";
		}
		
		return null;
	}
}
