package org.tzi.use.autocompletion.parserResultTypes.ocl;

import java.util.Objects;

/**
 * Represents a specific autocompletion parser result type associated with OCL collections.
 * It is used for expressions ending with a pipe (|) and containing an iteration expression identifier.
 * It extends {@link ResultTypeOCLCollections}.
 */
public class ResultTypeOCLCollectionsEndWithPipe extends ResultTypeOCLCollections{
    public String iterExprIdentifier;

    public ResultTypeOCLCollectionsEndWithPipe(String iterExprIdentifier) {
        this.iterExprIdentifier = iterExprIdentifier;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultTypeOCLCollectionsEndWithPipe that = (ResultTypeOCLCollectionsEndWithPipe) o;
        return Objects.equals(iterExprIdentifier, that.iterExprIdentifier);
    }

    @Override
    public String toString() {
        return "ResultTypeOCLCollectionsEndWithPipe{" +
                "iterExprIdentifier='" + iterExprIdentifier + '\'' +
                '}';
    }
}
