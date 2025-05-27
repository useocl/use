package org.tzi.use.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class UseClass {

    @Id
    private String name_mclass;
    // Unidirectional OneToMany relationship
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mClass_id")
    private List<Attribute> attributes = new ArrayList<>();
    // Unidirectional OneToMany relationship
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mClass_id")
    private List<Operation> operations = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mClass_id")
    private List<PrePostCondition> prePostConditions = new ArrayList<>();

    @OneToMany(cascade  = CascadeType.ALL, orphanRemoval  = true)
     @JoinColumn(name = "mClass_id")
    private List<Invariant> invariants = new ArrayList<>();

    public String getName_mclass() {
        return name_mclass;
    }


    public List<Attribute> getAttributes() {
        return attributes;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public List<PrePostCondition> getPrePostConditions() {
        return prePostConditions;
    }

    public List<Invariant> getInvariants() {
        return invariants;
    }

    public void addAttribute(Attribute attribute) {
        attributes.add(attribute);
    }

    public void removeAttribute(Attribute attribute) {
        attributes.remove(attribute);
    }

    public void addOperation(Operation operation) {
        operations.add(operation);
    }

    public void removeOperation(Operation operation) {
        operations.remove(operation);
    }

    public void addPrePostCondition(PrePostCondition condition) {
        prePostConditions.add(condition);
    }

    public void removePrePostCondition(PrePostCondition condition) {
        prePostConditions.remove(condition);
    }

    public void addInvariant(Invariant invariant) {
        invariants.add(invariant);
    }

    public void removeInvariant(Invariant invariant) {
        invariants.remove(invariant);
    }



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
