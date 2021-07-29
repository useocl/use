package org.tzi.use.uml.ocl.type;

import java.util.Set;

public class VoidType extends TypeImpl {

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
	public boolean conformsTo(Type t) {
		return true;
	}

	@Override
	public boolean isKindOfNumber(VoidHandling h) {
		return h == VoidHandling.INCLUDE_VOID;
	}

	@Override
	public boolean isKindOfInteger(VoidHandling h) {
		return h == VoidHandling.INCLUDE_VOID;
	}

	@Override
	public boolean isKindOfUnlimitedNatural(VoidHandling h) {
		return h == VoidHandling.INCLUDE_VOID;
	}

	@Override
	public boolean isKindOfReal(VoidHandling h) {
		return h == VoidHandling.INCLUDE_VOID;
	}

	@Override
	public boolean isKindOfString(VoidHandling h) {
		return h == VoidHandling.INCLUDE_VOID;
	}

	@Override
	public boolean isKindOfBoolean(VoidHandling h) {
		return h == VoidHandling.INCLUDE_VOID;
	}

	@Override
	public boolean isKindOfEnum(VoidHandling h) {
		return h == VoidHandling.INCLUDE_VOID;
	}

	@Override
	public boolean isKindOfCollection(VoidHandling h) {
		return h == VoidHandling.INCLUDE_VOID;
	}

	@Override
	public boolean isKindOfSet(VoidHandling h) {
		return h == VoidHandling.INCLUDE_VOID;
	}

	@Override
	public boolean isKindOfSequence(VoidHandling h) {
		return h == VoidHandling.INCLUDE_VOID;
	}

	@Override
	public boolean isKindOfOrderedSet(VoidHandling h) {
		return h == VoidHandling.INCLUDE_VOID;
	}

	@Override
	public boolean isKindOfBag(VoidHandling h) {
		return h == VoidHandling.INCLUDE_VOID;
	}

	@Override
	public boolean isKindOfClass(VoidHandling h) {
		return h == VoidHandling.INCLUDE_VOID;
	}

	@Override
	public boolean isKindOfOclAny(VoidHandling h) {
		return h == VoidHandling.INCLUDE_VOID;
	}

	@Override
	public boolean isKindOfTupleType(VoidHandling h) {
		return h == VoidHandling.INCLUDE_VOID;
	}

	@Override
	public boolean isKindOfClassifier(VoidHandling h) {
		return h == VoidHandling.INCLUDE_VOID;
	}

	@Override
	public boolean isKindOfAssociation(VoidHandling h) {
		return h == VoidHandling.INCLUDE_VOID;
	}

	@Override
	public boolean isTypeOfVoidType() {
		return true;
	}

	@Override
	public boolean isVoidOrElementTypeIsVoid() {
		return true;
	}

	@Override
    public StringBuilder toString(StringBuilder sb) {
		return sb.append("OclVoid");
	}
}
