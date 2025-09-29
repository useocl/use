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
public class OperationDTO {
    @Id
    private String id;
    private String name;
    private String body;

    // Constructors for backward compatibility
    public OperationDTO(String name, String body) {
        this.name = name;
        this.body = body;
    }

    public OperationDTO(String id, String name, ClassDTO mclass) {
        this.id = id;
        this.name = name;
    }

    public OperationDTO(String name) {
        this.name = name;
    }
}
