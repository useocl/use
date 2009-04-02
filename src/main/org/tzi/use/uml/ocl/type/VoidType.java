package org.tzi.use.uml.ocl.type;

import java.util.Set;

public class VoidType extends Type {

	public Set allSupertypes() {
		throw new RuntimeException("Not implemented yet.");
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
