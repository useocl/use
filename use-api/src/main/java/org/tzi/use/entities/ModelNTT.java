package org.tzi.use.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.tzi.use.DTO.ClassDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Document("model")
@Data
@AllArgsConstructor
@NoArgsConstructor
//@RequiredArgsConstructor
public class ModelNTT {
    @Id
    private String name;
    private List<ClassNTT> classes = new ArrayList<>();
    private Map<String,AssociationNTT> associations = new TreeMap<>();
    private Map<String, InvariantNTT> invariants = new TreeMap<>();
    private Map<String,PrePostConditionNTT> prePostConditions = new TreeMap<>();

    public ModelNTT(String name) {
        this.name = name;
    }


    public void addClass(ClassNTT classNTT) {
        if (classNTT == null) {
            return;
        }
        if (this.classes == null) {
            this.classes = new ArrayList<>();
        }
        this.classes.add(classNTT);
    }

}
