package org.tzi.use.uml.mm;

import org.tzi.use.uml.api.IType;

/**
 * Adapter interface owned by the mm-layer to avoid api<>mm cycles.
 */
public interface ITypeAdapter {
    /**
     * Try to extract an mm MClassifier from an API-level IType. Returns null if not possible.
     */
    org.tzi.use.uml.mm.MClassifier asMClassifier(IType apiType);

    /**
     * Convert an mm MClassifier to an API-level IType (may return an OCL-backed type).
     */
    IType asIType(org.tzi.use.uml.mm.MClassifier classifier);
}
