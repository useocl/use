package org.tzi.use.util.rubyintegration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.tzi.use.uml.ocl.expr.ExpConstBoolean;
import org.tzi.use.uml.ocl.expr.ExpConstInteger;
import org.tzi.use.uml.ocl.expr.ExpConstReal;
import org.tzi.use.uml.ocl.expr.ExpConstString;
import org.tzi.use.uml.ocl.expr.ExpUndefined;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.ExpressionWithValue;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.CollectionValue;
import org.tzi.use.uml.ocl.value.DateValue;
import org.tzi.use.uml.ocl.value.IntegerValue;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.RealValue;
import org.tzi.use.uml.ocl.value.StringValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.util.Log;

public class RubyHelper {
	private RubyHelper() {}
	
	public static Value rubyValueToUseValue(Object rubyValue) {
		if (rubyValue instanceof Value) {
			return (Value)rubyValue;
		}
		if (rubyValue instanceof Long) {
        	return new IntegerValue(((Long) rubyValue).intValue());
        }
		if (rubyValue instanceof Integer) {
        	return new IntegerValue(((Integer) rubyValue).intValue());
        }
		if (rubyValue instanceof String) {
			return new StringValue((String)rubyValue);
		}
		if (rubyValue instanceof Boolean) {
			return BooleanValue.get(((Boolean)rubyValue).booleanValue());
		}
		if (rubyValue instanceof Double) {
			return new RealValue(((Double)rubyValue).doubleValue());
		}
		if (rubyValue instanceof Date) {
			return new DateValue(((Date)rubyValue));
		}
		if (rubyValue instanceof MObject) {
			MObject obj = (MObject)rubyValue;
			return new ObjectValue(TypeFactory.mkObjectType(obj.cls()), obj);
		}
		if (rubyValue != null) {
			Log.warn("rubyValueToUseValue: Unhandeled Ruby value: " + rubyValue.toString());
		}
		
		return new UndefinedValue(TypeFactory.mkVoidType());
	}
	
	public static Object useValueToRubyValue(Value useValue) {
		if (useValue instanceof UndefinedValue) {
			return null;
		}
		if (useValue instanceof StringValue) {
			return ((StringValue)useValue).value();
		}
		if (useValue instanceof RealValue) {
			return ((RealValue)useValue).value();
		}
		if (useValue instanceof IntegerValue) {
			return ((IntegerValue)useValue).value();
		}
		if (useValue instanceof DateValue) {
			return ((DateValue)useValue).getValue();
		}
		if (useValue instanceof BooleanValue) {
			return ((BooleanValue)useValue).value();
		}
		if (useValue instanceof ObjectValue) {
			return ((ObjectValue)useValue).value();
		}
		if (useValue instanceof CollectionValue) {
			CollectionValue col = (CollectionValue)useValue;
			List<Object> result = new ArrayList<Object>(col.size());
			
			for (Value v : col.collection()) {
				result.add(useValueToRubyValue(v));
			}
			
			return result;
		}
		Log.warn("Unhandled USE value for Ruby:" + useValue.toStringWithType());
		return useValue;
	}
	
	public static Expression makeUSEExpression(Object rubyValue) {
		if (rubyValue instanceof Long) {
        	return new ExpConstInteger(((Long) rubyValue).intValue());
        }
		if (rubyValue instanceof Integer) {
        	return new ExpConstInteger(((Integer) rubyValue).intValue());
        }
		if (rubyValue instanceof String) {
			return new ExpConstString((String)rubyValue);
		}
		if (rubyValue instanceof Boolean) {
			return new ExpConstBoolean(((Boolean)rubyValue).booleanValue());
		}
		if (rubyValue instanceof Double) {
			return new ExpConstReal(((Double)rubyValue).doubleValue());
		}
		if (rubyValue instanceof MObject) {
			MObject obj = (MObject)rubyValue;
			return new ExpressionWithValue(new ObjectValue(TypeFactory.mkObjectType(obj.cls()), obj));
		}
		if (rubyValue != null) {
			Log.warn("makeUSEExpression: Unhandeled Ruby value: " + rubyValue.toString() + ":" + rubyValue.getClass().getName());
		}
		
		return new ExpUndefined(TypeFactory.mkVoidType());
	}
	
	public static Expression[] makeExpArray(Object[] exp) {
		Expression[] res = new Expression[exp.length];
		for (int i = 0; i < exp.length; i++) {
			res[i] = (Expression)exp[i];
		}
		
		return res;
	}
}
