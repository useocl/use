package org.tzi.use.uml.ocl.expr.operations;

import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.Type.VoidHandling;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.IntegerValue;
import org.tzi.use.uml.ocl.value.RealValue;
import org.tzi.use.uml.ocl.value.StringValue;
import org.tzi.use.uml.ocl.value.UnlimitedNaturalValue;
import org.tzi.use.uml.ocl.value.Value;

import com.google.common.collect.Multimap;

class StandardOperationsNumber {
	public static void registerTypeOperations(Multimap<String, OpGeneric> opmap) {
		// operations on Integer and Real
        OpGeneric.registerOperation(new Op_number_add(), opmap);
        OpGeneric.registerOperation(new Op_number_sub(), opmap);
        OpGeneric.registerOperation(new Op_number_mult(), opmap);
        OpGeneric.registerOperation(new Op_number_unaryminus(), opmap);
        OpGeneric.registerOperation(new Op_number_div(), opmap); 
        OpGeneric.registerOperation(new Op_number_unaryplus(), opmap); 
        OpGeneric.registerOperation(new Op_number_max(), opmap);
        OpGeneric.registerOperation(new Op_number_min(), opmap);
        OpGeneric.registerOperation(new Op_number_less(), opmap); 
        OpGeneric.registerOperation(new Op_number_greater(), opmap);
        OpGeneric.registerOperation(new Op_number_lessequal(), opmap); 
        OpGeneric.registerOperation(new Op_number_greaterequal(), opmap);
        OpGeneric.registerOperation(new Op_number_toString(), opmap);
        
        // Real
        OpGeneric.registerOperation(new Op_real_abs(), opmap);
        OpGeneric.registerOperation(new Op_real_floor(), opmap); 
        OpGeneric.registerOperation(new Op_real_round(), opmap); 
        
        // Integer
        OpGeneric.registerOperation(new Op_integer_abs(), opmap); 
        OpGeneric.registerOperation(new Op_integer_mod(), opmap); 
        OpGeneric.registerOperation(new Op_integer_idiv(), opmap);
        
	}
}

//--------------------------------------------------------
//
//Integer and Real operations.
//
//--------------------------------------------------------

/**
* This class is only used for +, *, -, max, min on Integers and Reals.
*/
abstract class ArithOperation extends OpGeneric {
	@Override
	public int kind() {
		return OPERATION;
	}

	@Override
	public Type matches(Type params[]) {
		if (params.length == 2) {
			if (params[0].isTypeOfInteger() && params[1].isTypeOfInteger())
				return TypeFactory.mkInteger();
			else if (params[0].isKindOfNumber(VoidHandling.INCLUDE_VOID)
					&& params[1].isKindOfNumber(VoidHandling.INCLUDE_VOID))
				return TypeFactory.mkReal();
		}
		return null;
	}
}

//--------------------------------------------------------
/* + : Integer x Integer -> Integer */
/* + : Real x Integer -> Real */
/* + : Integer x Real -> Real */
/* + : Real x Real -> Real */
final class Op_number_add extends ArithOperation {
	@Override
	public String name() {
		return "+";
	}

	@Override
	public boolean isInfixOrPrefix() {
		return true;
	}

	@Override
	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		if (args[0].isInteger() && args[1].isInteger()) {
			int res = ((IntegerValue) args[0]).value()
					+ ((IntegerValue) args[1]).value();
			return IntegerValue.valueOf(res);
		} else {
			double d1;
			double d2;
			if (args[0].isInteger())
				d1 = ((IntegerValue) args[0]).value();
			else
				d1 = ((RealValue) args[0]).value();

			if (args[1].isInteger())
				d2 = ((IntegerValue) args[1]).value();
			else
				d2 = ((RealValue) args[1]).value();
			return new RealValue(d1 + d2);
		}
	}
}

//--------------------------------------------------------

/* - : Integer x Integer -> Integer */
/* - : Real x Integer -> Real */
/* - : Integer x Real -> Real */
/* - : Real x Real -> Real */
final class Op_number_sub extends ArithOperation {
	@Override
	public String name() {
		return "-";
	}

	@Override
	public boolean isInfixOrPrefix() {
		return true;
	}

	@Override
	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		if (args[0].isInteger() && args[1].isInteger()) {
			int res = ((IntegerValue) args[0]).value()
					- ((IntegerValue) args[1]).value();
			return IntegerValue.valueOf(res);
		} else {
			double d1;
			double d2;
			if (args[0].isInteger())
				d1 = ((IntegerValue) args[0]).value();
			else
				d1 = ((RealValue) args[0]).value();

			if (args[1].isInteger())
				d2 = ((IntegerValue) args[1]).value();
			else
				d2 = ((RealValue) args[1]).value();
			return new RealValue(d1 - d2);
		}
	}
}

//--------------------------------------------------------

/* * : Integer x Integer -> Integer */
/* * : Real x Integer -> Real */
/* * : Integer x Real -> Real */
/* * : Real x Real -> Real */
final class Op_number_mult extends ArithOperation {
	@Override
	public String name() {
		return "*";
	}

	@Override
	public boolean isInfixOrPrefix() {
		return true;
	}

	@Override
	public Value eval(EvalContext ctx, Value[] args, Type resultType) {		
		if (args[0].isInteger() && args[1].isInteger()) {
			int res = ((IntegerValue) args[0]).value()
					* ((IntegerValue) args[1]).value();
			return IntegerValue.valueOf(res);
		} else {
			double d1;
			double d2;
			if (args[0].isInteger())
				d1 = ((IntegerValue) args[0]).value();
			else
				d1 = ((RealValue) args[0]).value();

			if (args[1].isInteger())
				d2 = ((IntegerValue) args[1]).value();
			else
				d2 = ((RealValue) args[1]).value();
			return new RealValue(d1 * d2);
		}
	}
}

// --------------------------------------------------------

/* / : Number x Number -> Real */
final class Op_number_div extends OpGeneric {
	@Override
	public String name() {
		return "/";
	}

	@Override
	public int kind() {
		return OPERATION;
	}

	@Override
	public boolean isInfixOrPrefix() {
		return true;
	}

	@Override
	public Type matches(Type params[]) {
		return (params.length == 2 && 
				params[0].isKindOfNumber(VoidHandling.EXCLUDE_VOID) && 
				params[1].isKindOfNumber(VoidHandling.EXCLUDE_VOID)) ? TypeFactory.mkReal() : null;
	}

	@Override
	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		double d1;
		double d2;
		if (args[0].isInteger())
			d1 = ((IntegerValue) args[0]).value();
		else
			d1 = ((RealValue) args[0]).value();

		if (args[1].isInteger())
			d2 = ((IntegerValue) args[1]).value();
		else
			d2 = ((RealValue) args[1]).value();
		double res = d1 / d2;
		// make special values resulting in undefined
		if (Double.isNaN(res) || Double.isInfinite(res))
			throw new ArithmeticException();
		return new RealValue(res);
	}
}

// --------------------------------------------------------

/* abs : Real -> Real */
final class Op_real_abs extends OpGeneric {
	@Override
	public String name() {
		return "abs";
	}

	@Override
	public int kind() {
		return OPERATION;
	}

	@Override
	public boolean isInfixOrPrefix() {
		return false;
	}

	@Override
	public Type matches(Type params[]) {
		return (params.length == 1 && params[0].isTypeOfReal()) ? TypeFactory
				.mkReal() : null;
	}

	@Override
	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		double d1 = ((RealValue) args[0]).value();
		return new RealValue(Math.abs(d1));
	}
}

// --------------------------------------------------------

/* abs : Integer -> Integer */
final class Op_integer_abs extends OpGeneric {
	@Override
	public String name() {
		return "abs";
	}

	@Override
	public int kind() {
		return OPERATION;
	}

	@Override
	public boolean isInfixOrPrefix() {
		return false;
	}

	@Override
	public Type matches(Type params[]) {
		return (params.length == 1 && params[0].isTypeOfInteger()) ? TypeFactory
				.mkInteger() : null;
	}

	@Override
	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		int i1 = ((IntegerValue) args[0]).value();
		return IntegerValue.valueOf(Math.abs(i1));
	}
}

// --------------------------------------------------------

/* - : Number -> Number */
final class Op_number_unaryminus extends OpGeneric {
	@Override
	public String name() {
		return "-";
	}

	@Override
	public int kind() {
		return OPERATION;
	}

	@Override
	public boolean isInfixOrPrefix() {
		return true;
	}

	@Override
	public Type matches(Type params[]) {
		return (params.length == 1 && params[0].isKindOfNumber(VoidHandling.EXCLUDE_VOID)) ? params[0] : null;
	}

	@Override
	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		Value res;
		if (args[0].isInteger()) {
			int i = ((IntegerValue) args[0]).value();
			res = IntegerValue.valueOf(-i);
		} else {
			double d = ((RealValue) args[0]).value();
			res = new RealValue(-d);
		}
		return res;
	}
}

// --------------------------------------------------------

/* + : Number -> Number */
final class Op_number_unaryplus extends OpGeneric {
	@Override
	public String name() {
		return "+";
	}

	@Override
	public int kind() {
		return OPERATION;
	}

	@Override
	public boolean isInfixOrPrefix() {
		return true;
	}

	@Override
	public Type matches(Type params[]) {
		return (params.length == 1 && params[0].isKindOfNumber(VoidHandling.EXCLUDE_VOID)) ? params[0] : null;
	}

	@Override
	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		// nop
		return args[0];
	}
}

// --------------------------------------------------------

/* floor : Real -> Integer */
/* floor : Integer -> Integer */
final class Op_real_floor extends OpGeneric {
	@Override
	public String name() {
		return "floor";
	}

	@Override
	public int kind() {
		return OPERATION;
	}

	@Override
	public boolean isInfixOrPrefix() {
		return false;
	}

	@Override
	public Type matches(Type params[]) {
		return (params.length == 1 && params[0].isKindOfNumber(VoidHandling.EXCLUDE_VOID)) ? TypeFactory
				.mkInteger() : null;
	}

	@Override
	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		double d1;
		if (args[0].isInteger())
			d1 = ((IntegerValue) args[0]).value();
		else
			d1 = ((RealValue) args[0]).value();

		return IntegerValue.valueOf((int) Math.floor(d1));
	}
}

// --------------------------------------------------------

/* round : Real -> Integer */
/* round : Integer -> Integer */
final class Op_real_round extends OpGeneric {
	@Override
	public String name() {
		return "round";
	}

	@Override
	public int kind() {
		return OPERATION;
	}

	@Override
	public boolean isInfixOrPrefix() {
		return false;
	}

	@Override
	public Type matches(Type params[]) {
		return (params.length == 1 && params[0].isKindOfNumber(VoidHandling.EXCLUDE_VOID)) ? TypeFactory
				.mkInteger() : null;
	}

	@Override
	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		double d1;
		if (args[0].isInteger())
			d1 = ((IntegerValue) args[0]).value();
		else
			d1 = ((RealValue) args[0]).value();

		return IntegerValue.valueOf((int) Math.round(d1));
	}
}

// --------------------------------------------------------

/* max : Integer x Integer -> Integer */
/* max : Real x Integer -> Real */
/* max : Integer x Real -> Real */
/* max : Real x Real -> Real */
final class Op_number_max extends ArithOperation {
	@Override
	public String name() {
		return "max";
	}

	@Override
	public int kind() {
		return OPERATION;
	}

	@Override
	public boolean isInfixOrPrefix() {
		return false;
	}

	@Override
	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		if (args[0].isInteger() && args[1].isInteger()) {
			int res = Math.max(((IntegerValue) args[0]).value(),
					((IntegerValue) args[1]).value());
			return IntegerValue.valueOf(res);
		} else {
			double d1;
			double d2;
			if (args[0].isInteger())
				d1 = ((IntegerValue) args[0]).value();
			else
				d1 = ((RealValue) args[0]).value();

			if (args[1].isInteger())
				d2 = ((IntegerValue) args[1]).value();
			else
				d2 = ((RealValue) args[1]).value();
			return new RealValue(Math.max(d1, d2));
		}
	}
}

// --------------------------------------------------------

/* min : Integer x Integer -> Integer */
/* min : Real x Integer -> Real */
/* min : Integer x Real -> Real */
/* min : Real x Real -> Real */
final class Op_number_min extends ArithOperation {
	@Override
	public String name() {
		return "min";
	}

	@Override
	public int kind() {
		return OPERATION;
	}

	@Override
	public boolean isInfixOrPrefix() {
		return false;
	}

	@Override
	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		if (args[0].isInteger() && args[1].isInteger()) {
			int res = Math.min(((IntegerValue) args[0]).value(),
					((IntegerValue) args[1]).value());
			return IntegerValue.valueOf(res);
		} else {
			double d1;
			double d2;
			if (args[0].isInteger())
				d1 = ((IntegerValue) args[0]).value();
			else
				d1 = ((RealValue) args[0]).value();

			if (args[1].isInteger())
				d2 = ((IntegerValue) args[1]).value();
			else
				d2 = ((RealValue) args[1]).value();
			return new RealValue(Math.min(d1, d2));
		}
	}
}

// --------------------------------------------------------

/* mod : Integer x Integer -> Integer */
final class Op_integer_mod extends OpGeneric {
	@Override
	public String name() {
		return "mod";
	}

	@Override
	public int kind() {
		return OPERATION;
	}

	@Override
	public boolean isInfixOrPrefix() {
		return true;
	}

	@Override
	public Type matches(Type params[]) {
		return (params.length == 2 && params[0].isTypeOfInteger() && params[1]
				.isTypeOfInteger()) ? TypeFactory.mkInteger() : null;
	}

	@Override
	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		int i1 = ((IntegerValue) args[0]).value();
		int i2 = ((IntegerValue) args[1]).value();
		return IntegerValue.valueOf(i1 % i2);
	}
}

// --------------------------------------------------------

/* idiv : Integer x Integer -> Integer */
final class Op_integer_idiv extends OpGeneric {
	@Override
	public String name() {
		return "div";
	}

	@Override
	public int kind() {
		return OPERATION;
	}

	@Override
	public boolean isInfixOrPrefix() {
		return true;
	}

	@Override
	public Type matches(Type params[]) {
		return (params.length == 2 && params[0].isTypeOfInteger() && params[1]
				.isTypeOfInteger()) ? TypeFactory.mkInteger() : null;
	}

	@Override
	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		int i1 = ((IntegerValue) args[0]).value();
		int i2 = ((IntegerValue) args[1]).value();
		return IntegerValue.valueOf(i1 / i2);
	}
}

// --------------------------------------------------------

/* < : Number x Number -> Boolean */
final class Op_number_less extends OpGeneric {
	@Override
	public String name() {
		return "<";
	}

	@Override
	public int kind() {
		return OPERATION;
	}

	@Override
	public boolean isInfixOrPrefix() {
		return true;
	}

	@Override
	public Type matches(Type params[]) {
		return (params.length == 2
				&& params[0].isKindOfNumber(VoidHandling.EXCLUDE_VOID) && params[1]
					.isKindOfNumber(VoidHandling.EXCLUDE_VOID)) ? TypeFactory
				.mkBoolean() : null;
	}

	@Override
	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		double d1;
		double d2;
		
		if (args[0].isUnlimitedNatural())
			d1 = ((UnlimitedNaturalValue) args[0]).value();
		else if (args[0].isInteger())
			d1 = ((IntegerValue) args[0]).value();
		else
			d1 = ((RealValue) args[0]).value();

		if (args[1].isUnlimitedNatural())
			d2 = ((UnlimitedNaturalValue) args[1]).value();
		else if (args[1].isInteger())
			d2 = ((IntegerValue) args[1]).value();
		else
			d2 = ((RealValue) args[1]).value();
		return BooleanValue.get(d1 < d2);
	}
}

// --------------------------------------------------------

/* > : Number x Number -> Boolean */
final class Op_number_greater extends OpGeneric {
	@Override
	public String name() {
		return ">";
	}

	@Override
	public int kind() {
		return OPERATION;
	}

	@Override
	public boolean isInfixOrPrefix() {
		return true;
	}

	@Override
	public Type matches(Type params[]) {
		return (params.length == 2
				&& params[0].isKindOfNumber(VoidHandling.EXCLUDE_VOID) && params[1]
					.isKindOfNumber(VoidHandling.EXCLUDE_VOID)) ? TypeFactory
				.mkBoolean() : null;
	}

	@Override
	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		double d1;
		double d2;
		if (args[0].isUnlimitedNatural())
			d1 = ((UnlimitedNaturalValue) args[0]).value();
		else if (args[0].isInteger())
			d1 = ((IntegerValue) args[0]).value();
		else
			d1 = ((RealValue) args[0]).value();

		if (args[1].isUnlimitedNatural())
			d2 = ((UnlimitedNaturalValue) args[1]).value();
		else if (args[1].isInteger())
			d2 = ((IntegerValue) args[1]).value();
		else
			d2 = ((RealValue) args[1]).value();
		return BooleanValue.get(d1 > d2);
	}
}

// --------------------------------------------------------

/* <= : Number x Number -> Boolean */
final class Op_number_lessequal extends OpGeneric {
	@Override
	public String name() {
		return "<=";
	}

	@Override
	public int kind() {
		return OPERATION;
	}

	@Override
	public boolean isInfixOrPrefix() {
		return true;
	}

	@Override
	public Type matches(Type params[]) {
		return (params.length == 2
				&& params[0].isKindOfNumber(VoidHandling.EXCLUDE_VOID) && params[1]
					.isKindOfNumber(VoidHandling.EXCLUDE_VOID)) ? TypeFactory
				.mkBoolean() : null;
	}

	@Override
	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		double d1;
		double d2;
		if (args[0].isUnlimitedNatural())
			d1 = ((UnlimitedNaturalValue) args[0]).value();
		else if (args[0].isInteger())
			d1 = ((IntegerValue) args[0]).value();
		else
			d1 = ((RealValue) args[0]).value();

		if (args[1].isUnlimitedNatural())
			d2 = ((UnlimitedNaturalValue) args[1]).value();
		else if (args[1].isInteger())
			d2 = ((IntegerValue) args[1]).value();
		else
			d2 = ((RealValue) args[1]).value();
		return BooleanValue.get(d1 <= d2);
	}
}

// --------------------------------------------------------

/* >= : Number x Number -> Boolean */
final class Op_number_greaterequal extends OpGeneric {
	@Override
	public String name() {
		return ">=";
	}

	@Override
	public int kind() {
		return OPERATION;
	}

	@Override
	public boolean isInfixOrPrefix() {
		return true;
	}

	@Override
	public Type matches(Type params[]) {
		return (params.length == 2
				&& params[0].isKindOfNumber(VoidHandling.EXCLUDE_VOID) && params[1]
					.isKindOfNumber(VoidHandling.EXCLUDE_VOID)) ? TypeFactory
				.mkBoolean() : null;
	}

	@Override
	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		double d1;
		double d2;
		if (args[0].isUnlimitedNatural())
			d1 = ((UnlimitedNaturalValue) args[0]).value();
		else if (args[0].isInteger())
			d1 = ((IntegerValue) args[0]).value();
		else
			d1 = ((RealValue) args[0]).value();

		if (args[1].isUnlimitedNatural())
			d2 = ((UnlimitedNaturalValue) args[1]).value();
		else if (args[1].isInteger())
			d2 = ((IntegerValue) args[1]).value();
		else
			d2 = ((RealValue) args[1]).value();
		return BooleanValue.get(d1 >= d2);
	}
}

/* toString : Number -> String */
final class Op_number_toString extends OpGeneric {
	@Override
	public String name() {
		return "toString";
	}

	@Override
	public int kind() {
		return OPERATION;
	}

	@Override
	public boolean isInfixOrPrefix() {
		return false;
	}

	@Override
	public Type matches(Type params[]) {
		return (params.length == 1 && params[0]
				.isKindOfNumber(VoidHandling.EXCLUDE_VOID)) ? TypeFactory
				.mkString() : null;
	}

	@Override
	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		return new StringValue(args[0].toString());
	}
}