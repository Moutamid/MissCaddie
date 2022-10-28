package com.moutamid.misscaddie.models;

public class BankDetails {

    private String id;
    private String account_holder_name;
    private String account_holder_type;
    private String routing_number;
    private String account_number;
    private String country;
    private String currency;

    public BankDetails(String id, String account_holder_name, String account_holder_type, String routing_number, String account_number, String country, String currency) {
        this.id = id;
        this.account_holder_name = account_holder_name;
        this.account_holder_type = account_holder_type;
        this.routing_number = routing_number;
        this.account_number = account_number;
        this.country = country;
        this.currency = currency;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount_holder_name() {
        return account_holder_name;
    }

    public void setAccount_holder_name(String account_holder_name) {
        this.account_holder_name = account_holder_name;
    }

    public String getAccount_holder_type() {
        return account_holder_type;
    }

    public void setAccount_holder_type(String account_holder_type) {
        this.account_holder_type = account_holder_type;
    }

    public String getRouting_number() {
        return routing_number;
    }

    public void setRouting_number(String routing_number) {
        this.routing_number = routing_number;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
