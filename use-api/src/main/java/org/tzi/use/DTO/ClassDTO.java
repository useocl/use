package org.tzi.use.DTO;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class ClassDTO {

    @Id
    private String name_mclass;
    // Unidirectional OneToMany relationship
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mClass_id")
    private List<AttributeDTO> attributeDTOS = new ArrayList<>();
    // Unidirectional OneToMany relationship
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mClass_id")
    private List<OperationDTO> operationDTOS = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mClass_id")
    private List<PrePostConditionDTO> prePostConditionDTOS = new ArrayList<>();

    @OneToMany(cascade  = CascadeType.ALL, orphanRemoval  = true)
     @JoinColumn(name = "mClass_id")
    private List<InvariantDTO> invariantDTOS = new ArrayList<>();

    public String getName_mclass() {
        return name_mclass;
    }


    public List<AttributeDTO> getAttributes() {
        return attributeDTOS;
    }

    public List<OperationDTO> getOperations() {
        return operationDTOS;
    }

    public List<PrePostConditionDTO> getPrePostConditions() {
        return prePostConditionDTOS;
    }

    public List<InvariantDTO> getInvariants() {
        return invariantDTOS;
    }

    public void addAttribute(AttributeDTO attributeDTO) {
        attributeDTOS.add(attributeDTO);
    }

    public void removeAttribute(AttributeDTO attributeDTO) {
        attributeDTOS.remove(attributeDTO);
    }

    public void addOperation(OperationDTO operationDTO) {
        operationDTOS.add(operationDTO);
    }

    public void removeOperation(OperationDTO operationDTO) {
        operationDTOS.remove(operationDTO);
    }

    public void addPrePostCondition(PrePostConditionDTO condition) {
        prePostConditionDTOS.add(condition);
    }

    public void removePrePostCondition(PrePostConditionDTO condition) {
        prePostConditionDTOS.remove(condition);
    }

    public void addInvariant(InvariantDTO invariantDTO) {
        invariantDTOS.add(invariantDTO);
    }

    public void removeInvariant(InvariantDTO invariantDTO) {
        invariantDTOS.remove(invariantDTO);
    }



    //Constructors
    public ClassDTO(String name_mclass, List<AttributeDTO> attributeDTOS, List<OperationDTO> operationDTOS) {
        this.name_mclass = name_mclass;
        this.attributeDTOS = attributeDTOS;
        this.operationDTOS = operationDTOS;
    }
    public ClassDTO(String name_mclass, AttributeDTO attributes, OperationDTO operations) {
        this.name_mclass = name_mclass;
        this.attributeDTOS.add(attributes);
        this.operationDTOS.add(operations);
    }
    public ClassDTO(String name_mclass) {
        this.name_mclass = name_mclass;
    }
    public ClassDTO() {}

}
