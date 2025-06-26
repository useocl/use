package org.tzi.use.autocompletion.parserResultTypes.useCommands;

import java.util.Objects;

/**
 * Represents an autocompletion parser result type associated with the USE command "insert" when no objects names were already given.
 * It is a subtype of {@link ResultTypeUseInsert}.
 */
public class ResultTypeUseInsertObjects implements ResultTypeUseInsert{
    public String objectNames; //comma seperated list

    public ResultTypeUseInsertObjects(String objectNames) {
        this.objectNames = objectNames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultTypeUseInsertObjects that = (ResultTypeUseInsertObjects) o;
        return Objects.equals(objectNames, that.objectNames);
    }

    @Override
    public String toString() {
        return "ResultTypeUseInsertObjects{" +
                "objectNames='" + objectNames + '\'' +
                '}';
    }
}
