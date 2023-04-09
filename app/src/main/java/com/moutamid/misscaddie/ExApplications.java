package com.moutamid.misscaddie;

import android.app.Application;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paypal.checkout.PayPalCheckout;
import com.paypal.checkout.config.CheckoutConfig;
import com.paypal.checkout.config.Environment;
import com.paypal.checkout.config.PaymentButtonIntent;
import com.paypal.checkout.config.SettingsConfig;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.UserAction;

public class ExApplications extends Application {

    DatabaseReference db;

    @Override
    public void onCreate() {
        super.onCreate();
        db = FirebaseDatabase.getInstance().getReference().child("PayPal");
        getStripeDetails();
        //BuildConfig.APPLICATION_ID + "://paypalpay",
    }

    private void getStripeDetails() {
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String clientId = snapshot.child("clientId").getValue().toString();
                    PayPalCheckout.setConfig(new CheckoutConfig(
                            ExApplications.this,
                            clientId,
                            Environment.LIVE,
                            CurrencyCode.USD,
                            UserAction.PAY_NOW,
                            PaymentButtonIntent.AUTHORIZE,
                            new SettingsConfig(
                                    true,
                                    false
                            )
                    ));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
