package com.moutamid.misscaddie.models;

public class IncomingMessageModel {
    String image, name, last_message, date;

    public IncomingMessageModel() {
    }

    public IncomingMessageModel(String image, String name, String last_message, String date) {
        this.image = image;
        this.name = name;
        this.last_message = last_message;
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_message() {
        return last_message;
    }

    public void setLast_message(String last_message) {
        this.last_message = last_message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
