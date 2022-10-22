package com.moutamid.misscaddie.models;

import com.google.gson.annotations.SerializedName;
import com.stripe.android.model.Card;

public class CreditCard extends Card {

    private String holderName;
    private String number;
    private String expiryMonth;
    private String expiryYear;
    private String ccv;

    public CreditCard(String number, Integer expMonth, Integer expYear, String cvc) {
        super(number, expMonth, expYear, cvc);
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(String expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public String getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(String expiryYear) {
        this.expiryYear = expiryYear;
    }

    public String getCcv() {
        return ccv;
    }

    public void setCcv(String ccv) {
        this.ccv = ccv;
    }
}
