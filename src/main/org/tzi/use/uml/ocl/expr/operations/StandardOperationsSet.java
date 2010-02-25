package org.tzi.use.uml.ocl.expr.operations;

import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.BagType;
import org.tzi.use.uml.ocl.type.SetType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.BagValue;
import org.tzi.use.uml.ocl.value.IntegerValue;
import org.tzi.use.uml.ocl.value.SetValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.util.MultiMap;
import org.tzi.use.util.StringUtil;

public class StandardOperationsSet {
	public static void registerTypeOperations(MultiMap<String, OpGeneric> opmap) {
		// operations on Set
		OpGeneric.registerOperation(new Op_set_union(), opmap);
		OpGeneric.registerOperation(new Op_set_union_bag(), opmap);
		OpGeneric.registerOperation(new Op_set_intersection(), opmap);
		OpGeneric.registerOperation(new Op_set_intersection_bag(), opmap);
		OpGeneric.registerOperation(new Op_set_difference(), opmap);
		OpGeneric.registerOperation(new Op_set_including(), opmap);
		OpGeneric.registerOperation(new Op_set_excluding(), opmap);
		OpGeneric.registerOperation(new Op_set_symmetricDifference(), opmap);
		// the following three are special expressions:
		// select
		// reject
		// collect
		// count: inherited from Collection		
		// Constructors
		OpGeneric.registerOperation(new Op_mkSet(), opmap);
		OpGeneric.registerOperation(new Op_mkSetRange(), opmap);
	}
}

//--------------------------------------------------------
//
// Set constructors.
//
// --------------------------------------------------------
/* mkSet : T x T x ... x T -> Set(T) */
final class Op_mkSet extends OpGeneric {
	public String name() {
		return "mkSet";
	}

	// may include undefined elements
	public int kind() {
		return SPECIAL;
	}

	public boolean isInfixOrPrefix() {
		return false;
	}

	public Type matches(Type params[]) {
		if (params.length > 0) {
			final Type elemType = params[0];

			// all arguments of set constructor must have equal type
			// FIXME: relax to common base type?
			for (int i = 1; i < params.length; i++)
				if (!params[i].equals(elemType))
					return null;
			return TypeFactory.mkSet(elemType);
		}
		return null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		return new SetValue(args[0].type(), args);
	}

	public String stringRep(Expression args[], String atPre) {
		return "Set{" + StringUtil.fmtSeq(args, ",") + "}";
	}
}

// --------------------------------------------------------

/* mkSetRange : Integer x Integer, ... -> Set(Integer) */
final class Op_mkSetRange extends OpGeneric {
	public String name() {
		return "mkSetRange";
	}

	public int kind() {
		return OPERATION;
	}

	public boolean isInfixOrPrefix() {
		return false;
	}

	public Type matches(Type params[]) {
		return (params.length >= 2 && params.length % 2 == 0
				&& params[0].isInteger() && params[1].isInteger()) ? TypeFactory
				.mkSet(TypeFactory.mkInteger())
				: null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		int[] ranges = new int[args.length];
		for (int i = 0; i < args.length; i++)
			ranges[i] = ((IntegerValue) args[i]).value();

		return new SetValue(TypeFactory.mkInteger(), ranges);
	}

	public String stringRep(Expression args[], String atPre) {
		if (args.length % 2 != 0)
			throw new IllegalArgumentException("length=" + args.length);
		String s = "Set{";
		for (int i = 0; i < args.length; i += 2) {
			if (i > 0)
				s += ",";
			s += args[i] + ".." + args[i + 1];
		}
		s += "}";
		return s;
	}
}

// --------------------------------------------------------
//
// Set operations.
//
// --------------------------------------------------------

/* union : Set(T1) x Set(T2) -> Set(T1), with T2 <= T1 */
final class Op_set_union extends OpGeneric {
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
		if (params.length == 2 && params[0].isSet() && params[1].isSet()) {
			SetType set1 = (SetType) params[0];
			SetType set2 = (SetType) params[1];

			return set1.getLeastCommonSupertype(set2);
		}

		return null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		SetValue set1 = (SetValue) args[0];
		SetValue set2 = (SetValue) args[1];
		return set1.union(set2);
	}
}

// --------------------------------------------------------

/* union : Set(T1) x Bag(T2) -> Bag(T1), with T2 <= T1 */
final class Op_set_union_bag extends OpGeneric {
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
		if (params.length == 2 && params[0].isSet() && params[1].isBag()) {
			SetType set = (SetType) params[0];
			BagType bag = (BagType) params[1];
			Type newElementType = set.elemType().getLeastCommonSupertype(
					bag.elemType());

			if (newElementType != null)
				return TypeFactory.mkBag(newElementType);
		}
		return null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		SetValue set = (SetValue) args[0];
		BagValue bag = (BagValue) args[1];
		return set.union(bag);
	}
}

// --------------------------------------------------------

/* intersection : Set(T1) x Set(T2) -> Set(T1), with T2 <= T1 */
final class Op_set_intersection extends OpGeneric {
	public String name() {
		return "intersection";
	}

	public int kind() {
		return OPERATION;
	}

	public boolean isInfixOrPrefix() {
		return false;
	}

	public Type matches(Type params[]) {
		if (params.length == 2 && params[0].isSet() && params[1].isSet()) {
			SetType set1 = (SetType) params[0];
			SetType set2 = (SetType) params[1];
			Type commonElementType = set1.elemType().getLeastCommonSupertype(
					set2.elemType());

			if (commonElementType != null)
				return TypeFactory.mkSet(commonElementType);
		}
		return null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		SetValue set1 = (SetValue) args[0];
		SetValue set2 = (SetValue) args[1];
		return set1.intersection(set2);
	}
}

// --------------------------------------------------------

/* intersection : Set(T1) x Bag(T2) -> Set(T1), with T2 <= T1 */
final class Op_set_intersection_bag extends OpGeneric {
	public String name() {
		return "intersection";
	}

	public int kind() {
		return OPERATION;
	}

	public boolean isInfixOrPrefix() {
		return false;
	}

	public Type matches(Type params[]) {
		if (params.length == 2 && params[0].isSet() && params[1].isBag()) {
			SetType set = (SetType) params[0];
			BagType bag = (BagType) params[1];

			Type commonElementType = set.elemType().getLeastCommonSupertype(
					bag.elemType());

			if (commonElementType != null)
				return TypeFactory.mkSet(commonElementType);
		}
		return null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		SetValue set = (SetValue) args[0];
		BagValue bag = (BagValue) args[1];
		return set.intersection(bag);
	}
}

// --------------------------------------------------------

/* - : Set(T1) x Set(T2) -> Set(T1), with T2 <= T1 */
final class Op_set_difference extends OpGeneric {
	public String name() {
		return "-";
	}

	public int kind() {
		return OPERATION;
	}

	public boolean isInfixOrPrefix() {
		return true;
	}

	public Type matches(Type params[]) {
		if (params.length == 2 && params[0].isSet() && params[1].isSet()) {
			SetType set1 = (SetType) params[0];
			SetType set2 = (SetType) params[1];
			Type commonElementType = set1.elemType().getLeastCommonSupertype(
					set2.elemType());

			if (commonElementType != null)
				return TypeFactory.mkSet(commonElementType);
		}
		return null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		SetValue set1 = (SetValue) args[0];
		SetValue set2 = (SetValue) args[1];
		return set1.difference(set2);
	}
}

// --------------------------------------------------------

/* including : Set(T1) x T2 -> Set(T1), with T2 <= T1 */
final class Op_set_including extends OpGeneric {
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
		if (params.length == 2 && params[0].isSet()) {
			SetType set1 = (SetType) params[0];

			Type commonElementType = set1.elemType().getLeastCommonSupertype(
					params[1]);

			if (commonElementType != null)
				return TypeFactory.mkSet(commonElementType);

		}
		return null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		if (args[0].isUndefined())
			return new UndefinedValue(resultType);
		SetValue set1 = (SetValue) args[0];
		return set1.including(args[1]);
	}
}

// --------------------------------------------------------

/* excluding : Set(T1) x T2 -> Set(T1), with T2 <= T1 */
final class Op_set_excluding extends OpGeneric {
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
		if (params.length == 2 && params[0].isSet()) {
			SetType set1 = (SetType) params[0];
			Type commonElementType = set1.elemType().getLeastCommonSupertype(
					params[1]);

			if (commonElementType != null)
				return TypeFactory.mkSet(commonElementType);
		}
		return null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		if (args[0].isUndefined())
			return new UndefinedValue(resultType);
		SetValue set1 = (SetValue) args[0];
		return set1.excluding(args[1]);
	}
}

// --------------------------------------------------------

/* symmetricDifference : Set(T1) x Set(T2) -> Set(T1) with T2 <= T1 */
final class Op_set_symmetricDifference extends OpGeneric {
	public String name() {
		return "symmetricDifference";
	}

	public int kind() {
		return OPERATION;
	}

	public boolean isInfixOrPrefix() {
		return false;
	}

	public Type matches(Type params[]) {
		if (params.length == 2 && params[0].isSet() && params[1].isSet()) {
			SetType set1 = (SetType) params[0];
			SetType set2 = (SetType) params[1];

			Type commonElementType = set1.elemType().getLeastCommonSupertype(
					set2.elemType());

			if (commonElementType != null)
				return TypeFactory.mkSet(commonElementType);
		}
		return null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		SetValue set1 = (SetValue) args[0];
		SetValue set2 = (SetValue) args[1];
		return set1.symmetricDifference(set2);
	}
}