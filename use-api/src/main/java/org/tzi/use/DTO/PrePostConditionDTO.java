package org.tzi.use.DTO;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PrePostConditionDTO {
    @Id
    private String operationName;
    private String name;
    private String condition;
    private boolean isPre;

    public PrePostConditionDTO() {
    }

    public PrePostConditionDTO(String operationName, String name, String condition, boolean isPre) {
        this.operationName = operationName;
        this.name = name;
        this.condition = condition;
        this.isPre = isPre;


    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public boolean isPre() {
        return isPre;
    }

    public void setPre(boolean pre) {
        isPre = pre;
    }
}
