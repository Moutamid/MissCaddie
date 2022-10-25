package com.moutamid.misscaddie.models;

import android.os.Parcelable;

public class ServiceListModel {
    public static final Parcelable.Creator<ServiceListModel> CREATOR = null;
    String title, price;


    public ServiceListModel() {
    }

    public ServiceListModel(String title, String price) {
        this.title = title;
        this.price = price;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
