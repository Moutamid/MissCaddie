package com.moutamid.misscaddie;

import com.paypal.android.MEP.PayPalResultDelegate;
import com.stripe.model.Card;

import java.io.Serializable;

public class ResultDelegate implements PayPalResultDelegate, Serializable {
    private static String paymentID;

    public ResultDelegate(){
        super();
    }
    public ResultDelegate(String paymentID){
        super();
        this.paymentID = paymentID;
    }

    public String getPaymentID(){
        return this.paymentID;
    }

    public void onPaymentSucceeded(String payKey, String paymentStatus) {
        // TODO: update database marking payment as successful using paymentID
        CardPaymentActivity.resultTitle = "SUCCESS";
        CardPaymentActivity.resultInfo = "You have successfully completed your transaction.";
        CardPaymentActivity.resultExtra = "Key: " + payKey;
    }


    public void onPaymentFailed(String paymentStatus, String correlationID,
                                String payKey, String errorID, String errorMessage) {
        // TODO: update database marking payment as failed using paymentID
        CardPaymentActivity.resultTitle = "FAILURE";
        CardPaymentActivity.resultInfo = errorMessage;
        CardPaymentActivity.resultExtra = "Error ID: " + errorID + "\nCorrelation ID: "
                + correlationID + "\nPay Key: " + payKey;
    }


    public void onPaymentCanceled(String paymentStatus) {
        // TODO: update database marking payment as failed using paymentID
        CardPaymentActivity.resultTitle = "CANCELED";
        CardPaymentActivity.resultInfo = "The transaction has been cancelled.";
        CardPaymentActivity.resultExtra = "";
    }
}