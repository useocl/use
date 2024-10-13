package org.tzi.use.autocompletion.parserResultTypes.ocl;

import java.util.Objects;

/**
 * Represents a specific autocompletion parser result type associated with OCL collections.
 * It is used for iteration expressions containing a colon but not ending on "," or "|"
 * It extends {@link ResultTypeOCLCollections}.
 */
public class ResultTypeOCLCollectionsContainsColon implements ResultTypeOCLCollections {
    public String className;
    public String elemType;

    public ResultTypeOCLCollectionsContainsColon(String className, String elemType) {
        this.className = className;
        this.elemType = elemType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultTypeOCLCollectionsContainsColon that = (ResultTypeOCLCollectionsContainsColon) o;
        return Objects.equals(className, that.className) &&
                Objects.equals(elemType, that.elemType);
    }

    @Override
    public String toString() {
        return "ResultTypeOCLCollectionsContainsColon{" +
                "className='" + className + '\'' +
                ", elemType='" + elemType + '\'' +
                '}';
    }
}
