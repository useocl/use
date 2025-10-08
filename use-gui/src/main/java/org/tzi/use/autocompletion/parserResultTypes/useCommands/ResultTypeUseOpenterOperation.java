package org.tzi.use.autocompletion.parserResultTypes.useCommands;

import java.util.Objects;

/**
 * Represents an autocompletion parser result type associated with the USE command "openter" when the object name was already given.
 * It is a subtype of {@link ResultTypeUseOpenter}.
 */
public class ResultTypeUseOpenterOperation implements ResultTypeUseOpenter{
    public String objectName;
    public String operationPrefix;

    public ResultTypeUseOpenterOperation(String objectName, String operationPrefix) {
        this.objectName = objectName;
        this.operationPrefix = operationPrefix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultTypeUseOpenterOperation that = (ResultTypeUseOpenterOperation) o;
        return Objects.equals(objectName, that.objectName) &&
                Objects.equals(operationPrefix, that.operationPrefix);
    }

    @Override
    public String toString() {
        return "ResultTypeUseOpenterOperation{" +
                "objectName='" + objectName + '\'' +
                ", operationPrefix='" + operationPrefix + '\'' +
                '}';
    }
}
