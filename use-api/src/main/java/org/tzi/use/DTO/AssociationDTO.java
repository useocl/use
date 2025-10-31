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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssociationDTO {
    private String name;

//    @Valid
//    private List<AssociationEndDTO> associationEnds;

    private List<String> roleNames;
    private Set<String> associatedClassNames;
}
