package org.tzi.use.uml.ocl.expr.operations;

import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.BagType;
import org.tzi.use.uml.ocl.type.CollectionType;
import org.tzi.use.uml.ocl.type.SetType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.BagValue;
import org.tzi.use.uml.ocl.value.SetValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.util.StringUtil;

import com.google.common.collect.Multimap;

public class StandardOperationsSet {
	public static void registerTypeOperations(Multimap<String, OpGeneric> opmap) {
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
		if (params.length == 2 && 
			params[0].isTypeOfSet() && 
			params[1].isTypeOfSet()) {
			
			return params[0].getLeastCommonSupertype(params[1]);
		}

		return null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		SetValue set1 = (SetValue) args[0];
		SetValue set2 = (SetValue) args[1];
		return set1.union(resultType, set2);
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
		if (params.length == 2 && 
			params[0].isTypeOfSet() && 
			params[1].isTypeOfBag()) {
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
		return set.union(resultType, bag);
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
		if (params.length == 2 && 
			params[0].isTypeOfSet() &&
			params[1].isTypeOfSet()) {
			
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
		return set1.intersection(resultType, set2);
	}
	
	@Override
	public String checkWarningUnrelatedTypes(Expression args[]) {
		CollectionType col1 = (CollectionType) args[0].type();
		CollectionType col2 = (CollectionType) args[1].type();
		
		Type elemType1 = col1.elemType();
		Type elemType2 = col2.elemType();
		
		Type commonElementType = elemType1.getLeastCommonSupertype(elemType2);
		
		if (!(elemType1.isTypeOfOclAny() || elemType2.isTypeOfOclAny()) && commonElementType.isTypeOfOclAny()) {
			return "Expression " + StringUtil.inQuotes(this.stringRep(args, "")) + 
					 " will always evaluate to an empty set, " + StringUtil.NEWLINE +
					 "because the element types " + StringUtil.inQuotes(elemType1) + 
					 " and " + StringUtil.inQuotes(elemType2) + " are unrelated.";
		}
		
		return null;
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
		if (params.length == 2 && 
			params[0].isTypeOfSet() && 
			params[1].isTypeOfBag()) {
			
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
		return set.intersection(resultType, bag);
	}
	
	@Override
	public String checkWarningUnrelatedTypes(Expression args[]) {
		CollectionType col1 = (CollectionType) args[0].type();
		CollectionType col2 = (CollectionType) args[1].type();
		
		Type elemType1 = col1.elemType();
		Type elemType2 = col2.elemType();
		
		Type commonElementType = elemType1.getLeastCommonSupertype(elemType2);
		
		if (!(elemType1.isTypeOfOclAny() || elemType2.isTypeOfOclAny()) && commonElementType.isTypeOfOclAny()) {
			return "Expression " + StringUtil.inQuotes(this.stringRep(args, "")) + 
					 " will always evaluate to an empty, " + StringUtil.NEWLINE +
					 "because the element type " + StringUtil.inQuotes(elemType1) + 
					 " and " + StringUtil.inQuotes(elemType2) + " are unrelated.";
		}
		
		return null;
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
		if (params.length == 2 && 
			params[0].isTypeOfSet() && 
			params[1].isTypeOfSet()) {
			
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
		return set1.difference(resultType, set2);
	}
	
	@Override
	public String checkWarningUnrelatedTypes(Expression args[]) {
		CollectionType col1 = (CollectionType) args[0].type();
		CollectionType col2 = (CollectionType) args[1].type();
		
		Type elemType1 = col1.elemType();
		Type elemType2 = col2.elemType();
		
		Type commonElementType = elemType1.getLeastCommonSupertype(elemType2);
		
		if (!(elemType1.isTypeOfOclAny() || elemType2.isTypeOfOclAny()) && commonElementType.isTypeOfOclAny()) {
			return "Expression " + StringUtil.inQuotes(this.stringRep(args, "")) + 
					 " will always evaluate to the same set, " + StringUtil.NEWLINE +
					 "because the element types " + StringUtil.inQuotes(elemType1) + 
					 " and " + StringUtil.inQuotes(elemType2) + " are unrelated.";
		}
		
		return null;
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
		if (params.length == 2 && 
			params[0].isTypeOfSet()) {
			SetType set1 = (SetType) params[0];

			Type commonElementType = set1.elemType().getLeastCommonSupertype(params[1]);

			if (commonElementType != null)
				return TypeFactory.mkSet(commonElementType);

		}
		return null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		if (args[0].isUndefined())
			return UndefinedValue.instance;
		SetValue set1 = (SetValue) args[0];
				
		return set1.including(resultType, args[1]);
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
		if (params.length == 2 && 
			params[0].isTypeOfSet()) {
			
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
			return UndefinedValue.instance;
		SetValue set1 = (SetValue) args[0];
		return set1.excluding(resultType, args[1]);
	}
	
	@Override
	public String checkWarningUnrelatedTypes(Expression args[]) {
		CollectionType col = (CollectionType) args[0].type();
		
		Type commonElementType = col.elemType().getLeastCommonSupertype(args[1].type());
		
		if (!(col.elemType().isTypeOfOclAny() || args[1].type().isTypeOfOclAny()) && commonElementType.isTypeOfOclAny()) {
			return "Expression " + StringUtil.inQuotes(this.stringRep(args, "")) + 
					 " will always evaluate to the same set, " + StringUtil.NEWLINE +
					 "because the element type " + StringUtil.inQuotes(col.elemType()) + 
					 " and the parameter type " + StringUtil.inQuotes(args[1].type()) + " are unrelated.";
		}
		
		return null;
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
		if (params.length == 2 && 
			params[0].isTypeOfSet() &&
			params[1].isTypeOfSet()) {
			
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
		return set1.symmetricDifference(resultType, set2);
	}
	
	@Override
	public String checkWarningUnrelatedTypes(Expression args[]) {
		CollectionType col1 = (CollectionType) args[0].type();
		CollectionType col2 = (CollectionType) args[1].type();
		
		Type elemType1 = col1.elemType();
		Type elemType2 = col2.elemType();
		
		Type commonElementType = elemType1.getLeastCommonSupertype(elemType2);
		
		if (!(elemType1.isTypeOfOclAny() || elemType2.isTypeOfOclAny()) && commonElementType.isTypeOfOclAny()) {
			return "Expression " + StringUtil.inQuotes(this.stringRep(args, "")) + 
					 " will always evaluate to the union of both sets, " + StringUtil.NEWLINE +
					 "because the element types " + StringUtil.inQuotes(elemType1) + 
					 " and " + StringUtil.inQuotes(elemType2) + " are unrelated.";
		}

		return null;
	}
}