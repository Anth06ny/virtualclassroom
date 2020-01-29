package com.monteiro.virtualclassroom.virtualclassroom.model.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


public class UserBean {

    @DatabaseField(id = true)
    private Long id;

    @DatabaseField
    private String name;

    @DatabaseField
    private String surname;



    //Constructeur vide obligatoire avec cette ORM
    public UserBean() {
    }

    public UserBean(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
