package org.tzi.use.autocompletion;

import java.util.*;

/**
 * Is a specialized map that associates types with their
 * respective attributes. Is used in {@link AutoCompletion} to map class names to types to attributes.
 * Each Type is mapped to a list of attribute names, where each attribute
 * is represented as a string.
 */
public class SuggestionForClassName implements Map<String, List<String>> {
    //contains the attributes of a class seperated by Type, e.g.: "Number", "String", "Boolean"
    private final Map<String, List<String>> typeToAttributeMap;

    public SuggestionForClassName() {
        typeToAttributeMap = new HashMap<>();
    }

    @Override
    public int size() {
        return typeToAttributeMap.size();
    }

    @Override
    public boolean isEmpty() {
        return typeToAttributeMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return typeToAttributeMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return typeToAttributeMap.containsValue(value);
    }

    @Override
    public List<String> get(Object key) {
        return typeToAttributeMap.get(key);
    }

    @Override
    public List<String> put(String key, List<String> value) {
        return typeToAttributeMap.put(key, value);
    }

    @Override
    public List<String> remove(Object key) {
        return typeToAttributeMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends List<String>> m) {
        typeToAttributeMap.putAll(m);
    }

    @Override
    public void clear() {
        typeToAttributeMap.clear();
    }

    @Override
    public Set<String> keySet() {
        return typeToAttributeMap.keySet();
    }

    @Override
    public Collection<List<String>> values() {
        return typeToAttributeMap.values();
    }

    @Override
    public Set<Entry<String, List<String>>> entrySet() {
        return typeToAttributeMap.entrySet();
    }
}
