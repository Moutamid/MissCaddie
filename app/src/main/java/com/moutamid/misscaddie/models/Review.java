package com.moutamid.misscaddie.models;

public class Review {

    private String id;
    private String caddieId;
    private String golferId;
    private float rating;
    private String feedback;
    private String date;

    private Review(){

    }

    public Review(String id, String caddieId, String golferId, float rating, String feedback,String date) {
        this.id = id;
        this.caddieId = caddieId;
        this.golferId = golferId;
        this.rating = rating;
        this.feedback = feedback;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaddieId() {
        return caddieId;
    }

    public void setCaddieId(String caddieId) {
        this.caddieId = caddieId;
    }

    public String getGolferId() {
        return golferId;
    }

    public void setGolferId(String golferId) {
        this.golferId = golferId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
