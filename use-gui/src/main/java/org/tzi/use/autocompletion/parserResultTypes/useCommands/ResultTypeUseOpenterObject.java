package org.tzi.use.autocompletion.parserResultTypes.useCommands;

import java.util.Objects;

/**
 * Represents an autocompletion parser result type associated with the USE command "openter" when no object name was already given.
 * It is a subtype of {@link ResultTypeUseOpenter}.
 */
public class ResultTypeUseOpenterObject implements ResultTypeUseOpenter{
    public String objectName;

    public ResultTypeUseOpenterObject(String objectName) {
        this.objectName = objectName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultTypeUseOpenterObject that = (ResultTypeUseOpenterObject) o;
        return Objects.equals(objectName, that.objectName);
    }

    @Override
    public String toString() {
        return "ResultTypeUseOpenterObject{" +
                "objectName='" + objectName + '\'' +
                '}';
    }
}
