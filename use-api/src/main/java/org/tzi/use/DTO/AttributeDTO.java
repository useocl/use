package org.tzi.use.DTO;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttributeDTO {
    @Id
    private String id;
    private String name;
    private String type;

    // Constructors for backward compatibility
    public AttributeDTO(String name, String type){
        this.name = name;
        this.type = type;
    }

    public AttributeDTO(String id, String name, String type, ClassDTO mclass) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
}
