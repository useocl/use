package org.tzi.use.uml.api;

import java.util.Map;

/**
 * API-level view of an element annotation. Kept minimal and free of mm types.
 */
public interface IElementAnnotation {
    String getName();
    Map<String, String> getValues();

    default boolean hasAnnotationValue(String name) {
        Map<String, String> vals = getValues();
        return vals != null && vals.containsKey(name);
    }

    default String getAnnotationValue(String name) {
        Map<String, String> vals = getValues();
        return (vals == null) ? null : vals.get(name);
    }
}

