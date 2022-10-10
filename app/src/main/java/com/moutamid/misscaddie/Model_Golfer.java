package com.moutamid.misscaddie;

public class Model_Golfer {
    String name , price , length , catagory , place , reviews , status;
    int image;

    public Model_Golfer() {
    }

    public Model_Golfer(String name, String price, String length, String catagory, String place, String reviews, String status, int image) {
        this.name = name;
        this.price = price;
        this.length = length;
        this.catagory = catagory;
        this.place = place;
        this.reviews = reviews;
        this.status = status;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
