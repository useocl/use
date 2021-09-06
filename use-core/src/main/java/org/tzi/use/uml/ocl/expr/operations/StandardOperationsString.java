package org.tzi.use.uml.ocl.expr.operations;

import java.util.ArrayList;
import java.util.List;

import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.IntegerValue;
import org.tzi.use.uml.ocl.value.RealValue;
import org.tzi.use.uml.ocl.value.SequenceValue;
import org.tzi.use.uml.ocl.value.StringValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;

import com.google.common.collect.Multimap;

public class StandardOperationsString {
	public static void registerTypeOperations(Multimap<String, OpGeneric> opmap) {
		// operations on String
		OpGeneric.registerOperation(new Op_string_concatinfix(), opmap);
		OpGeneric.registerOperation(new Op_string_size(), opmap);
		OpGeneric.registerOperation(new Op_string_concat(), opmap);
		OpGeneric.registerOperation(new Op_string_substring(), opmap);
		OpGeneric.registerOperation(new Op_string_toInteger(), opmap);
		OpGeneric.registerOperation(new Op_string_toReal(), opmap);
		
		OpGeneric temp = new Op_string_toUpper();
		// Register as toUpper
		OpGeneric.registerOperation(temp, opmap);
		// Register as toUpperCase
		OpGeneric.registerOperation("toUpperCase", temp, opmap);
		
		temp = new Op_string_toLower();
		// Register as toLower
		OpGeneric.registerOperation(temp, opmap);
		// Register as toLowerCase
		OpGeneric.registerOperation("toLowerCase", temp, opmap);
		
		OpGeneric.registerOperation(new Op_string_indexOf(), opmap);
		OpGeneric.registerOperation(new Op_string_equalsIgnoreCase(), opmap);
		OpGeneric.registerOperation(new Op_string_at(), opmap);
		OpGeneric.registerOperation(new Op_string_characters(), opmap);
		OpGeneric.registerOperation(new Op_string_toBoolean(), opmap);
		
		// OCL extensions!
		OpGeneric.registerOperation(new Op_string_less(), opmap);
		OpGeneric.registerOperation(new Op_string_greater(), opmap);
		OpGeneric.registerOperation(new Op_string_lessequal(), opmap);
		OpGeneric.registerOperation(new Op_string_greaterequal(), opmap);
		OpGeneric.registerOperation(new Op_string_split(), opmap);
	}
}

// --------------------------------------------------------
//
// String operations.
//
// --------------------------------------------------------

/* size : String -> Integer */
final class Op_string_size extends OpGeneric {
	public String name() {
		return "size";
	}

	public int kind() {
		return OPERATION;
	}

	public boolean isInfixOrPrefix() {
		return false;
	}

	public Type matches(Type params[]) {
		return (params.length == 1 && params[0].isTypeOfString()) ? TypeFactory
				.mkInteger() : null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		int res = ((StringValue) args[0]).value().length();
		return IntegerValue.valueOf(res);
	}
}

// --------------------------------------------------------

/* concat : String x String -> String */
final class Op_string_concat extends OpGeneric {
	public String name() {
		return "concat";
	}

	public int kind() {
		return OPERATION;
	}

	public boolean isInfixOrPrefix() {
		return false;
	}

	public Type matches(Type params[]) {
		return (params.length == 2 && params[0].isTypeOfString() && params[1]
				.isTypeOfString()) ? params[0] : null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		String s1 = ((StringValue) args[0]).value();
		String s2 = ((StringValue) args[1]).value();
		return new StringValue(s1 + s2);
	}
}

//--------------------------------------------------------

/* + : String x String -> String */
final class Op_string_concatinfix extends OpGeneric {
	public String name() {
		return "+";
	}

	public int kind() {
		return OPERATION;
	}

	public boolean isInfixOrPrefix() {
		return true;
	}

	public Type matches(Type params[]) {
		return (params.length == 2 && params[0].isTypeOfString() && params[1]
				.isTypeOfString()) ? params[0] : null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		String s1 = ((StringValue) args[0]).value();
		String s2 = ((StringValue) args[1]).value();
		return new StringValue(s1 + s2);
	}
}

// --------------------------------------------------------

/* toUpper : String -> String */
final class Op_string_toUpper extends OpGeneric {
	public String name() {
		return "toUpper";
	}

	public int kind() {
		return OPERATION;
	}

	public boolean isInfixOrPrefix() {
		return false;
	}

	public Type matches(Type params[]) {
		return (params.length == 1 && params[0].isTypeOfString()) ? params[0] : null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		String s1 = ((StringValue) args[0]).value();
		return new StringValue(s1.toUpperCase());
	}
}

// --------------------------------------------------------

/* toLower : String -> String */
final class Op_string_toLower extends OpGeneric {
	public String name() {
		return "toLower";
	}

	public int kind() {
		return OPERATION;
	}

	public boolean isInfixOrPrefix() {
		return false;
	}

	public Type matches(Type params[]) {
		return (params.length == 1 && params[0].isTypeOfString()) ? params[0] : null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		String s1 = ((StringValue) args[0]).value();
		return new StringValue(s1.toLowerCase());
	}
}

// --------------------------------------------------------

/* substring : String x Integer x Integer -> String */
final class Op_string_substring extends OpGeneric {
	public String name() {
		return "substring";
	}

	public int kind() {
		return OPERATION;
	}

	public boolean isInfixOrPrefix() {
		return false;
	}

	public Type matches(Type params[]) {
		return (params.length == 3 && params[0].isTypeOfString()
				&& params[1].isTypeOfInteger() && params[2].isTypeOfInteger()) ? params[0]
				: null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		String s1 = ((StringValue) args[0]).value();
		int lower = ((IntegerValue) args[1]).value();
		int upper = ((IntegerValue) args[2]).value();

		// return empty string in case of invalid range
		String s;
		try {
			s = s1.substring(lower - 1, upper);
		} catch (StringIndexOutOfBoundsException e) {
			s = "";
		}
		return new StringValue(s);
	}
}

// --------------------------------------------------------

/* < : String x String -> Boolean */
final class Op_string_less extends OpGeneric {
	public String name() {
		return "<";
	}

	public int kind() {
		return OPERATION;
	}

	public boolean isInfixOrPrefix() {
		return true;
	}

	public Type matches(Type params[]) {
		return (params.length == 2 && params[0].isTypeOfString() && params[1]
				.isTypeOfString()) ? TypeFactory.mkBoolean() : null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		int c = args[0].compareTo(args[1]);
		return (c < 0) ? BooleanValue.TRUE : BooleanValue.FALSE;
	}
}

// --------------------------------------------------------

/* > : String x String -> Boolean */
final class Op_string_greater extends OpGeneric {
	public String name() {
		return ">";
	}

	public int kind() {
		return OPERATION;
	}

	public boolean isInfixOrPrefix() {
		return true;
	}

	public Type matches(Type params[]) {
		return (params.length == 2 && params[0].isTypeOfString() && params[1]
				.isTypeOfString()) ? TypeFactory.mkBoolean() : null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		int c = args[0].compareTo(args[1]);
		return (c > 0) ? BooleanValue.TRUE : BooleanValue.FALSE;
	}
}

// --------------------------------------------------------

/* <= : String x String -> Boolean */
final class Op_string_lessequal extends OpGeneric {
	public String name() {
		return "<=";
	}

	public int kind() {
		return OPERATION;
	}

	public boolean isInfixOrPrefix() {
		return true;
	}

	public Type matches(Type params[]) {
		return (params.length == 2 && params[0].isTypeOfString() && params[1]
				.isTypeOfString()) ? TypeFactory.mkBoolean() : null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		int c = args[0].compareTo(args[1]);
		return (c <= 0) ? BooleanValue.TRUE : BooleanValue.FALSE;
	}
}

// --------------------------------------------------------

/* >= : String x String -> Boolean */
final class Op_string_greaterequal extends OpGeneric {
	public String name() {
		return ">=";
	}

	public int kind() {
		return OPERATION;
	}

	public boolean isInfixOrPrefix() {
		return true;
	}

	public Type matches(Type params[]) {
		return (params.length == 2 && params[0].isTypeOfString() && params[1]
				.isTypeOfString()) ? TypeFactory.mkBoolean() : null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		int c = args[0].compareTo(args[1]);
		return (c >= 0) ? BooleanValue.TRUE : BooleanValue.FALSE;
	}
}

//--------------------------------------------------------

/* toReal : String -> Real */
final class Op_string_toReal extends OpGeneric {
	public String name() {
		return "toReal";
	}

	public int kind() {
		return OPERATION;
	}

	public boolean isInfixOrPrefix() {
		return false;
	}

	public Type matches(Type params[]) {
		return params.length == 1 && params[0].isTypeOfString() ? TypeFactory.mkReal() : null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		StringValue v = (StringValue)args[0];
		
		try {
			double d = Double.parseDouble(v.value());
			return new RealValue(d);
		} catch (NumberFormatException e) {
			return UndefinedValue.instance;
		}
	}
}

//--------------------------------------------------------

/* toInteger : String -> Integer */
final class Op_string_toInteger extends OpGeneric {
	public String name() {
		return "toInteger";
	}

	public int kind() {
		return OPERATION;
	}

	public boolean isInfixOrPrefix() {
		return false;
	}

	public Type matches(Type params[]) {
		return params.length == 1 && params[0].isTypeOfString() ? TypeFactory.mkInteger() : null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		StringValue v = (StringValue)args[0];
		
		try {
			int i = Integer.parseInt(v.value());
			return IntegerValue.valueOf(i);
		} catch (NumberFormatException e) {
			return UndefinedValue.instance;
		}
	}
}

//--------------------------------------------------------
/* indexOf : String x String -> Integer */
// From OCL 2.2 p. 145f.:
//
// Queries the index in self at which s is a substring of self, or zero if s is not a substring of self. 
// The empty string is considered to be a substring of every string but the empty string, at index 1. 
// No string is a substring of the empty string.
//
// post: self.size() = 0 implies result = 0
// post: s.size() = 0 and self.size() > 0 implies result = 1
// post: s.size() > 0 and result > 0 implies self.substring(result, result + s.size() - 1) = s
final class Op_string_indexOf extends OpGeneric {
	public String name() {
		return "indexOf";
	}

	public int kind() {
		return OPERATION;
	}

	public boolean isInfixOrPrefix() {
		return false;
	}

	public Type matches(Type params[]) {
		return params.length == 2 && params[0].isTypeOfString() && params[1].isTypeOfString() ? 
				TypeFactory.mkInteger() : null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		String self = ((StringValue)args[0]).value();
		String s = ((StringValue)args[1]).value();
		
		if (self.length() == 0) return IntegerValue.valueOf(0);
		if (s.length()== 0 && self.length() > 0) return IntegerValue.valueOf(1);
		
		return IntegerValue.valueOf(self.indexOf(s) + 1);
	}
}

//--------------------------------------------------------

/* equalsIgnoreCase : String x String -> Boolean */
final class Op_string_equalsIgnoreCase extends OpGeneric {
	public String name() {
		return "equalsIgnoreCase";
	}

	public int kind() {
		return OPERATION;
	}

	public boolean isInfixOrPrefix() {
		return false;
	}

	public Type matches(Type params[]) {
		return params.length == 2 && params[0].isTypeOfString() && params[1].isTypeOfString() ? 
				TypeFactory.mkBoolean() : null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		String self = ((StringValue)args[0]).value();
		String s = ((StringValue)args[1]).value();
		
		return BooleanValue.get(self.equalsIgnoreCase(s));
	}
}

//--------------------------------------------------------

/* at : String x Integer -> String */
final class Op_string_at extends OpGeneric {
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
		return params.length == 2 && params[0].isTypeOfString() && params[1].isTypeOfInteger() ? 
				TypeFactory.mkString() : null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		String self = ((StringValue)args[0]).value();
		int i = ((IntegerValue)args[1]).value();
		
		if (i <= 0) return UndefinedValue.instance;
		if (i > self.length()) return UndefinedValue.instance;
		
		return new StringValue(self.substring(i - 1, i));
	}
}

//--------------------------------------------------------

/* characters : String -> Sequence(String) */
final class Op_string_characters extends OpGeneric {
	public String name() {
		return "characters";
	}

	public int kind() {
		return OPERATION;
	}

	public boolean isInfixOrPrefix() {
		return false;
	}

	public Type matches(Type params[]) {
		return params.length == 1 && params[0].isTypeOfString() ? 
				TypeFactory.mkSequence(TypeFactory.mkString()) : null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		String self = ((StringValue)args[0]).value();
		String c;
		List<Value> chars = new ArrayList<Value>(self.length());
		
		for (int i = 0; i < self.length(); i++) {
			c = self.substring(i, i + 1);
			chars.add(new StringValue(c));
		}
		
		SequenceValue result = new SequenceValue(TypeFactory.mkString(), chars);
		return result;
	}
}

//--------------------------------------------------------

/* toBoolean : String -> Boolean */
final class Op_string_toBoolean extends OpGeneric {
	public String name() {
		return "toBoolean";
	}

	public int kind() {
		return OPERATION;
	}

	public boolean isInfixOrPrefix() {
		return false;
	}

	public Type matches(Type params[]) {
		return params.length == 1 && params[0].isTypeOfString() ? 
				TypeFactory.mkBoolean() : null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		StringValue v = (StringValue)args[0];
		
		try {
			boolean b = Boolean.parseBoolean(v.value());
			return BooleanValue.get(b);
		} catch (NumberFormatException e) {
			return UndefinedValue.instance;
		}
	}
}

/* split : String x String -> Sequence(String) */
final class Op_string_split extends OpGeneric {
	public String name() {
		return "split";
	}

	public int kind() {
		return OPERATION;
	}

	public boolean isInfixOrPrefix() {
		return false;
	}

	public Type matches(Type params[]) {
		return params.length == 2 && params[0].isTypeOfString() && params[1].isTypeOfString() ? 
				TypeFactory.mkSequence(TypeFactory.mkString()) : null;
	}

	public Value eval(EvalContext ctx, Value[] args, Type resultType) {
		String source = ((StringValue)args[0]).value();
		String separator = ((StringValue)args[1]).value();
		
		try {
			String[] parts = source.split(separator);
			Value[] values = new Value[parts.length];
			
			for (int i = 0; i < parts.length; i++) {
				values[i] = new StringValue(parts[i]);
			}
			
			return new SequenceValue(TypeFactory.mkString(), values);
			
		} catch (NumberFormatException e) {
			return UndefinedValue.instance;
		}
	}
}