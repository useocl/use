package org.tzi.use.uml.api;

import java.util.Map;

/**
 * API-level annotatable contract that mirrors mm.Annotatable but without mm types.
 */
public interface IAnnotatable {
    boolean isAnnotated();

    /**
     * Returns a map of annotation name -> annotation values (attribute->value)
     */
    Map<String, IElementAnnotation> getAllAnnotations();

    IElementAnnotation getAnnotation(String name);

    String getAnnotationValue(String annotationName, String attributeName);

    void addAnnotation(IElementAnnotation an);
}

