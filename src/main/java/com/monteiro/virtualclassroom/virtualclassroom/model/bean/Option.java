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

    public Option() {}
    public Option(String option_content, Question question) {
        this.option_content = option_content;
        this.question = question;
    }

    public int getId_option() {
        return id_option;
    }

    public int getId_optionByQuestion(int id_question) {
        return id_option;
    }

    public void setId_option(int id_option) {
        this.id_option = id_option;
    }

    public String getOption_content(int id_question) {
        return option_content;
    }

    public void setOption_content(String option_content) {
        this.option_content = option_content;
    }

    public Question getQuestion() {
        return question;
    }

    public void setId_question(Question question) {
        this.question = question;
    }
}
