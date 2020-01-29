package com.monteiro.virtualclassroom.virtualclassroom.model.bean;

// imports
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;



import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;


//annotations ORMLite
@DatabaseTable(tableName = "USERS")
public class User {


    @DatabaseField(generatedId = true)
    private int id_user;

    @DatabaseField(canBeNull = false)
    private String user_name;

    @DatabaseField(canBeNull = false)
    private String user_email;

    @DatabaseField(canBeNull = false)
    private String  user_password;

    @DatabaseField(canBeNull = false)
    private String user_lastname;

    @DatabaseField
    private boolean isAdmin;

    @DatabaseField (canBeNull = false
//            foreign = true, foreignColumnName = "id_classroom", foreignAutoCreate = true
    )
    private long id_classroom;

    // mandatory no-argument-constructor for ORMlite
    public User() {
    }

    // constructor
    public User(String name, String lastname, String email, String password, boolean isAdmin, long classroom) {
        this.user_name = name;
        this.user_lastname = lastname;
        this.user_email = email;
        this.user_password = password;
        this.isAdmin = isAdmin;
        this.id_classroom = classroom;
    }

    // setter/getter id_user
    public int getUser_id() {
        return id_user;
    }

    public void setUser_id(int id_user) {
        this.id_user = id_user;
    }

    // setter/getter lastname
    public String getUser_lastname() {
        return user_lastname;
    }

    public void setUser_lastname(String user_lastname) {
        this.user_lastname = user_lastname;
    }

    // setter/getter password
    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        this.user_password = user_password;
    }


    // setter/getter email
    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    // setter/getter name
    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    //setter/getter isAdmin
    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        isAdmin = false;
    }

    // setter/getter id_classroom
    public long get_classroom() {
        return id_classroom;
    }

    public void set_classroom(int classroom) {
        this.id_classroom = classroom;
    }

}
