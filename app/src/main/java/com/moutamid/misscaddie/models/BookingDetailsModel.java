package com.moutamid.misscaddie.models;

public class BookingDetailsModel {
    String personImage, personName, date, location, message, services;

    public BookingDetailsModel() {
    }

    public BookingDetailsModel(String personImage, String personName, String date, String location, String message, String services) {
        this.personImage = personImage;
        this.personName = personName;
        this.date = date;
        this.location = location;
        this.message = message;
        this.services = services;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getPersonImage() {
        return personImage;
    }

    public void setPersonImage(String personImage) {
        this.personImage = personImage;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
