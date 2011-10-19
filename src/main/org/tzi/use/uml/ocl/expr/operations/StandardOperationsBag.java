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
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.collections.MultiMap;

public class StandardOperationsBag {
	public static void registerTypeOperations(MultiMap<String, OpGeneric> opmap) {
		// operations on Bag
		OpGeneric.registerOperation(new Op_bag_union(), opmap);
		OpGeneric.registerOperation(new Op_bag_union_set(), opmap);
		OpGeneric.registerOperation(new Op_bag_intersection(), opmap);
		OpGeneric.registerOperation(new Op_bag_intersection_set(), opmap);
		OpGeneric.registerOperation(new Op_bag_including(), opmap);
		OpGeneric.registerOperation(new Op_bag_excluding(), opmap);
		// the following three are special expressions:
		// select
		// reject
		// collect
		// count: inherited from Collection		
		// Constructors
		OpGeneric.registerOperation(new Op_mkBagRange(), opmap);
	}
}

//--------------------------------------------------------
//
// Bag constructors.
//
// --------------------------------------------------------

/* mkBagRange : Integer x Integer, ... -> Bag(Integer) */
final class Op_mkBagRange extends OpGeneric {
	public String name() {
		return "mkBagRange";
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
				.mkBag(TypeFactory.mkInteger())
				: null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		int[] ranges = new int[args.length];
		for (int i = 0; i < args.length; i++)
			ranges[i] = ((IntegerValue) args[i]).value();

		return new BagValue(TypeFactory.mkInteger(), ranges);
	}

	public String stringRep(Expression args[], String atPre) {
		if (args.length % 2 != 0)
			throw new IllegalArgumentException("length=" + args.length);
		String s = "Bag{";
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
// Bag operations.
//
// --------------------------------------------------------

/* union : Bag(T1) x Bag(T2) -> Bag(T1), with T2 <= T1 */
final class Op_bag_union extends OpGeneric {
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
		if (params.length == 2 && params[0].isBag() && params[1].isBag()) {
			BagType bag1 = (BagType) params[0];
			BagType bag2 = (BagType) params[1];

			Type commonElementType = bag1.elemType().getLeastCommonSupertype(
					bag2.elemType());

			if (commonElementType != null)
				return TypeFactory.mkBag(commonElementType);
		}
		return null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		BagValue bag1 = (BagValue) args[0];
		BagValue bag2 = (BagValue) args[1];
		return bag1.union(bag2);
	}
}

// --------------------------------------------------------

/* union : Bag(T1) x Set(T2) -> Bag(T1), with T2 <= T1 */
final class Op_bag_union_set extends OpGeneric {
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
		if (params.length == 2 && params[0].isBag() && params[1].isSet()) {
			BagType bag = (BagType) params[0];
			SetType set = (SetType) params[1];

			Type commonElementType = bag.elemType().getLeastCommonSupertype(
					set.elemType());

			if (commonElementType != null)
				return TypeFactory.mkBag(commonElementType);
		}
		return null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		BagValue bag = (BagValue) args[0];
		SetValue set = (SetValue) args[1];
		return bag.union(set.asBag());
	}
}

// --------------------------------------------------------

/* intersection : Bag(T1) x Bag(T2) -> Bag(T1), with T2 <= T1 */
final class Op_bag_intersection extends OpGeneric {
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
		if (params.length == 2 && params[0].isBag() && params[1].isBag()) {
			BagType bag1 = (BagType) params[0];
			BagType bag2 = (BagType) params[1];

			Type commonElementType = bag1.elemType().getLeastCommonSupertype(
					bag2.elemType());

			if (commonElementType != null)
				return TypeFactory.mkBag(commonElementType);

		}
		return null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		BagValue bag1 = (BagValue) args[0];
		BagValue bag2 = (BagValue) args[1];
		return bag1.intersection(bag2);
	}
	
	@Override
	public String checkWarningUnrelatedTypes(Expression args[]) {
		BagType bag1 = (BagType) args[0].type();
		BagType bag2 = (BagType) args[1].type();
		
		Type elemType1 = bag1.elemType();
		Type elemType2 = bag2.elemType();
		
		Type commonElementType = elemType1.getLeastCommonSupertype(elemType2);
		
		if (!(elemType1.isTrueOclAny() || elemType2.isTrueOclAny()) && commonElementType.isTrueOclAny()) {
			return  "Expression " + StringUtil.inQuotes(this.stringRep(args, "")) + 
					 " can never evaluate to more then an empty bag, " + StringUtil.NEWLINE +
					 "because the element types " + StringUtil.inQuotes(elemType1) + 
					 " and " + StringUtil.inQuotes(elemType2) + " are unrelated.";
		}
		
		return null;
	}
}

// --------------------------------------------------------

/* intersection : Bag(T1) x Set(T2) -> Set(T1), with T2 <= T1 */
final class Op_bag_intersection_set extends OpGeneric {
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
		if (params.length == 2 && params[0].isBag() && params[1].isSet()) {
			BagType bag = (BagType) params[0];
			SetType set = (SetType) params[1];

			Type commonElementType = bag.elemType().getLeastCommonSupertype(
					set.elemType());

			if (commonElementType != null)
				return TypeFactory.mkSet(commonElementType);
		}
		return null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		BagValue bag = (BagValue) args[0];
		SetValue set = (SetValue) args[1];
		return bag.asSet().intersection(set);
	}
	
	@Override
	public String checkWarningUnrelatedTypes(Expression args[]) {
		BagType  bag = (BagType) args[0].type();
		SetType set = (SetType) args[1].type();
		
		Type elemType1 = bag.elemType();
		Type elemType2 = set.elemType();
		
		Type commonElementType = elemType1.getLeastCommonSupertype(elemType2);
		
		if (!(elemType1.isTrueOclAny() || elemType2.isTrueOclAny()) && commonElementType.isTrueOclAny()) {
			return "Expression " + StringUtil.inQuotes(this.stringRep(args, "")) + 
					 " can never evaluate to more then an empty set, " + StringUtil.NEWLINE +
					 "because the element types " + StringUtil.inQuotes(elemType1) + 
					 " and " + StringUtil.inQuotes(elemType2) + " are unrelated.";
		}
		
		return null;
	}
}

// --------------------------------------------------------

/* including : Bag(T1) x T2 -> Bag(T1), with T2 <= T1 */
final class Op_bag_including extends OpGeneric {
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
		if (params.length == 2 && params[0].isBag()) {
			BagType bag = (BagType) params[0];
			Type commonElementType = bag.elemType().getLeastCommonSupertype(
					params[1]);

			if (commonElementType != null)
				return TypeFactory.mkBag(commonElementType);
		}
		return null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		if (args[0].isUndefined())
			return UndefinedValue.instance;
		BagValue bag = (BagValue) args[0];
		return bag.including(args[1]);
	}
}

// --------------------------------------------------------

/* excluding : Bag(T1) x T2 -> Bag(T1), with T2 <= T1 */
final class Op_bag_excluding extends OpGeneric {
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
		if (params.length == 2 && params[0].isBag()) {
			BagType bag = (BagType) params[0];
			Type commonElementType = bag.elemType().getLeastCommonSupertype(
					params[1]);

			if (commonElementType != null)
				return TypeFactory.mkBag(commonElementType);
		}
		return null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		if (args[0].isUndefined())
			return UndefinedValue.instance;
		BagValue bag = (BagValue) args[0];
		return bag.excluding(args[1]);
	}
	
	@Override
	public String checkWarningUnrelatedTypes(Expression args[]) {
		BagType  bag = (BagType) args[0].type();
		Type commonElementType = bag.elemType().getLeastCommonSupertype(args[1].type());
		
		if (!(bag.elemType().isTrueOclAny() || args[1].type().isTrueOclAny()) && commonElementType.isTrueOclAny()) {
			return "Expression " + StringUtil.inQuotes(this.stringRep(args, "")) + 
					" will always evaluate to the same bag, " + StringUtil.NEWLINE +
					"because the element type " + StringUtil.inQuotes(bag.elemType()) + 
					" and the parameter type " + StringUtil.inQuotes(args[1].type()) + " are unrelated.";
		}
		
		return null;
	}
}