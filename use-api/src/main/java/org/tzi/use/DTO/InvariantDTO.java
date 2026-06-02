package org.tzi.use.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvariantDTO {

    private String invName;
    private String invBody;
    private boolean isExistential;

}
