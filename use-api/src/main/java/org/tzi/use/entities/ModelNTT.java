package org.tzi.use.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.tzi.use.DTO.ClassDTO;

import java.util.ArrayList;
import java.util.List;

@Document("model")
@Data
@AllArgsConstructor
@NoArgsConstructor
//@RequiredArgsConstructor
public class ModelNTT {
    @Id
    private String name;
    private List<ClassNTT> classes = new ArrayList<>();
//    private List<String> associations = new ArrayList<>();
    private List<String> invariants = new ArrayList<>();

    public ModelNTT(String name) {
        this.name = name;
    }


    public void addClass(ClassNTT classDTO) {
        if (classDTO == null) {
            return;
        }
        if (this.classes == null) {
            this.classes = new ArrayList<>();
        }
        this.classes.add(classDTO);
    }

    public void addInvariant(String invariant) {
        if (invariant == null || invariant.trim().isEmpty()) {
            return;
        }
        if (this.invariants == null) {
            this.invariants = new ArrayList<>();
        }
        this.invariants.add(invariant);
    }

}
