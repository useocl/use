package org.tzi.use.util.rubyintegration;

import org.tzi.use.uml.ocl.type.CollectionType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.Type.VoidHandling;
import org.tzi.use.uml.ocl.value.*;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.util.Log;

import java.util.ArrayList;
import java.util.List;

public class RubyHelper {
	private RubyHelper() {}
	
	public static Value rubyValueToUseValue(Object rubyValue, Type expectedType) {
		Value result = UndefinedValue.instance;
		
		if (rubyValue == null) return result;
		
		if (rubyValue instanceof Value) {
			result = (Value)rubyValue;
		} else if (rubyValue instanceof Long) {
			result = IntegerValue.valueOf(((Long) rubyValue).intValue());
        } else if (rubyValue instanceof Integer) {
			result = IntegerValue.valueOf((Integer) rubyValue);
        } else if (rubyValue instanceof String) {
			result = new StringValue((String)rubyValue);
		} else if (rubyValue instanceof Boolean) {
			result = BooleanValue.get((Boolean) rubyValue);
		} else if (rubyValue instanceof Double) {
			result = new RealValue((Double) rubyValue);
		} else if (rubyValue instanceof MObject) {
			MObject obj = (MObject)rubyValue;
			result = new ObjectValue(obj.cls(), obj);
		} else if (rubyValue instanceof List<?> && expectedType.isKindOfCollection(VoidHandling.EXCLUDE_VOID)) {
			List<?> list = (List<?>)rubyValue;
			Value[] elements = new Value[list.size()];
			
			for (int index = 0; index < list.size(); index++) {
				elements[index] = rubyValueToUseValue(list.get(index), ((CollectionType)expectedType).elemType());
			}
			
			CollectionType expectedCollectionType = (CollectionType)expectedType;
			result = expectedCollectionType.createCollectionValue(elements);
		} else {
			Log.warn("rubyValueToUseValue: Unhandeled Ruby value: " + rubyValue.toString());
		}
		
		if (result.type().conformsTo(expectedType)) {
			return result;
		} else {
			Log.warn("rubyValueToUseValue: converted type of value (`"
					+ result.type().toString()
					+ "') is not a subtype of the expected result type (`"
					+ expectedType.toString() + "'");
			return UndefinedValue.instance;
		}
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
}
