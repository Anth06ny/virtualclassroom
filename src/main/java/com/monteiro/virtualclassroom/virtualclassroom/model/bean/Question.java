package com.monteiro.virtualclassroom.virtualclassroom.model.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

@DatabaseTable(tableName = "questions")
public class Question {
    @DatabaseField(generatedId = true)
    private int id_question;

    @DatabaseField(canBeNull = false)
    private String question_content;

    @DatabaseField(canBeNull = false, foreign = true, columnName = "classroom_id")
    private Classroom classroom;

    @DatabaseField(canBeNull = false)
    private boolean isRadio =true;

    // list option param
    public List<Option> options;

    public Question(){};

    public Question(String question_content, boolean isRadio) {
        this.question_content = question_content;
        this.isRadio = isRadio;
    }

    public int getId_question() {
        return id_question;
    }

    public void setId_question(int id_question) {
        this.id_question = id_question;
    }

    public String getQuestion_content() {
        return question_content;
    }

    public void setQuestion_content(String question_content) {
        this.question_content = question_content;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public boolean getIsRadio() {
        return this.isRadio;
    }

    public void setRadio(boolean radio) {
        this.isRadio = radio;
    }

    // method  get and set of options
    public List<Option> getOptions() {
        return this.options;
    }
    public void setOptions(List<Option> options) {
        this.options = options;
    }
}
