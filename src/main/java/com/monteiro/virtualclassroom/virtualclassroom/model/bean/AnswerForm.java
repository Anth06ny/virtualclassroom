package com.monteiro.virtualclassroom.virtualclassroom.model.bean;


public class AnswerForm {

    int[] checkboxOptions;

    int radioOption;


    public AnswerForm() {
    }


    public int[] getCheckboxOptions() {
        return checkboxOptions;
    }

    public void setCheckboxOptions(int[] checkboxOptions) {
        this.checkboxOptions = checkboxOptions;
    }

    public int getRadioOption() {
        return radioOption;
    }

    public void setRadioOption(int radioOption) {
        this.radioOption = radioOption;
    }
}
