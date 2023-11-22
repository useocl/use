package org.tzi.use.autocompletion.parserResultTypes.ocl;

import java.util.Objects;

public class ResultTypeOCLCollectionsDefault extends ResultTypeOCLCollections {
    public String prefix;

    public String collectionType;

    public ResultTypeOCLCollectionsDefault(String prefix, String collectionType) {
        this.prefix = prefix;
        this.collectionType = collectionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultTypeOCLCollectionsDefault that = (ResultTypeOCLCollectionsDefault) o;
        return Objects.equals(prefix, that.prefix);
    }

    @Override
    public String toString() {
        return "ResultTypeOCLCollectionsDefault{" +
                "prefix='" + prefix + '\'' +
                "collectionType='" + collectionType + '\'' +
                '}';
    }
}
