package org.tzi.use.DTO;

import lombok.*;
import org.tzi.use.entities.InvariantNTT;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelDTO {
    private String name;
    private List<ClassDTO> classes = new ArrayList<>();
    private List<String> associations = new ArrayList<>();
    private List<InvariantNTT> invariants = new ArrayList<>();

    public ModelDTO(String name) {
        this.name = name;
    }

}
