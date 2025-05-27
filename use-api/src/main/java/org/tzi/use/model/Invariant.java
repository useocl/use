package org.tzi.use.model;

import jakarta.persistence.Id;

public class Invariant {

    @Id
    private String invName;
    private String invBody;
    private boolean isExistential;

    public Invariant() {

    }

    public Invariant(String invName, String invBody, boolean isExistential) {
        this.invName = invName;
        this.invBody = invBody;
        this.isExistential = isExistential;
    }

    public String getInvName() {
        return invName;
    }

    public String getInvBody() {
        return invBody;
    }

    public boolean isExistential() {
        return isExistential;
    }

    public void setInvName(String invName) {
        this.invName = invName;
    }

    public void setInvBody(String invBody) {
        this.invBody = invBody;
    }

    public void setExistential(boolean existential) {
        isExistential = existential;
    }
}
