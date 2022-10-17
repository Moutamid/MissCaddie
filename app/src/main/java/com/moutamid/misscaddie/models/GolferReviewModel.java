package com.moutamid.misscaddie.models;

public class GolferReviewModel {
    String image, name, date, message;
    int image2;

    public GolferReviewModel() {
    }

    public GolferReviewModel(String image, String name, String date, String message) {
        this.image = image;
        this.name = name;
        this.date = date;
        this.message = message;
    }

    public GolferReviewModel(String name, String date, String message, int image2) {
        this.name = name;
        this.date = date;
        this.message = message;
        this.image2 = image2;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getImage2() {
        return image2;
    }

    public void setImage2(int image2) {
        this.image2 = image2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
