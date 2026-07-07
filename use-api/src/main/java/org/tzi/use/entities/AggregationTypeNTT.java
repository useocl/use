package org.tzi.use.entities;

public enum AggregationTypeNTT {
    NONE("association"),
    AGGREGATION("aggregation"),
    COMPOSITION("composition");

    private final String displayName;

    AggregationTypeNTT(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
