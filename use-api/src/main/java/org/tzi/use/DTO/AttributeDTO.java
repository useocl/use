package org.tzi.use.DTO;

import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
    public class AttributeDTO {
        @Id
        private String name_attr;
        private String type;

//    @ManyToOne
//    @JoinColumn(name = "name_mclass", nullable = false)
//    private ClassDTO mclass;


    // Getters and Setters

    public String getName_attr() {
        return name_attr;
    }

    public void setName_attr(String name_attr) {
        this.name_attr = name_attr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

//    public ClassDTO getMclass() {
//        return mclass;
//    }
//
//    public void setMclass(ClassDTO mclass) {
//        this.mclass = mclass;
//    }

    //Constructors
    public AttributeDTO(String name_attr, String type){
        this.name_attr = name_attr;
        this.type = type;
    }

    public AttributeDTO(String name, String string, ClassDTO mclass) {
        this.name_attr = name;
        this.type = string;
//        this.mclass = mclass;
    }

    public AttributeDTO(){}
}
