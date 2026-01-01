package org.tzi.use.uml.api;

import java.util.List;
import java.util.Map;
import java.util.Iterator;

/**
 * API interface for enumeration types used by the mm package.
 */
public interface IEnumType {
    String name();
    String qualifiedName();
    List<String> getLiterals();
    boolean contains(String lit);
    void setModel(IModel model);

    /**
     * Position in the model (for ordering in the source file)
     */
    int getPositionInModel();

    /**
     * Backwards-compatible iterator-style access. Some existing mm code
     * still calls literals(), so provide a default implementation.
     */
    default Iterator<String> literals() {
        List<String> lits = getLiterals();
        return (lits == null) ? java.util.Collections.<String>emptyList().iterator() : lits.iterator();
    }

    /**
     * Annotation helpers — provided directly on IEnumType to avoid clashes with mm implementations.
     */
    default boolean isAnnotated() { return !annotations().isEmpty(); }

    void addAnnotation(IElementAnnotation an);

    default Map<String, IElementAnnotation> annotations() { return java.util.Collections.emptyMap(); }
}
