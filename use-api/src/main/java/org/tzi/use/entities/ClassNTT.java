package org.tzi.use.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassNTT {
    private String name;
    private List<AttributeNTT> attributes = new ArrayList<>();
    private List<OperationNTT> operations = new ArrayList<>();
    private List<AssociationNTT> associations = new ArrayList<>();
}
