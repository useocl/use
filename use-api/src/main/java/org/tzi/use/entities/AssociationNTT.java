package org.tzi.use.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("association")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssociationNTT {
    @Id
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

