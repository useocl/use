package org.tzi.use.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssociationNTT {
    private String associationName;

    private String end1ClassName;
    private String end1RoleName;
    private String end1Multiplicity;
    private AggregationTypeNTT end1Aggregation;

    private String end2ClassName;
    private String end2RoleName;
    private String end2Multiplicity;
    private AggregationTypeNTT end2Aggregation;
}

