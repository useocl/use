package org.tzi.use.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
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
    private List<ClassDTO> classes = new ArrayList<>();
//    private List<String> associations = new ArrayList<>();
    private List<String> invariants = new ArrayList<>();

    public ModelNTT(String name) {
        this.name = name;
    }
}
