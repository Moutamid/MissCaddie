package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.misscaddie.databinding.ActivityCaddieBankDetailsBinding;
import com.moutamid.misscaddie.models.Model_Caddie;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.Token;

import java.util.HashMap;
import java.util.Map;

public class CaddieBankDetailsActivity extends AppCompatActivity {

    private ActivityCaddieBankDetailsBinding b;
    private String name,number,routing_number,ssn;
    private String type = "";
    private DatabaseReference db,db1;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private SharedPreferencesManager manager;
    private String apiKey = "";
    ImageView backBtn;
    private String[] typeList = {"Select Type (Individual)","Individual","Company"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        b = ActivityCaddieBankDetailsBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        db = FirebaseDatabase.getInstance().getReference().child("BankAccounts");
        db1 = FirebaseDatabase.getInstance().getReference().child("Caddie");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        manager = new SharedPreferencesManager(CaddieBankDetailsActivity.this);
        apiKey = manager.retrieveString("apiKey","");
        name = manager.retrieveString("name","");
        type = manager.retrieveString("type","");
        number = manager.retrieveString("number","");
        routing_number = manager.retrieveString("routing","");
        ssn = manager.retrieveString("ssn","");

        if (!name.equals("") && !type.equals("") && !number.equals("") && !ssn.equals("")){
            b.infoLayout.setVisibility(View.GONE);
            b.detailsLayout.setVisibility(View.VISIBLE);
            b.nametxt.setText(name);
            b.type.setText(type);
            b.accountNumber.setText(number);
            b.ssnNo.setText("SSN # " + ssn);
        }else {
            b.infoLayout.setVisibility(View.VISIBLE);
            b.detailsLayout.setVisibility(View.GONE);
        }

        b.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                Animatoo.animateSwipeLeft(CaddieBankDetailsActivity.this);
            }
        });

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
                if (validInfo()){
                    createConnectedAccount(name,type,number,ssn);
                }
            }
        });

        b.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.infoLayout.setVisibility(View.VISIBLE);
                b.detailsLayout.setVisibility(View.GONE);
                getDetails();
            }
        });
    }


    public boolean validInfo() {

        name = b.name.getText().toString();
        number = b.number.getText().toString();
        routing_number = b.routingNumber.getText().toString();
        ssn = b.ssn.getText().toString();

        if(name.isEmpty()){
            b.name.setError("Input Account Holder Name");
            b.name.requestFocus();
            return false;
        }

        if(number.isEmpty()){
            b.number.setError("Input Account Number");
            b.number.requestFocus();
            return false;
        }
        if(routing_number.isEmpty()){
            b.routingNumber.setError("Input Routing Number");
            b.routingNumber.requestFocus();
            return false;
        }
        if(ssn.isEmpty()){
            b.ssn.setError("Input SSN Number");
            b.ssn.requestFocus();
            return false;
        }

        if (Integer.parseInt(number) < 12){
            Toast.makeText(CaddieBankDetailsActivity.this,"Account Number must have 12 digits",Toast.LENGTH_LONG).show();
            return false;
        }
        if (Integer.parseInt(routing_number) < 9){
            Toast.makeText(CaddieBankDetailsActivity.this,"Account Number must have 12 digits",Toast.LENGTH_LONG).show();
            return false;
        }
        if (Integer.parseInt(ssn) < 4){
            Toast.makeText(CaddieBankDetailsActivity.this,"Account Number must have 12 digits",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    private void getDetails() {

        b.name.setText(name);
        b.number.setText(number);
        b.routingNumber.setText(routing_number);
        b.ssn.setText(ssn);
        for (int i = 0; i < typeList.length; i++){
            if (typeList[i].equals(type)){
                b.spinnerType.setSelection(i);
            }
        }

//        String last4 = number.substring(number.length()-4);
  //      Toast.makeText(CaddieBankDetailsActivity.this, last4, Toast.LENGTH_SHORT).show();
    }

    private void createConnectedAccount(String fname, String stype, String accountNo, String ssnNo) {

        Stripe.apiKey = apiKey;

        db1.child(user.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Model_Caddie model = snapshot.getValue(Model_Caddie.class);

                            Map<String, Object> cardPayments =
                                    new HashMap<>();
                            cardPayments.put("requested", true);
                            Map<String, Object> transfers = new HashMap<>();
                            transfers.put("requested", true);
                            Map<String, Object> capabilities =
                                    new HashMap<>();
                            capabilities.put("card_payments", cardPayments);
                            capabilities.put("transfers", transfers);
                            Map<String, Object> support_address = new HashMap<>();
                            support_address.put("city",model.getState());
                            support_address.put("country","US");
                            support_address.put("line1","300 North Akard Street");
                            support_address.put("postal_code","75201");
                            support_address.put("state","TX");
                            Map<String, Object> params = new HashMap<>();
                            params.put("type", "custom");
                            params.put("country", "US");
                            params.put("email", model.getEmail());
                            params.put("business_type", "individual");
                            Map<String, Object> business_profile = new HashMap<>();
                            business_profile.put("mcc","5999");
                            business_profile.put("product_description","glass art. Cigar cases.\\nCustomers will pay when they purchase");
                            business_profile.put("support_address",support_address);
                            business_profile.put("support_phone",model.getPhone());
                            business_profile.put("url","https://www.instagram.com/lux_sesh_supply/");

                            String last4 = accountNo.substring(accountNo.length()-4);

                            Map<String, Object> company = new HashMap<>();
                            company.put("address",support_address);
                            company.put("owners_provided",false);
                            company.put("phone",model.getPhone());

                            Map<String, Object> bankAccount = new HashMap<>();
                            bankAccount.put("object", "bank_account");
                            bankAccount.put("country", "US");
                            bankAccount.put("currency", "usd");
                            bankAccount.put(
                                    "account_holder_name",
                                    fname
                            );
                            bankAccount.put(
                                    "account_holder_type",
                                    stype
                            );
                            bankAccount.put("routing_number", routing_number);
                            bankAccount.put("account_number", accountNo);
                            bankAccount.put("last4", last4);

                            db1.child(user.getUid()).child("dob").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()){
                                        String day = snapshot.child("day").getValue().toString();
                                        String month = snapshot.child("month").getValue().toString();
                                        String year = snapshot.child("year").getValue().toString();

                                        Map<String, Object> individual = new HashMap<>();
                                        individual.put("address",support_address);
                                        Map<String, Object> dob = new HashMap<>();
                                        dob.put("day",day);
                                        dob.put("month",month);
                                        dob.put("year",year);
                                        individual.put("dob",dob);
                                        individual.put("email",model.getEmail());
                                        individual.put("first_name",model.getName());
                                        individual.put("last_name","(Caddie)");
                                        individual.put("phone",model.getPhone());
                                        individual.put("ssn_last_4", ssnNo);

                                        Map<String, Object> tos = new HashMap<>();
                                        Long date = System.currentTimeMillis();
                                        tos.put("date",1668986011);
                                        tos.put("ip","108.196.233.55");

                                        params.put("capabilities", capabilities);
                                        params.put("business_profile", business_profile);
                                        params.put("company", company);
                                        params.put("external_account",bankAccount);
                                        params.put("individual",individual);
                                        params.put("tos_acceptance",tos);
                                        try {
                                            Account account = Account.create(params);
                                            HashMap<String,Object> hashMap = new HashMap<>();
                                            hashMap.put("accountId",account.getId());
                                            db.child(user.getUid()).updateChildren(hashMap);
                                            Toast.makeText(CaddieBankDetailsActivity.this,"Account Created Successfully!",Toast.LENGTH_SHORT).show();
                                            savePreferenceData(fname,stype,accountNo,ssnNo);
                                            finish();
                                        } catch (StripeException e) {
                                            e.printStackTrace();
                                            Toast.makeText(CaddieBankDetailsActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void savePreferenceData(String fname, String stype, String accountNo, String ssnNo) {
        manager.storeString("name",fname);
        manager.storeString("type",stype);
        manager.storeString("routing",routing_number);
        manager.storeString("number",accountNo);
        manager.storeString("ssn",ssnNo);
        b.infoLayout.setVisibility(View.GONE);
        b.detailsLayout.setVisibility(View.VISIBLE);
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