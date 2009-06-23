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

	public String toString() {
		return "OclVoid";
	}

}
