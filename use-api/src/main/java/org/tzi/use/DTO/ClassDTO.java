package org.tzi.use.DTO;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassDTO {

    private String name;
    private List<AttributeDTO> attributes = new ArrayList<>();
    private List<OperationDTO> operations = new ArrayList<>();
    private List<AssociationDTO> associations = new ArrayList<>();
//    private List<PrePostConditionDTO> prepostcondition = new ArrayList<>();
}
