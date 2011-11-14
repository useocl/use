package org.tzi.use.uml.ocl.type;

import java.util.HashSet;
import java.util.Set;

public class DateType extends BasicType {
	
	public DateType() {
		super("Date");
	}
	
	public boolean isDate() {
    	return true;
    }
    
    /** 
     * Returns true if this type is a subtype of <code>t</code>. 
     */
    public boolean isSubtypeOf(Type t) {
        return equals(t) || t.isTrueOclAny();
    }
    
    @Override
	public Set<Type> allSupertypes() {
		Set<Type> res = new HashSet<Type>(2);
        res.add(TypeFactory.mkOclAny());
        res.add(this);
        return res;
	}
}
