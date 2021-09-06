package org.tzi.use.uml.ocl.expr.operations;

import java.util.ArrayList;
import java.util.Collections;

import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.CollectionType;
import org.tzi.use.uml.ocl.type.OrderedSetType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.IntegerValue;
import org.tzi.use.uml.ocl.value.OrderedSetValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.util.StringUtil;

import com.google.common.collect.Multimap;

public class StandardOperationsOrderedSet {
	public static void registerTypeOperations(Multimap<String, OpGeneric> opmap) {
		// operations on OrderedSet
		OpGeneric.registerOperation(new Op_orderedSet_append(), opmap);
		OpGeneric.registerOperation(new Op_orderedSet_prepend(), opmap);
		OpGeneric.registerOperation(new Op_orderedSet_insertAt(), opmap);
		OpGeneric.registerOperation(new Op_orderedSet_subOrderedSet(), opmap);
		OpGeneric.registerOperation(new Op_orderedSet_at(), opmap);
		OpGeneric.registerOperation(new Op_orderedSet_indexOf(), opmap);
		OpGeneric.registerOperation(new Op_orderedSet_first(), opmap);
		OpGeneric.registerOperation(new Op_orderedSet_last(), opmap);
		OpGeneric.registerOperation(new Op_orderedSet_reverse(), opmap);
		
		// Not mentioned in OCL 2.2 specification
		OpGeneric.registerOperation(new Op_orderedSet_union(), opmap);		
		OpGeneric.registerOperation(new Op_orderedSet_including(), opmap);
		OpGeneric.registerOperation(new Op_orderedSet_excluding(), opmap);
	}
}

// --------------------------------------------------------
//
// OrderedSet operations.
//
// --------------------------------------------------------

/* union : OrderedSet(T1) x OrderedSet(T2) -> OrderedSet(T1), with T2 <= T1 */
final class Op_orderedSet_union extends OpGeneric {
	public String name() {
		return "union";
	}

	public int kind() {
		return OPERATION;
	}

	public boolean isInfixOrPrefix() {
		return false;
	}

	public Type matches(Type params[]) {
		if (params.length == 2 && params[0].isTypeOfOrderedSet()
				&& params[1].isTypeOfOrderedSet()) {
			OrderedSetType oset1 = (OrderedSetType) params[0];
			OrderedSetType oset2 = (OrderedSetType) params[1];

			Type commonElementType = oset1.elemType().getLeastCommonSupertype(
					oset2.elemType());

			if (commonElementType != null)
				return TypeFactory.mkOrderedSet(commonElementType);

		}
		return null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		OrderedSetValue oset1 = (OrderedSetValue) args[0];
		OrderedSetValue oset2 = (OrderedSetValue) args[1];
		return oset1.union(resultType, oset2);
	}
}

// --------------------------------------------------------

/* append : OrderedSet(T) x T -> OrderedSet(T) */
final class Op_orderedSet_append extends OpGeneric {
	public String name() {
		return "append";
	}

	public int kind() {
		return SPECIAL;
	}

	public boolean isInfixOrPrefix() {
		return false;
	}

	public Type matches(Type params[]) {
		if (params.length == 2 && params[0].isTypeOfOrderedSet()) {
			OrderedSetType osetType = (OrderedSetType) params[0];

			Type commonElementType = osetType.elemType()
					.getLeastCommonSupertype(params[1]);

			if (commonElementType != null)
				return TypeFactory.mkOrderedSet(commonElementType);
		}
		return null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		OrderedSetValue oset = (OrderedSetValue) args[0];
		return oset.append(resultType, args[1]);
	}
}

// --------------------------------------------------------

/* prepend : OrderedSet(T) x T -> OrderedSet(T) */
final class Op_orderedSet_prepend extends OpGeneric {
	public String name() {
		return "prepend";
	}

	public int kind() {
		return SPECIAL;
	}

	public boolean isInfixOrPrefix() {
		return false;
	}

	public Type matches(Type params[]) {
		if (params.length == 2 && params[0].isTypeOfOrderedSet()) {
			OrderedSetType osetType = (OrderedSetType) params[0];

			Type commonElementType = osetType.elemType()
					.getLeastCommonSupertype(params[1]);

			if (commonElementType != null)
				return TypeFactory.mkOrderedSet(commonElementType);
		}
		return null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		OrderedSetValue oset = (OrderedSetValue) args[0];
		return oset.prepend(resultType, args[1]);
	}
}

// --------------------------------------------------------

/* insertAt : OrderedSet(T) x Integer x T -> OrderedSet(T) */
final class Op_orderedSet_insertAt extends OpGeneric {
	public String name() {
		return "insertAt";
	}

	public int kind() {
		return OPERATION;
	}

	public boolean isInfixOrPrefix() {
		return false;
	}

	public Type matches(Type params[]) {
		if (params.length == 3 && params[0].isTypeOfOrderedSet()
				&& params[1].isTypeOfInteger()) {
			OrderedSetType osetType = (OrderedSetType) params[0];

			Type commonElementType = osetType.elemType()
					.getLeastCommonSupertype(params[2]);

			if (commonElementType != null)
				return TypeFactory.mkOrderedSet(commonElementType);
		}
		return null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		OrderedSetValue oset = (OrderedSetValue) args[0];
		OrderedSetValue res = oset.insertAt(resultType, (IntegerValue) args[1], args[2]);

		if (res == null)
			return UndefinedValue.instance;
		else
			return res;
	}
}

// --------------------------------------------------------

/* subOrderedSet : OrderedSet(T) x Integer x Integer -> OrderedSet(T) */
final class Op_orderedSet_subOrderedSet extends OpGeneric {
	public String name() {
		return "subOrderedSet";
	}

	public int kind() {
		return OPERATION;
	}

	public boolean isInfixOrPrefix() {
		return false;
	}

	public Type matches(Type params[]) {
		return (params.length == 3 && params[0].isTypeOfOrderedSet()
				&& params[1].isTypeOfInteger() && params[2].isTypeOfInteger()) ? params[0]
				: null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		OrderedSetValue seq = (OrderedSetValue) args[0];
		int lower = ((IntegerValue) args[1]).value();
		int upper = ((IntegerValue) args[2]).value();
		if (lower > upper)
			return UndefinedValue.instance;

		Value res = null;
		try {
			res = seq.subOrderedSet(resultType, lower - 1, upper);
		} catch (IndexOutOfBoundsException e) {
			res = UndefinedValue.instance;
		}
		return res;
	}
}

// --------------------------------------------------------

/* at : OrderedSet(T) x Integer -> T */
final class Op_orderedSet_at extends OpGeneric {
	public String name() {
		return "at";
	}

	public int kind() {
		return OPERATION;
	}

	public boolean isInfixOrPrefix() {
		return false;
	}

	public Type matches(Type params[]) {
		if (params.length == 2 && params[0].isTypeOfOrderedSet()
				&& params[1].isTypeOfInteger()) {
			OrderedSetType oset = (OrderedSetType) params[0];
			return oset.elemType();
		}
		return null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		OrderedSetValue oset = (OrderedSetValue) args[0];
		IntegerValue n = (IntegerValue) args[1];
		Value res = null;
		try {
			res = oset.get(n.value() - 1);
		} catch (IndexOutOfBoundsException e) {
			res = UndefinedValue.instance;
		}
		return res;
	}
}

// --------------------------------------------------------

/* first : OrderedSet(T) -> T */
final class Op_orderedSet_first extends OpGeneric {
	public String name() {
		return "first";
	}

	public int kind() {
		return OPERATION;
	}

	public boolean isInfixOrPrefix() {
		return false;
	}

	public Type matches(Type params[]) {
		if (params.length == 1 && params[0].isTypeOfOrderedSet()) {
			OrderedSetType oset = (OrderedSetType) params[0];
			return oset.elemType();
		}
		return null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		OrderedSetValue oset = (OrderedSetValue) args[0];
		if (oset.isEmpty())
			return UndefinedValue.instance;
		return oset.get(0);
	}
}

// --------------------------------------------------------

/* last : OrderedSet(T) -> T */
final class Op_orderedSet_last extends OpGeneric {
	public String name() {
		return "last";
	}

	public int kind() {
		return OPERATION;
	}

	public boolean isInfixOrPrefix() {
		return false;
	}

	public Type matches(Type params[]) {
		if (params.length == 1 && params[0].isTypeOfOrderedSet()) {
			OrderedSetType oset = (OrderedSetType) params[0];
			return oset.elemType();
		}
		return null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		OrderedSetValue oset = (OrderedSetValue) args[0];
		if (oset.isEmpty())
			return UndefinedValue.instance;
		return oset.get(oset.size() - 1);
	}
}

// --------------------------------------------------------

/* including : OrderedSet(T) x T -> OrderedSet(T) */
final class Op_orderedSet_including extends OpGeneric {
	public String name() {
		return "including";
	}

	public int kind() {
		return SPECIAL;
	}

	public boolean isInfixOrPrefix() {
		return false;
	}

	public Type matches(Type params[]) {
		if (params.length == 2 && params[0].isTypeOfOrderedSet()) {
			OrderedSetType osetType = (OrderedSetType) params[0];

			Type commonElementType = osetType.elemType()
					.getLeastCommonSupertype(params[1]);

			if (commonElementType != null)
				return TypeFactory.mkOrderedSet(commonElementType);

		}
		return null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		if (args[0].isUndefined())
			return UndefinedValue.instance;
		OrderedSetValue oset = (OrderedSetValue) args[0];
		return oset.append(resultType, args[1]);
	}
}

// --------------------------------------------------------

/* excluding : OrderedSet(T) x T -> OrderedSet(T) */
final class Op_orderedSet_excluding extends OpGeneric {
	public String name() {
		return "excluding";
	}

	public int kind() {
		return SPECIAL;
	}

	public boolean isInfixOrPrefix() {
		return false;
	}

	public Type matches(Type params[]) {
		if (params.length == 2 && params[0].isTypeOfOrderedSet()) {
			OrderedSetType osetType = (OrderedSetType) params[0];

			Type commonElementType = osetType.elemType()
					.getLeastCommonSupertype(params[1]);

			if (commonElementType != null)
				return TypeFactory.mkOrderedSet(commonElementType);
		}
		return null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		if (args[0].isUndefined())
			return UndefinedValue.instance;
		OrderedSetValue oset = (OrderedSetValue) args[0];
		return oset.excluding(resultType, args[1]);
	}
	
	@Override
	public String checkWarningUnrelatedTypes(Expression args[]) {
		CollectionType col = (CollectionType) args[0].type();
		
		Type commonElementType = col.elemType().getLeastCommonSupertype(args[1].type());
		
		if (!(col.elemType().isTypeOfOclAny() || args[1].type().isTypeOfOclAny()) && commonElementType.isTypeOfOclAny()) {
			return "Expression " + StringUtil.inQuotes(this.stringRep(args, "")) + 
					 " will always evaluate to the same ordered set, " + StringUtil.NEWLINE +
					 "because the element type " + StringUtil.inQuotes(col.elemType()) + 
					 " and the parameter type " + StringUtil.inQuotes(args[1].type()) + " are unrelated.";
		}
		
		return null;
	}
}

/* indexOf : OrderedSet(T) x T -> Integer */
final class Op_orderedSet_indexOf extends OpGeneric {
	public String name() {
		return "indexOf";
	}

	public int kind() {
		return SPECIAL;
	}

	public boolean isInfixOrPrefix() {
		return false;
	}

	public Type matches(Type params[]) {
		if (params.length == 2 && params[0].isTypeOfOrderedSet()) {
			OrderedSetType osetType = (OrderedSetType) params[0];

			Type commonElementType = osetType.elemType()
					.getLeastCommonSupertype(params[1]);

			if (commonElementType != null)
				return TypeFactory.mkInteger();

		}
		return null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		if (args[0].isUndefined())
			return UndefinedValue.instance;

		OrderedSetValue oset = (OrderedSetValue) args[0];

		int index = oset.indexOf(args[1]);
		if (index == -1)
			return UndefinedValue.instance;
		else
			return IntegerValue.valueOf(index + 1);
	}
	
	@Override
	public String checkWarningUnrelatedTypes(Expression args[]) {
		CollectionType col = (CollectionType) args[0].type();
		
		Type commonElementType = col.elemType().getLeastCommonSupertype(args[1].type());
		
		if (!(col.elemType().isTypeOfOclAny() || args[1].type().isTypeOfOclAny()) && commonElementType.isTypeOfOclAny()) {
			return "Expression " + StringUtil.inQuotes(this.stringRep(args, "")) + 
					" will always evaluate to undefined, " + StringUtil.NEWLINE +
					"because the element type " + StringUtil.inQuotes(col.elemType()) + 
					" and the parameter type " + StringUtil.inQuotes(args[1].type()) + " are unrelated.";
		}
		
		return null;
	}
}

/* reverse : OrderedSet(T) -> OrderedSet(T) */
final class Op_orderedSet_reverse extends OpGeneric {
	public String name() {
		return "reverse";
	}

	public int kind() {
		return SPECIAL;
	}

	public boolean isInfixOrPrefix() {
		return false;
	}

	public Type matches(Type params[]) {
		if (params.length == 1 && params[0].isTypeOfOrderedSet()) {
			return params[0];
		}
		return null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		if (args[0].isUndefined()) return UndefinedValue.instance;
			
		OrderedSetValue col = (OrderedSetValue)args[0];
		ArrayList<Value> elements = new ArrayList<Value>(col.collection());
		Collections.reverse(elements);
		
		return new OrderedSetValue(col.elemType(), elements);
	}
}