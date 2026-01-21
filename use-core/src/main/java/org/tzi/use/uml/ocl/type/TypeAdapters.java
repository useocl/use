package org.tzi.use.uml.ocl.type;

import org.tzi.use.uml.api.IType;
import org.tzi.use.uml.mm.MClassifier;

/**
 * Central adapter utilities for converting between API/mm types and
 * low-level OCL Type implementations.
 *
 * Purpose: keep all adaptation logic in one place so we can avoid
 * scattered casts and reduce ClassCastException risks after refactorings.
 */
public final class TypeAdapters {

    private TypeAdapters() {
        // utility
    }

    /**
     * Convert an API-level IType to a low-level OCL Type.
     * If the provided IType already is a low-level Type it is returned as-is.
     * If it is an mm.MClassifier, a ClassifierType adapter is created.
     * Otherwise a conservative fallback OclAny is returned.
     */
    public static Type asOclType(IType apiType) {
        if (apiType == null) {
            return TypeFactory.mkOclAny();
        }
        if (apiType instanceof Type) {
            return (Type) apiType;
        }
        if (apiType instanceof MClassifier) {
            return TypeFactory.mkClassifierType((MClassifier) apiType);
        }
        return TypeFactory.mkOclAny();
    }

    /**
     * Convenience overload: adapt an mm MClassifier to an OCL Type.
     */
    public static Type asOclType(MClassifier classifier) {
        if (classifier == null) {
            return TypeFactory.mkOclAny();
        }
        if (classifier instanceof Type) {
            return (Type) classifier;
        }
        return TypeFactory.mkClassifierType(classifier);
    }

    /**
     * Extract an mm.MClassifier from a low-level OCL Type if possible.
     * Returns null if extraction is not possible.
     */
    public static MClassifier asMClassifier(Type type) {
        if (type == null) {
            return null;
        }
        if (type instanceof ClassifierType) {
            return ((ClassifierType) type).classifier();
        }
        if (type instanceof MClassifier) {
            return (MClassifier) type;
        }
        return null;
    }

    /**
     * Extract an mm.MClassifier from an API-level IType when possible.
     */
    public static MClassifier asMClassifier(IType apiType) {
        if (apiType == null) {
            return null;
        }

        // ⭐ DAS FEHLT
        if (apiType instanceof ClassifierType) {
            return ((ClassifierType) apiType).classifier();
        }

        if (apiType instanceof MClassifier) {
            return (MClassifier) apiType;
        }

        if (apiType instanceof Type) {
            return asMClassifier((Type) apiType);
        }

        return null;
    }

}

