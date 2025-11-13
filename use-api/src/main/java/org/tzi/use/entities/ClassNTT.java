package org.tzi.use.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.tzi.use.DTO.AssociationDTO;
import org.tzi.use.DTO.AttributeDTO;
import org.tzi.use.DTO.OperationDTO;

import java.util.ArrayList;
import java.util.List;

@Document("class")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassNTT {
    @Id
    private String name;
    private List<AttributeNTT> attributes = new ArrayList<>();
    private List<OperationNTT> operations = new ArrayList<>();
    private List<AssociationNTT> associations = new ArrayList<>();
}
