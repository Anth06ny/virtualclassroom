package com.monteiro.virtualclassroom.virtualclassroom.model.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "options")
public class Option {

    @DatabaseField(generatedId = true)
    private int id_option;

    @DatabaseField(canBeNull = false)
    private String option_content;

    @DatabaseField(foreign = true, columnName = "id_question")
    private Question question;

    public Option() {
    }

    public Option(String option_content) {
        this.option_content = option_content;
    }

    public int getId_option() {
        return id_option;
    }

    public String getOption_content() {
        return option_content;
    }

    public void setOption_content(String option_content) {
        this.option_content = option_content;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

}
