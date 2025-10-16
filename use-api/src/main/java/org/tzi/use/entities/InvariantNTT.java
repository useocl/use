package org.tzi.use.entities;

import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("invariant")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvariantNTT {
    @Id
    private String invName;
    private String invBody;
    private boolean isExistential;
}
