package org.tzi.use.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Operation {
    @Id
    private String head;

    private String body;

//    @ManyToOne
//    @JoinColumn(name = "name_mclass", nullable = false)
//    private UseClass mclass;


    // Getters and Setters

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

//    public UseClass getMclass() {
//        return mclass;
//    }
//
//    public void setMclass(UseClass mclass) {
//        this.mclass = mclass;
//    }

    //Constructors
    public Operation(String head, String body) {
        this.head = head;
        this.body = body;
    }
    public Operation(String head, UseClass mclass) {
        this.head = head;
//        this.mclass = mclass;
    }
    public Operation(String head) {
        this.head = head;
    }
    public Operation() {}


}
