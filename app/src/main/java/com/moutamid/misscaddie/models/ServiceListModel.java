package com.moutamid.misscaddie.models;

import android.os.Parcelable;

public class ServiceListModel {
    String id;
    String title, price;
    boolean isSelected;


    public ServiceListModel() {
    }

    public ServiceListModel(String id,String title, String price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
