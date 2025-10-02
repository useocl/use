package org.tzi.use.DTO;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
//@RequiredArgsConstructor
@NoArgsConstructor
public class ModelDTO {
    private String name;
//    private String id;
    private List<ClassDTO> classes = new ArrayList<>();
    private List<String> associations = new ArrayList<>();
    private List<String> invariants = new ArrayList<>();
//    private List<PrePostConditionDTO> preConditions = new ArrayList<>();
//    private List<PrePostConditionDTO> postConditions = new ArrayList<>();

    public ModelDTO(String name) {
        this.name = name;
    }

}
