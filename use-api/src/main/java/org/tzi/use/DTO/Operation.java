package org.tzi.use.DTO;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Operation {
    @Id
    private String name;

    private String body;

//    @ManyToOne
//    @JoinColumn(name = "name_mclass", nullable = false)
//    private ClassDTO mclass;


    // Getters and Setters

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public ClassDTO getMclass() {
//        return mclass;
//    }
//
//    public void setMclass(ClassDTO mclass) {
//        this.mclass = mclass;
//    }

    //Constructors
    public Operation(String name, String body) {
        this.name = name;
        this.body = body;
    }
    public Operation(String name, ClassDTO mclass) {
        this.name = name;
//        this.mclass = mclass;
    }
    public Operation(String name) {
        this.name = name;
    }
    public Operation() {}


}
