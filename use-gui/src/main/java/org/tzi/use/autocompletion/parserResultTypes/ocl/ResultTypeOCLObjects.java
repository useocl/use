package org.tzi.use.autocompletion.parserResultTypes.ocl;

import java.util.Objects;

/**
 * Represents a specific autocompletion parser result type associated with OCL.
 * It is used for expressions containing a completed object name.
 * It extends {@link ResultTypeOCL}.
 */
public class ResultTypeOCLObjects implements ResultTypeOCL {

    public String objectName;

    public String operationType;

    public ResultTypeOCLObjects(String objectName, String operationType) {
        this.objectName = objectName;
        this.operationType = operationType;
    }@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultTypeOCLObjects that = (ResultTypeOCLObjects) o;
        return Objects.equals(objectName, that.objectName) &&
                Objects.equals(operationType, that.operationType);
    }

    @Override
    public String toString() {
        return "ResultTypeOCLObjects{" +
                "objectName='" + objectName + '\'' +
                ", operationType='" + operationType + '\'' +
                '}';
    }

}
