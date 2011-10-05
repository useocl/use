package org.tzi.use.uml.ocl.type;

import java.util.List;

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
    
	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.type.Type#initOrderedSuperTypes(java.util.List)
	 */
	@Override
	protected void getOrderedSuperTypes(List<Type> allSupertypes) {
		allSupertypes.add(TypeFactory.mkOclAny());
	}
}
