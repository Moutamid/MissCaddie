package com.moutamid.misscaddie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moutamid.misscaddie.databinding.ActivityCaddieBankDetailsBinding;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Token;

import java.util.HashMap;
import java.util.Map;

public class CaddieBankDetailsActivity extends AppCompatActivity {

    private ActivityCaddieBankDetailsBinding b;
    private String name,number,routing_number;
    private String type = "";
    private DatabaseReference db;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        b = ActivityCaddieBankDetailsBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        db = FirebaseDatabase.getInstance().getReference().child("BankAccounts");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        b.spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        b.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = b.name.getText().toString();
                number = b.number.getText().toString();
                routing_number = b.routingNumber.getText().toString();

                if (!name.isEmpty() && !number.isEmpty() && !routing_number.isEmpty() && !type.equals("")){
                    saveData();
                }
            }
        });
    }

    private void saveData() {


        //Stripe.apiKey = "sk_test_51LxZuNLymI2iQWRAajXZzf99LstDowO1MAQYH9KEO3Dna899Gknv2vUcuVY3l1mNL0WgfFdpkZLiXbbK4bXP65JY006NYVTs8y";
        Stripe.apiKey = "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";

        Map<String, Object> bankAccount = new HashMap<>();
        bankAccount.put("country", "US");
        bankAccount.put("currency", "usd");
        bankAccount.put(
                "account_holder_name",
                name
        );
        bankAccount.put(
                "account_holder_type",
                type
        );
        bankAccount.put("routing_number", routing_number);
        bankAccount.put("account_number", number);
        Map<String, Object> params = new HashMap<>();
        params.put("bank_account", bankAccount);

        try {
            Token token = Token.create(params);
            Log.d("Token",token.getId());
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("token",token.getId());
            db.child(user.getUid()).setValue(hashMap);
            Toast.makeText(this, "Added Successfully!", Toast.LENGTH_SHORT).show();
        } catch (StripeException e) {
            e.printStackTrace();
            Log.d("Error",e.getMessage());
        }
    }
}