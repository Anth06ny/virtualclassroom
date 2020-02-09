package com.monteiro.virtualclassroom.virtualclassroom.model.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable( tableName = "answers")
public class Answer {
    @DatabaseField(columnName = "id_answer", generatedId = true)
    private long id_answer;
    @DatabaseField(canBeNull = false, foreign = true, columnName = "id_user")
    private User user;

    @DatabaseField(canBeNull = false, foreign = true, columnName = "id_option")
    private Option option;


    public Answer(){
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }

    public int getId_userFromOption(int id_option) {
        return user.getUser_id();
    }

    public int getId_optionFromAnswer(int id_question) {
        return option.getId_option();
    }


}







