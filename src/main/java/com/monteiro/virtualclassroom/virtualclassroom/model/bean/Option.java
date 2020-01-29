package com.monteiro.virtualclassroom.virtualclassroom.model.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "options")
public class Option {
    @DatabaseField(generatedId = true)
    private int id_option;

    @DatabaseField(canBeNull = false)
    private String option_content;

    @DatabaseField(canBeNull = false)
    private int id_question;

    public Option() {}
    public Option(String option_content){
        this.option_content=option_content;
    };
    public Option(String option_content, int id_question) {
        this.option_content = option_content;
        this.id_question = id_question;
    }

    public int getId_option() {
        return id_option;
    }

    public void setId_option(int id_option) {
        this.id_option = id_option;
    }

    public String getOption_content() {
        return option_content;
    }

    public void setOption_content(String option_content) {
        this.option_content = option_content;
    }
    public int getId_question() {
        return id_question;
    }

    public void setId_question(int id_question) {
        this.id_question = id_question;
    }
}
