package org.tzi.use.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssociationDTO {

    private String associationName;

    private String end1ClassName;
    private String end1RoleName;
    private String end1Multiplicity;
    private AggregationTypeDTO end1Aggregation;

    private String end2ClassName;
    private String end2RoleName;
    private String end2Multiplicity;
    private AggregationTypeDTO end2Aggregation;
}


