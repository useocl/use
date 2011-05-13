package org.tzi.use.uml.ocl.value;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.tzi.use.uml.ocl.type.TypeFactory;

public class DateValue extends Value {

	Date value;

	public DateValue(Date value) {
		super(TypeFactory.mkDate());
		this.value = value;
	}
	
	public Date getValue() {
		return value;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj instanceof DateValue) {
			return value.equals(((DateValue)obj).value);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
    public StringBuilder toString(StringBuilder sb) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return sb.append(df.format(value));
	}

	@Override
	public int compareTo(Value obj) {
		DateValue v2 = (DateValue)obj;
		return value.compareTo(v2.getValue());
	}

}
