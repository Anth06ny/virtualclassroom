package com.monteiro.virtualclassroom.virtualclassroom.model.bean;

// imports

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.monteiro.virtualclassroom.virtualclassroom.model.Security.AeSimpleSHA1;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;


@DatabaseTable(tableName = "USERS")
public class User {

    @DatabaseField(generatedId = true)
    private int id_user;

    @DatabaseField(canBeNull = false)
    private String user_name;

    @DatabaseField(canBeNull = false)
    private String user_lastname;

    @DatabaseField(canBeNull = false)
    private String user_email;

    @DatabaseField(canBeNull = false)
    private String user_password;

    @DatabaseField
    private boolean isAdmin;

    @DatabaseField(foreign = true, columnName = "id_classroom")
    private Classroom classroom;

    // mandatory no-argument-constructor for ORMlite
    public User() {
    }

    // constructor
    public User(String name, String lastname, String email, boolean isAdmin) {
        user_name = name;
        user_lastname = lastname;
        user_email = email;
        this.isAdmin = isAdmin;
    }

    public static String hashPassword(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return AeSimpleSHA1.SHA1(password);
    }

    // setter/getter id_user
    public int getUser_id() {
        return id_user;
    }

    public void setUser_id(int id_user) {
        this.id_user = id_user;
    }

    // setter/getter name
    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    // setter/getter lastname
    public String getUser_lastname() {
        return user_lastname;
    }

    public void setUser_lastname(String user_lastname) {
        this.user_lastname = user_lastname;
    }

    // setter/getter email
    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    // setter/getter password
    public String getUser_password() {
        return user_password;
    }

    public void setPassword(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        user_password = hashPassword(password);
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    //setter/getter isAdmin
    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        isAdmin = false;
    }

    // setter/getter id_classroom
    public long get_UserClassroomId() {
        return classroom.getId_classroom();
    }

}
