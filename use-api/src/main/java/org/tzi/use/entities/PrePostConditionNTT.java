package org.tzi.use.entities;

import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("prepostcondition")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrePostConditionNTT {
    private String operationName;
    private String name;
    private String condition;
    private boolean isPre;
}
