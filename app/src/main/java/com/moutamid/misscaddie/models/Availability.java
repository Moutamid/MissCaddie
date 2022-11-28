package com.moutamid.misscaddie.models;

public class Availability {

    private String date;
    private boolean available;

    public Availability() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
