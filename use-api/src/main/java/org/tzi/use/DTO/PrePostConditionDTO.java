package org.tzi.use.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrePostConditionDTO {

    private String operationName;
    private String name;
    private String condition;
    private boolean isPre;

}
