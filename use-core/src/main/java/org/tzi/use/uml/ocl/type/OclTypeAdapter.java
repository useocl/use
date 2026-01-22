package org.tzi.use.uml.ocl.type;

import org.tzi.use.uml.mm.MClassifier;

/**
 * Adapter delegating to the existing TypeAdapters utility in the OCL layer.
 * Implements the mm-owned ITypeAdapter so the mm package can use it without
 * depending on ocl at compile time.
 */
public final class OclTypeAdapter implements org.tzi.use.uml.mm.ITypeAdapter {

    @Override
    public MClassifier asMClassifier(org.tzi.use.uml.api.IType apiType) {
        return TypeAdapters.asMClassifier(apiType);
    }

    @Override
    public org.tzi.use.uml.api.IType asIType(MClassifier classifier) {
        return TypeAdapters.asOclType(classifier);
    }
}
