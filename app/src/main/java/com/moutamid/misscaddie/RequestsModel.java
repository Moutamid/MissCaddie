package com.moutamid.misscaddie;

import java.util.List;

public class RequestsModel {
    String image, name, price, status_title, date, address;
    List<ServiceListModel> tableRows;

    public RequestsModel() {
    }

    public RequestsModel(String image, String name, String price, String status_title, String date, String address, List<ServiceListModel> tableRows) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.status_title = status_title;
        this.date = date;
        this.address = address;
        this.tableRows = tableRows;
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

    public String getStatus_title() {
        return status_title;
    }

    public void setStatus_title(String status_title) {
        this.status_title = status_title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ServiceListModel> getTableRows() {
        return tableRows;
    }

    public void setTableRows(List<ServiceListModel> tableRows) {
        this.tableRows = tableRows;
    }
}
