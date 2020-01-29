package com.monteiro.virtualclassroom.virtualclassroom.model.bean;

public class Information {
    private int id_information;
    private String information_label;
    private String information_url;

    public Information() {}

    public Information(String information_label, String information_url) {
        this.information_label = information_label;
        this.information_url = information_url;
    }

    public int getId_information() {
        return id_information;
    }

    public void setId_information(int id_information) {
        this.id_information = id_information;
    }

    public String getInformation_label() {
        return information_label;
    }

    public void setInformation_label(String information_label) {
        this.information_label = information_label;
    }

    public String getInformation_url() {
        return information_url;
    }

    public void setInformation_url(String information_url) {
        this.information_url = information_url;
    }
}
