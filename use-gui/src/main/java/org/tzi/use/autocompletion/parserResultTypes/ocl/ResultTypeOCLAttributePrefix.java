package org.tzi.use.autocompletion.parserResultTypes.ocl;

import java.util.Objects;

/**
 * Represents the autocompletion parser's result type for OCL expressions with an attribute prefix.
 * It extends {@link ResultTypeOCL}.
 */
public class ResultTypeOCLAttributePrefix implements ResultTypeOCL {

    public String objectName;
    public String operationType;
    public String attributePrefix;

    public ResultTypeOCLAttributePrefix(String objectName, String operationType, String attributePrefix) {
        this.objectName = objectName;
        this.operationType = operationType;
        this.attributePrefix = attributePrefix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultTypeOCLAttributePrefix that = (ResultTypeOCLAttributePrefix) o;
        return Objects.equals(objectName, that.objectName) &&
                Objects.equals(operationType, that.operationType) &&
                Objects.equals(attributePrefix, that.attributePrefix);
    }

    @Override
    public String toString() {
        return "ResultTypeOCLAttributePrefix{" +
                "objectName='" + objectName + '\'' +
                ", operationType='" + operationType + '\'' +
                ", attributePrefix='" + attributePrefix + '\'' +
                '}';
    }
}
