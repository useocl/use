package org.tzi.use.autocompletion.parserResultTypes.useCommands;

import java.util.Objects;

/**
 * Represents an autocompletion parser result type associated with the USE command "delete" when no object names were already given.
 * It is a subtype of {@link ResultTypeUseDelete}.
 */
public class ResultTypeUseDeleteObjects implements ResultTypeUseDelete{
    public String objectNames; //comma seperated list

    public ResultTypeUseDeleteObjects(String objectNames) {
        this.objectNames = objectNames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultTypeUseDeleteObjects that = (ResultTypeUseDeleteObjects) o;
        return Objects.equals(objectNames, that.objectNames);
    }

    @Override
    public String toString() {
        return "ResultTypeUseDeleteObjects{" +
                "objectNames='" + objectNames + '\'' +
                '}';
    }
}
