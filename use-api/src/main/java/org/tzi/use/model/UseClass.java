package org.tzi.use.model;

import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class UseClass {

    @Id
    private String id;

    private String name_mclass;
    // Unidirectional OneToMany relationship
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mClass_id") // Foreign key column in the Employee table
    private List<Attribute> attributes = new ArrayList<>();
    // Unidirectional OneToMany relationship
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mClass_id") // Foreign key column in the Employee table
    private List<Operation> operations = new ArrayList<>();

//    @OneToMany(mappedBy = "children", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<UseClass> parents = new ArrayList<>();

//    @OneToMany(mappedBy = "associations", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<UseClass> associations = new ArrayList<>();


    // Getters and Setters

    public String getName_mclass() {
        return name_mclass;
    }

    public void setName_mclass(String name_mclass) {
        this.name_mclass = name_mclass;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }
//
//    public void setAttributes(List<Attribute> attributes) {
//        this.attributes = attributes;
//    }
//
    public List<Operation> getOperations() {
        return operations;
    }
//
//    public void setOperations(List<Operation> operations) {
//        this.operations = operations;
//    }

    //Constructors
    public UseClass(String name_mclass, List<Attribute> attributes, List<Operation> operations) {
        this.name_mclass = name_mclass;
        this.attributes = attributes;
        this.operations = operations;
    }
    public UseClass(String name_mclass, Attribute attributes, Operation operations) {
        this.name_mclass = name_mclass;
        this.attributes.add(attributes);
        this.operations.add(operations);
    }
    public UseClass(String name_mclass) {
        this.name_mclass = name_mclass;
    }
    public UseClass() {}

}
