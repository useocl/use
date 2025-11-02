package org.tzi.use.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssociationDTO {

    private String name;
    private List<String> roleNames;
    private Set<String> associatedClassNames;
}


enum AggregationKind {
    NONE("association"),
    AGGREGATION("aggregation"),
    COMPOSITION("composition");

    private final String displayName;

    AggregationKind(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}