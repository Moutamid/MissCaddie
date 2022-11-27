package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.misscaddie.models.CreditCard;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Token;
import com.stripe.model.Transfer;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.TransferCreateParams;

import java.util.HashMap;
import java.util.Map;


public class CardPaymentActivity extends AppCompatActivity {

    TextView submit;

    private TextView card_number;
    private TextView card_expire;
    private TextView card_cvv;
    private TextView card_name;
    private ProgressDialog dialog;

    private ImageView card_logo;

    private TextInputEditText et_card_number;
    private TextInputEditText et_expire;
    private TextInputEditText et_cvv;
    private TextInputEditText et_name;
    private boolean findFlag = false;
    int amount = 0;
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_card_payment);
        card_number = findViewById(R.id.card_number);
        card_expire = findViewById(R.id.card_expire);
        card_cvv = findViewById(R.id.card_cvv);
        card_name = findViewById(R.id.card_name);
        amount = getIntent().getIntExtra("price",0);
        id = getIntent().getStringExtra("id");

        card_logo = findViewById(R.id.card_logo);
        dialog = new ProgressDialog(CardPaymentActivity.this);
        et_card_number = findViewById(R.id.et_card_number);
        et_expire = findViewById(R.id.et_expire);
        et_cvv = findViewById(R.id.et_cvv);
        et_name = findViewById(R.id.et_name);
        submit = findViewById(R.id.submit);
        et_name.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

        et_expire.addTextChangedListener(MaskEditUtil.mask(et_expire, MaskEditUtil.FORMAT_DATE_CARD));

        et_card_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if (charSequence.toString().trim().length() == 0) {
                    card_number.setText("**** **** **** ****");
                    findFlag = false;
                    card_logo.setVisibility(View.INVISIBLE);
                } else {
                    String number = insertPeriodically(charSequence.toString().trim(), " ", 4);
                    card_number.setText(number);
                    if(!findFlag){
                        setFlagCard(number);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(et_card_number.getText().toString().equals("5")){
                    findFlag = false;
                    card_logo.setVisibility(View.INVISIBLE);
                }
            }
        });

        et_expire.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if (charSequence.toString().trim().length() == 0) {
                    card_expire.setText("MM/AA");
                } else {
                    String exp = insertPeriodically(charSequence.toString().trim(), "", 2);
                    card_expire.setText(exp);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        et_cvv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if (charSequence.toString().trim().length() == 0) {
                    card_cvv.setText("***");
                } else {
                    card_cvv.setText(charSequence.toString().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if (charSequence.toString().trim().length() == 0) {
                    card_name.setText("Enter owner name");
                } else {
                    card_name.setText(charSequence.toString().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(validate()){
                    String cardMonth = et_expire.getText().toString().substring(0,2);
                    String cardYear = et_expire.getText().toString().substring(3,5);
                    CreditCard card = new CreditCard(et_card_number.getText().toString(),
                            cardMonth,
                            cardYear,
                            et_cvv.getText().toString());

                    addCard(card);
                }

            }
        });
    }

    private void startCheckout() {

    }

    public boolean validate(){

        if(et_card_number.getText().toString().equals("") ||
                et_expire.getText().toString().equals("") ||
                et_cvv.getText().toString().equals("") ||
                et_name.getText().toString().equals("")){

            Toast.makeText(this, "Enter Card number!", Toast.LENGTH_SHORT).show();
            return false;
        } else if(et_card_number.getText().toString().length() != 16){
            Toast.makeText(this, "Card number must have 16 characters", Toast.LENGTH_SHORT).show();
            return false;
        } else if(!ValidateCardNumber.isValid(et_card_number.getText().toString())){
            Toast.makeText(this, "Invalid Card number!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void setFlagCard(String number){

        for(int i=0; i<number.length(); i++){
            char c = number.charAt(i);
            if(i == 0){
                if(c == '4'){
                    card_logo.setVisibility(View.VISIBLE);
                    card_logo.setImageResource(R.drawable.ic_visa_new);
                    findFlag = true;
                }
            } else if(i == 1){
                if(Integer.parseInt(et_card_number.getText().toString().substring(0,2)) > 50 && Integer.parseInt(et_card_number.getText().toString().substring(0,2)) < 56){
                    card_logo.setVisibility(View.VISIBLE);
                    card_logo.setImageResource(R.drawable.mastercard);
                    findFlag = true;
                }
            }
        }

    }

    public static String insertPeriodically(String text, String insert, int period) {
        StringBuilder builder = new StringBuilder(text.length() + insert.length() * (text.length() / period) + 1);
        int index = 0;
        String prefix = "";
        while (index < text.length()) {
            builder.append(prefix);
            prefix = insert;
            builder.append(text.substring(index, Math.min(index + period, text.length())));
            index += period;
        }
        return builder.toString();
    }


    private void addCard(CreditCard creditCard) {

        dialog.setTitle("Adding Card Information");
        dialog.setMessage("Please wait, while adding your card info.....");
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        long total = amount * 100;
        String clientId = "cus_MhKFyY6eUDXTYd";

        Stripe.apiKey = "";

        Map<String, Object> card = new HashMap<>();
        card.put("number", creditCard.getNumber());
        card.put("exp_month", creditCard.getExpiryMonth());
        card.put("exp_year", creditCard.getExpiryYear());
        card.put("cvc", creditCard.getCcv());
        Map<String, Object> params = new HashMap<>();
        params.put("card", card);

        try {
            Token token = Token.create(params);
            Log.d("Token",token.getId());
            PaymentIntentCreateParams params1 =
                    PaymentIntentCreateParams.builder()
                            .addPaymentMethodType("card")
                            .setAmount(total)
                            .setCurrency("usd")
                            .setTransferGroup("{ORDER10}")
                            .build();
            PaymentIntent paymentIntent = PaymentIntent.create(params1);
            Log.d("payment","" + paymentIntent.getAmount());
            DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("BankAccounts");
            db.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        String key = snapshot.child("token").getValue().toString();
                        TransferCreateParams transferParams =
                                TransferCreateParams.builder()
                                        .setAmount(7000L)
                                        .setCurrency("usd")
                                        .setDestination(key)
                                        .setTransferGroup("{ORDER10}")
                                        .build();

                        try {
                            Transfer transfer = Transfer.create(transferParams);
                            Log.d("Transfer1",transfer.getId());
                        } catch (StripeException e) {
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            TransferCreateParams secondTransferParams =
                    TransferCreateParams.builder()
                            .setAmount(2000L)
                            .setCurrency("usd")
                            .setDestination(clientId)
                            .setTransferGroup("{ORDER10}")
                            .build();
            Transfer secondTransfer = Transfer.create(secondTransferParams);
            Log.d("Transfer2",secondTransfer.getId());

            dialog.dismiss();
        } catch (StripeException e) {
            e.printStackTrace();
            Log.d("Error",e.getMessage());
        }

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CardPaymentActivity.this,Dashboard_Golfer.class));
        finish();
        Animatoo.animateZoom(CardPaymentActivity.this);
    }
}