package org.tzi.use.uml.ocl.type;

import java.util.HashSet;
import java.util.Set;

import org.tzi.use.uml.api.IType;
import org.tzi.use.uml.mm.MClassifier;

/**
 * Adapter that exposes an mm.MClassifier as an OCL low-level Type.
 */
public class ClassifierType extends TypeImpl {
    private final MClassifier classifier;

    public ClassifierType(MClassifier classifier) {
        this.classifier = classifier;
    }

    public MClassifier classifier() {
        return classifier;
    }

    @Override
    public StringBuilder toString(StringBuilder sb) {
        sb.append(classifier.name());
        return sb;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof ClassifierType) {
            return classifier.equals(((ClassifierType) obj).classifier);
        }
        if (obj instanceof MClassifier) {
            return classifier.equals(obj);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return classifier.hashCode();
    }

    @Override
    public Type getLeastCommonSupertype(Type other) {
        if (other == null) return this;
        // delegate to mm classifier logic for least common supertype
        IType res = classifier.getLeastCommonSupertype((IType) other);
        if (res instanceof Type) {
            return (Type) res;
        }
        return TypeFactory.mkOclAny();
    }

    @Override
    public Set<Type> allSupertypes() {
        Set<Type> res = new HashSet<>();
        res.add(TypeFactory.mkOclAny());
        res.add(this);
        for (MClassifier p : classifier.allParents()) {
            res.add(TypeFactory.mkClassifierType(p));
        }
        return res;
    }

    @Override
    public boolean isTypeOfClassifier() {
        return true;
    }

    @Override
    public boolean isKindOfClassifier(IType.VoidHandling h) {
        return true;
    }

    @Override
    public boolean isTypeOfClass() {
        return classifier.isKindOfClass(IType.VoidHandling.EXCLUDE_VOID) || classifier.isTypeOfClass();
    }

    @Override
    public boolean isTypeOfDataType() {
        return classifier.isTypeOfDataType();
    }

    @Override
    public boolean isTypeOfOclAny() {
        return false;
    }
}

