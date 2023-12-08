package org.tzi.use.autocompletion.parserResultTypes.useCommands;

import java.util.Objects;

/**
 * Represents an autocompletion parser result type associated with the USE command "set" when the object name was already given.
 * It is a subtype of {@link ResultTypeUseSet}.
 */
public class ResultTypeUseSetAttr implements ResultTypeUseSet {
    public String objectName;

    public ResultTypeUseSetAttr(String objectName) {
        this.objectName = objectName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultTypeUseSetAttr that = (ResultTypeUseSetAttr) o;
        return Objects.equals(objectName, that.objectName);
    }

    @Override
    public String toString() {
        return "ResultTypeUseSetAttr{" +
                "objectName='" + objectName + '\'' +
                '}';
    }
}
