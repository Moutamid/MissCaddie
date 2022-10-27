package com.moutamid.misscaddie.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class RequestsModel implements Parcelable {
    String id, userId, status_title, date, address, message,time,caddieId;
    boolean payment;
    List<ServiceListModel> tableRows;

    public RequestsModel() {
    }

    public RequestsModel(String id, String userId, String status_title, String date,
                         String time,String address, String message, String caddieId,List<ServiceListModel> tableRows) {
        this.id = id;
        this.userId = userId;
        this.status_title = status_title;
        this.date = date;
        this.time = time;
        this.address = address;
        this.message = message;
        this.caddieId = caddieId;
        this.tableRows = tableRows;
    }

    protected RequestsModel(Parcel in) {
        id = in.readString();
        userId = in.readString();
        status_title = in.readString();
        date = in.readString();
        address = in.readString();
        message = in.readString();
        time = in.readString();
        caddieId = in.readString();
        payment = in.readByte() != 0;
    }

    public static final Creator<RequestsModel> CREATOR = new Creator<RequestsModel>() {
        @Override
        public RequestsModel createFromParcel(Parcel in) {
            return new RequestsModel(in);
        }

        @Override
        public RequestsModel[] newArray(int size) {
            return new RequestsModel[size];
        }
    };

    public boolean isPayment() {
        return payment;
    }

    public void setPayment(boolean payment) {
        this.payment = payment;
    }



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<ServiceListModel> getTableRows() {
        return tableRows;
    }

    public void setTableRows(List<ServiceListModel> tableRows) {
        this.tableRows = tableRows;
    }


    public String getCaddieId() {
        return caddieId;
    }

    public void setCaddieId(String caddieId) {
        this.caddieId = caddieId;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(userId);
        parcel.writeString(status_title);
        parcel.writeString(date);
        parcel.writeString(address);
        parcel.writeString(message);
        parcel.writeString(time);
        parcel.writeString(caddieId);
        parcel.writeByte((byte) (payment ? 1 : 0));
    }
}
