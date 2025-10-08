package org.tzi.use.autocompletion.parserResultTypes.ocl;

/**
 * Represents a specific autocompletion parser result type associated with OCL collections.
 * It is used for expressions ending with a comma and having "forAll" as operation.
 * It extends {@link ResultTypeOCLCollections}.
 */
public class ResultTypeOCLCollectionsEndsWithCommaAndIsForAll implements ResultTypeOCLCollections{
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }
}
