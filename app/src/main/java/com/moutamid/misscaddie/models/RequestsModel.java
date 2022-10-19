package com.moutamid.misscaddie.models;

import java.util.List;

public class RequestsModel {
    String id, userId, price, status_title, date, address, message,service,caddieId;
    List<ServiceListModel> tableRows;

    public RequestsModel() {
    }

    public RequestsModel(String id, String userId, String status_title, String date, String address,
                         String message, String service,String caddieId) {
        this.id = id;
        this.userId = userId;
        this.status_title = status_title;
        this.date = date;
        this.address = address;
        this.message = message;
        this.service = service;
        this.caddieId = caddieId;
    }

    public String getCaddieId() {
        return caddieId;
    }

    public void setCaddieId(String caddieId) {
        this.caddieId = caddieId;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
