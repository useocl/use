package org.tzi.use.uml.ocl.expr;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.DateValue;
import org.tzi.use.uml.ocl.value.IntegerValue;
import org.tzi.use.uml.ocl.value.Value;

/**
 * Base class for standard operations on Dates.
 * 
 * @author lhamann
 *
 */
abstract class ExpStdOpDate extends OpGeneric {

}

/**
 * Baseclass for Operations
 * Date x Date -> Boolean
 * @author lhamann
 *
 */
abstract class ExpStdOpDate_Date_Bool extends ExpStdOpDate
{
	@Override
	boolean isInfixOrPrefix() {
		return true;
	}

	@Override
	int kind() {
		return PREDICATE;
	}
	
	@Override
	Type matches(Type[] params) {
		if (params.length == 2 &&
			params[0].isDate() &&
			params[1].isDate())
		{
			return TypeFactory.mkBoolean();
		}
		
		return null;
	}
}

final class Op_Date_Lower extends ExpStdOpDate_Date_Bool {

	@Override
	String name() {
		return "<";
	}

	@Override
	Value eval(EvalContext ctx, Value[] args, Type resultType) {
		DateValue d1 = (DateValue)args[0];
		DateValue d2 = (DateValue)args[1];

		return BooleanValue.get(d1.compareTo(d2) < 0);
	}	
}

final class Op_Date_LowerEqual extends ExpStdOpDate_Date_Bool {

	@Override
	String name() {
		return "<=";
	}

	@Override
	Value eval(EvalContext ctx, Value[] args, Type resultType) {
		DateValue d1 = (DateValue)args[0];
		DateValue d2 = (DateValue)args[1];

		return BooleanValue.get(d1.compareTo(d2) <= 0);
	}	
}

final class Op_Date_Equal extends ExpStdOpDate_Date_Bool {

	@Override
	String name() {
		return "=";
	}

	@Override
	Value eval(EvalContext ctx, Value[] args, Type resultType) {
		DateValue d1 = (DateValue)args[0];
		DateValue d2 = (DateValue)args[1];

		return BooleanValue.get(d1.compareTo(d2) == 0);
	}	
}

final class Op_Date_GreaterEqual extends ExpStdOpDate_Date_Bool {

	@Override
	String name() {
		return ">=";
	}

	@Override
	Value eval(EvalContext ctx, Value[] args, Type resultType) {
		DateValue d1 = (DateValue)args[0];
		DateValue d2 = (DateValue)args[1];

		return BooleanValue.get(d1.compareTo(d2) >= 0);
	}	
}

final class Op_Date_Greater extends ExpStdOpDate_Date_Bool {

	@Override
	String name() {
		return ">";
	}

	@Override
	Value eval(EvalContext ctx, Value[] args, Type resultType) {
		DateValue d1 = (DateValue)args[0];
		DateValue d2 = (DateValue)args[1];

		return BooleanValue.get(d1.compareTo(d2) > 0);
	}	
}

final class Op_Date_Day extends ExpStdOpDate
{
	@Override
	String name() {
		return "Day";
	}
	
	@Override
	boolean isInfixOrPrefix() {
		return false;
	}

	@Override
	int kind() {
		return OPERATION;
	}

	@Override
	Type matches(Type[] params) {
		return (params.length == 1 && params[0].isDate()) ? TypeFactory.mkInteger() : null;
	}

	@Override
	Value eval(EvalContext ctx, Value[] args, Type resultType) {
		Calendar c = new GregorianCalendar();
		c.setTime(((DateValue)args[0]).getValue());
		
		return new IntegerValue(c.get(Calendar.DAY_OF_MONTH));
	}
}