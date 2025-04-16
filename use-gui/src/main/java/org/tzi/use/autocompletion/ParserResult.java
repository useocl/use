package org.tzi.use.autocompletion;

import org.tzi.use.autocompletion.parserResultTypes.ResultTypeRoot;

import java.util.*;

/**
 * Represents the result of a parsed input String by {@link AutoCompletionParser}
 * It contains information about found types, found values, and the root result type.
 */
public class ParserResult {
    public List<String> foundTypes;

    public Map<String, String> foundValues;

    public ResultTypeRoot result;

    public ParserResult(){
        foundTypes = new LinkedList<>();
        foundValues = new HashMap<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParserResult that = (ParserResult) o;
        return Objects.equals(foundTypes, that.foundTypes) &&
                Objects.equals(foundValues, that.foundValues) &&
                Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(foundTypes, foundValues, result);
    }

    @Override
    public String toString() {
        return "ParserResult{" +
                "foundTypes=" + foundTypes +
                ", foundValues=" + foundValues +
                ", result=" + result +
                '}';
    }



    public void clearAll() {
        foundTypes.clear();
        foundValues.clear();
    }
}
