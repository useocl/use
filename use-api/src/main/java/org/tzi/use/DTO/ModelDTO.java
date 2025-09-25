package org.tzi.use.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ModelDTO {
    private String name;
    private String id;
    private List<ClassDTO> classes = new ArrayList<>();
    private List<String> associations = new ArrayList<>();
    private List<String> invariants = new ArrayList<>();
    private List<PrePostConditionDTO> preConditions = new ArrayList<>();
    private List<PrePostConditionDTO> postConditions = new ArrayList<>();

    public ModelDTO(String name, String id) {
        this.name = name;
        this.id = id;
    }
}
