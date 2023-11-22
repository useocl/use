package org.tzi.use.autocompletion.parserResultTypes.ocl;

import java.util.Objects;

/**
 * Represents a specific autocompletion parser result type associated with OCL.
 * It is used for expressions containing an object prefix.
 * It extends {@link ResultTypeOCL}.
 */
public class ResultTypeOCLObjectPrefix extends ResultTypeOCL{
    public String objectPrefix;

    public ResultTypeOCLObjectPrefix(String objectPrefix) {
        this.objectPrefix = objectPrefix;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultTypeOCLObjectPrefix that = (ResultTypeOCLObjectPrefix) o;
        return Objects.equals(objectPrefix, that.objectPrefix);
    }

    @Override
    public String toString() {
        return "ResultTypeOCLObjectPrefix{" +
                "objectPrefix='" + objectPrefix + '\'' +
                '}';
    }
}
