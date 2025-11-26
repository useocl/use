package org.tzi.use.DTO;

import lombok.*;
import org.tzi.use.entities.InvariantNTT;
import org.tzi.use.entities.PrePostConditionNTT;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelDTO {
    private String name;
    private List<ClassDTO> classes = new ArrayList<>();
    private List<AssociationDTO> associations = new ArrayList<>();
    private List<InvariantDTO> invariants = new ArrayList<>();
    private Map<String, PrePostConditionNTT> prePostConditions = new TreeMap<>();

    public ModelDTO(String name) {
        this.name = name;
    }

}
