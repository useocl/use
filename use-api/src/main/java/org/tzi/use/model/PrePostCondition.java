package org.tzi.use.model;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PrePostCondition {
    @Id
    private String operationName;
    private String name;
    private String condition;
    private boolean isPre;

    public PrePostCondition() {
    }

    public PrePostCondition(String operationName, String name, String condition, boolean isPre) {
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
