package org.tzi.use.uml.ocl.type;

import java.util.Set;

public class VoidType extends Type {

	public Set<Type> allSupertypes() {
		throw new UnsupportedOperationException("Call to allSupertypes is invalid on OclVoid");
	}

	public boolean equals(Object obj) {
		return obj instanceof VoidType;
	}

	public int hashCode() {
		return 0;
	}

	public boolean isVoidType() {
    	return true;
    }
	
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

	@Override
	public boolean isVoidOrElementTypeIsVoid() {
		return true;
	}

}
