package org.tzi.use.DTO;

import lombok.*;
import org.tzi.use.entities.AssociationNTT;
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
    private Map<String, AssociationNTT> associations = new TreeMap<>();
    private Map<String, InvariantNTT> invariants = new TreeMap<>();
    private Map<String,PrePostConditionNTT> prePostConditions = new TreeMap<>();

    public ModelDTO(String name) {
        this.name = name;
    }

}
