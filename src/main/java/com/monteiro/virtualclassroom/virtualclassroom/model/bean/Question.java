package com.monteiro.virtualclassroom.virtualclassroom.model.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "questions")
public class Question {
    @DatabaseField(generatedId = true)
    private int id_question;

    @DatabaseField(canBeNull = false)
    private String question_content;

    @DatabaseField(canBeNull = false)
    private long id_classroom;

    @DatabaseField(canBeNull = false)
    private boolean isRadio;

    public Question(){};

    public Question(String question_content,long id_classroom,boolean isRadio) {
        this.question_content = question_content;
        this.id_classroom = id_classroom;
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

    public long getId_classroom() {
        return id_classroom;
    }

    public void setId_classroom(long id_classroom) {
        this.id_classroom = id_classroom;
    }

    public boolean getIsRadio() {
        return isRadio;
    }

    public void setRadio(boolean radio) {
        isRadio = radio;
    }
}
