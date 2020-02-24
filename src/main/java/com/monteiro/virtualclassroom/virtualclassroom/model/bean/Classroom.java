package com.monteiro.virtualclassroom.virtualclassroom.model.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "classrooms")
public class Classroom {

    @DatabaseField(columnName = "id_classroom", generatedId = true)
    private long id_classroom;

    @DatabaseField(columnName = "classroom_name")
    private String classroom_name;

    public Classroom() {
    }

    public Classroom(String classroom_name) {
        this.classroom_name = classroom_name;
    }

    public long getId_classroom() {
        return id_classroom;
    }

    public void setId_classroom(long id_classroom) {
        this.id_classroom = id_classroom;
    }

    public String getClassroom_name() {
        return classroom_name;
    }

    public void setClassroom_name(String classroom_name) {
        this.classroom_name = classroom_name;
    }

}
