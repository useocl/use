package org.tzi.use.autocompletion.parserResultTypes.ocl;

import java.util.Objects;

/**
 * Represents a specific autocompletion parser result type associated with OCL collections.
 * It is used for expressions ending with a pipe (|) and containing a colon (:).
 * It extends {@link ResultTypeOCLCollections}.
 */
public class ResultTypeOCLCollectionsEndsWithPipeAndContainsColon extends ResultTypeOCLCollections{
    public String className;

    public ResultTypeOCLCollectionsEndsWithPipeAndContainsColon(String className) {
        this.className = className;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultTypeOCLCollectionsEndsWithPipeAndContainsColon that = (ResultTypeOCLCollectionsEndsWithPipeAndContainsColon) o;
        return Objects.equals(className, that.className);
    }

    @Override
    public String toString() {
        return "ResultTypeOCLCollectionsEndsWithPipeAndContainsColon{" +
                "className='" + className + '\'' +
                '}';
    }
}
