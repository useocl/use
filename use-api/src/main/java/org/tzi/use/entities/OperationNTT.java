package org.tzi.use.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("operation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationNTT {

    private String operationName;
    private String[][] parameter;
    private String returnType;

}

