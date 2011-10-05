package org.tzi.use.uml.ocl.type;

import java.util.List;
import java.util.Set;

public class VoidType extends Type {

	@Override
	public Set<Type> allSupertypes() {
		throw new UnsupportedOperationException("Call to allSupertypes is invalid on OclVoid");
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof VoidType;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public boolean isVoidType() {
    	return true;
    }
	
	@Override
	public boolean isSubtypeOf(Type t) {
		return true;
	}

	@Override
	public boolean isBag() {
		return true;
	}

	@Override
	public boolean isBoolean() {
		return true;
	}

	@Override
	public boolean isCollection(boolean excludeVoid) {
		if (excludeVoid)
			return false;
		else
			return true;
	}

	@Override
	public boolean isDate() {
		return true;
	}

	@Override
	public boolean isEnum() {
		return true;
	}

	@Override
	public boolean isInstantiableCollection() {
		return true;
	}

	@Override
	public boolean isInteger() {
		return true;
	}

	@Override
	public boolean isNumber() {
		return true;
	}

	@Override
	public boolean isObjectType() {
		return true;
	}

	@Override
	public boolean isOrderedSet() {
		return true;
	}

	@Override
	public boolean isReal() {
		return true;
	}

	@Override
	public boolean isSequence() {
		return true;
	}

	@Override
	public boolean isSet() {
		return true;
	}

	@Override
	public boolean isString() {
		return true;
	}

	@Override
	public boolean isTupleType(boolean excludeVoid) {
		return !excludeVoid;
	}

	@Override
    public StringBuilder toString(StringBuilder sb) {
		return sb.append("OclVoid");
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.uml.ocl.type.Type#initOrderedSuperTypes(java.util.List)
	 */
	@Override
	protected void getOrderedSuperTypes(List<Type> allSupertypes) {
		// Nothing to do
	}

}
