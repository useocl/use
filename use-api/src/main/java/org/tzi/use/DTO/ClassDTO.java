package org.tzi.use.DTO;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassDTO {

    @Id
    private String id;
    private String name;

    @DBRef
    @Builder.Default
    private List<AttributeDTO> attributeDTOS = new ArrayList<>();

    @DBRef
    @Builder.Default
    private List<OperationDTO> operationDTOS = new ArrayList<>();

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

    // Additional constructor
    public ClassDTO(String id, String name, AttributeDTO attributes, OperationDTO operations) {
        this.id = id;
        this.name = name;
        this.attributeDTOS = new ArrayList<>();
        this.operationDTOS = new ArrayList<>();
        if (attributes != null) this.attributeDTOS.add(attributes);
        if (operations != null) this.operationDTOS.add(operations);
    }

    // Convenience constructor
    public ClassDTO(String id, String name) {
        this.id = id;
        this.name = name;
        this.attributeDTOS = new ArrayList<>();
        this.operationDTOS = new ArrayList<>();
    }
}
