package org.tzi.use.autocompletion.parserResultTypes.useCommands;

import java.util.Objects;

/**
 * Represents a specific autocompletion parser result type associated with the USE command "destroy".
 * It extends {@link ResultTypeUseCommands}.
 */

public class ResultTypeUseDestroy implements ResultTypeUseCommands{
    public String objectNames; //comma seperated list

    public ResultTypeUseDestroy(String objectNames) {
        this.objectNames = objectNames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultTypeUseDestroy that = (ResultTypeUseDestroy) o;
        return Objects.equals(objectNames, that.objectNames);
    }

    @Override
    public String toString() {
        return "ResultTypeUseDestroy{" +
                "objectNames='" + objectNames + '\'' +
                '}';
    }
}
