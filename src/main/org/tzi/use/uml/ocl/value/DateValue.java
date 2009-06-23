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
		return value.equals(obj);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public String toString() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return df.format(value);
	}

	@Override
	public int compareTo(Value obj) {
		DateValue v2 = (DateValue)obj;
		return value.compareTo(v2.getValue());
	}

}
