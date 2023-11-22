package org.tzi.use.autocompletion.parserResultTypes.useCommands;

import java.util.Objects;

/**
 * Represents an autocompletion parser result type associated with the USE command "create" when an association name was already given.
 * It is a subtype of {@link ResultTypeUseCreate}.
 */
public class ResultTypeUseCreateAssociation extends ResultTypeUseCreate{
    public String objectName;
    public String associationName;

    public ResultTypeUseCreateAssociation(String objectName, String associationName) {
        this.objectName = objectName;
        this.associationName = associationName;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultTypeUseCreateAssociation that = (ResultTypeUseCreateAssociation) o;
        return Objects.equals(objectName, that.objectName) &&
                Objects.equals(associationName, that.associationName);
    }

    @Override
    public String toString() {
        return "ResultTypeUseCreateAssociation{" +
                "objectName='" + objectName + '\'' +
                ", associationName='" + associationName + '\'' +
                '}';
    }
}
