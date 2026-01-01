package org.tzi.use.uml.api;

import java.util.Set;

/** API abstraction for types used by mm layer. Implemented by ocl.Type via adapter. */
public interface IType {
    /** Returns whether this type conforms to another API-level type. */
    boolean conformsTo(IType other);

    enum VoidHandling {
        INCLUDE_VOID,
        EXCLUDE_VOID
    }

    Set<? extends IType> allSupertypes();

    /**
     * Returns the least common supertype of this type and the given API-level type.
     */
    IType getLeastCommonSupertype(IType other);


    boolean isVoidOrElementTypeIsVoid();

    boolean isKindOfNumber(VoidHandling h);
    boolean isTypeOfInteger();
    boolean isKindOfInteger(VoidHandling h);
    boolean isTypeOfUnlimitedNatural();
    boolean isKindOfUnlimitedNatural(VoidHandling h);
    boolean isKindOfReal(VoidHandling h);
    boolean isTypeOfReal();
    boolean isKindOfString(VoidHandling h);
    boolean isTypeOfString();
    boolean isKindOfBoolean(VoidHandling h);
    boolean isTypeOfBoolean();
    boolean isKindOfEnum(VoidHandling h);
    boolean isTypeOfEnum();
    boolean isKindOfCollection(VoidHandling h);
    boolean isTypeOfCollection();
    boolean isKindOfSet(VoidHandling h);
    boolean isTypeOfSet();
    boolean isKindOfSequence(VoidHandling h);
    boolean isTypeOfSequence();
    boolean isKindOfOrderedSet(VoidHandling h);
    boolean isTypeOfOrderedSet();
    boolean isKindOfBag(VoidHandling h);
    boolean isTypeOfBag();
    boolean isKindOfClassifier(VoidHandling h);
    boolean isTypeOfClassifier();
    boolean isKindOfClass(VoidHandling h);
    boolean isKindOfDataType(VoidHandling h);
    boolean isTypeOfDataType();
    boolean isTypeOfClass();
    boolean isKindOfAssociation(VoidHandling h);
    boolean isTypeOfAssociation();
    boolean isKindOfOclAny(VoidHandling h);
    boolean isTypeOfOclAny();
    boolean isKindOfTupleType(VoidHandling h);
    boolean isTypeOfTupleType();
    boolean isTypeOfVoidType();
    boolean isInstantiableCollection();

    StringBuilder toString(StringBuilder sb);

    String shortName();

    String qualifiedName();
}
