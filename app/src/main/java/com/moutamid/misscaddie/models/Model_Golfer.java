package com.moutamid.misscaddie.models;

public class Model_Golfer {
    String id;
    String name , price,email, password , length , catagory , place , reviews , status;
    String image;

    public Model_Golfer() {
    }

    public Model_Golfer(String id,String name, String price, String length, String catagory,
                        String place, String reviews, String status, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.length = length;
        this.catagory = catagory;
        this.place = place;
        this.reviews = reviews;
        this.status = status;
        this.image = image;
    }

    public Model_Golfer(String id, String name, String email, String password,String image) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
