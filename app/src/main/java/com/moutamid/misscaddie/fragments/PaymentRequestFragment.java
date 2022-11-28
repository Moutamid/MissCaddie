package com.moutamid.misscaddie.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.misscaddie.CaddieBankDetailsActivity;
import com.moutamid.misscaddie.CaddieBookingDetailsActivity;
import com.moutamid.misscaddie.CardPaymentActivity;
import com.moutamid.misscaddie.Dashboard_Golfer;
import com.moutamid.misscaddie.R;
import com.moutamid.misscaddie.SharedPreferencesManager;
import com.moutamid.misscaddie.adapters.PaymentRequestesAdapter;
import com.moutamid.misscaddie.adapters.RequestesAdapter;
import com.moutamid.misscaddie.listners.ItemClickListener;
import com.moutamid.misscaddie.models.RequestsModel;
import com.moutamid.misscaddie.models.ServiceListModel;
import com.stripe.Stripe;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;
import com.stripe.android.paymentsheet.PaymentSheetResultCallback;
import com.stripe.exception.StripeException;
import com.stripe.model.Transfer;
import com.stripe.param.TransferCreateParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PaymentRequestFragment extends Fragment {

    RecyclerView requestRC;
    private DatabaseReference db,db1;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ArrayList<RequestsModel> itemList;
    private SharedPreferencesManager manager;
    private String apiKey = "";
    private String pubKey = "";
    private String customerID,emphericalKey,clientSecret;
    private int amount = 0;
    private PaymentSheet paymentSheet;
    private String group;
    private String caddieKey = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        View view = inflater.inflate(R.layout.fragment_payment_request, container, false);
        requestRC = view.findViewById(R.id.recyclerView_golfer);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference().child("Requests");
        db1 = FirebaseDatabase.getInstance().getReference().child("BankAccounts");
        manager = new SharedPreferencesManager(getActivity());
        apiKey = manager.retrieveString("apiKey","");
        pubKey = manager.retrieveString("publisherKey","");
        itemList = new ArrayList<>();
        if (isAdded()) {
            getRequests();
            requestRC.setLayoutManager(new LinearLayoutManager(getActivity()));
            requestRC.setHasFixedSize(false);

            PaymentConfiguration.init(requireActivity(), pubKey);
            paymentSheet = new PaymentSheet(this, new PaymentSheetResultCallback() {
            @Override
            public void onPaymentSheetResult(@NonNull PaymentSheetResult paymentSheetResult) {
                onPaymentResult(paymentSheetResult);
            }
            });
         /*   ConfirmPaymentIntentParams confirmPaymentIntentParams = ConfirmPaymentIntentParams
                    .create(clientSecret);
            stripe.confirmPayment(this, confirmPaymentIntentParams);*/
        }
        return view;
    }

    private void onPaymentResult(PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Completed){
            Toast.makeText(getActivity(), "Payment Successful", Toast.LENGTH_SHORT).show();
            transfers();
        }
    }

    private void transfers() {
        Stripe.apiKey = apiKey;

        db1.child(group)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String accountId = snapshot.child("accountId").getValue().toString();
                            long half = (long) (amount*0.9);
                            TransferCreateParams secondTransferParams =
                                    TransferCreateParams.builder()
                                            .setAmount(half)
                                            .setCurrency("usd")
                                            .setDestination(accountId)
                                            .setTransferGroup(group)
                                            .build();


                            try {
                                Transfer secondTransfer = Transfer.create(secondTransferParams);
                                Toast.makeText(getActivity(), secondTransfer.getId(), Toast.LENGTH_SHORT).show();
                            } catch (StripeException e) {
                                e.printStackTrace();
                                Log.d("transfer",e.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        TransferCreateParams secondTransferParams =
                TransferCreateParams.builder()
                        .setAmount((long) (amount*0.1))
                        .setCurrency("usd")
                        .setDestination("acct_1M6M97BLuTeRO57f")
                        .setTransferGroup(group)
                        .build();


        try {
            Transfer secondTransfer = Transfer.create(secondTransferParams);
            Toast.makeText(getActivity(), secondTransfer.getId(), Toast.LENGTH_SHORT).show();
        } catch (StripeException e) {
            e.printStackTrace();
            Log.d("transfer",e.getMessage());
        }
    }

    private void getRequests() {
        Query query = db.orderByChild("payment").equalTo(false);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    itemList.clear();
                    for (DataSnapshot ds : snapshot.getChildren()){
                        RequestsModel model = ds.getValue(RequestsModel.class);
                        itemList.add(model);
                    }

                    PaymentRequestesAdapter adapter = new PaymentRequestesAdapter(itemList, getActivity());
                    requestRC.setAdapter(adapter);
                    adapter.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onItemClick(int position, View view) {
                            RequestsModel requestsModel = itemList.get(position);
                            group = requestsModel.getCaddieId();
                            for (ServiceListModel services : requestsModel.getTableRows()){
                                amount += Integer.parseInt(services.getPrice());
                            }
                            checkout();
                        }
                    });
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void checkout(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/customers",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            customerID = jsonObject.getString("id");
                            //Toast.makeText(getActivity(), customerID, Toast.LENGTH_SHORT).show();

                            getEmphericalKey(customerID);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Authorization","Bearer " + apiKey);

                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void getEmphericalKey(String customerID) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/ephemeral_keys",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            emphericalKey = jsonObject.getString("id");
                          //  Toast.makeText(getActivity(), emphericalKey, Toast.LENGTH_SHORT).show();

                            getClientSecret(customerID,emphericalKey);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Authorization","Bearer " + apiKey);
                headers.put("Stripe-Version","2022-11-15");
                return headers;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("customer",customerID);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void getClientSecret(String customerID, String emphericalKey) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/payment_intents",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            clientSecret = jsonObject.getString("client_secret");
//                            Toast.makeText(getActivity(), clientSecret, Toast.LENGTH_SHORT).show();

                            paymentFlow();

                        } catch (JSONException e) {
                            e.printStackTrace();
                         //   Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Authorization","Bearer " + apiKey);
                return headers;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("customer",customerID);
                params.put("amount",amount + "00");
                params.put("currency","usd");
                params.put("automatic_payment_methods[enabled]","true");
                params.put("transfer_group",group);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void paymentFlow() {

        paymentSheet.presentWithPaymentIntent(clientSecret,new PaymentSheet.Configuration("MisCaddie",
                new PaymentSheet.CustomerConfiguration(
                        customerID,emphericalKey
                )));
    }


    private void transferToCaddie(){

    /*    db1.child(group)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String accountId = snapshot.child("accountId").getValue().toString();
                            double half = amount*0.9;
                            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                    "https://api.stripe.com/v1/transfers",
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                String transfer = jsonObject.getString("id");
                                                Toast.makeText(getActivity(), transfer, Toast.LENGTH_SHORT).show();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }){

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String,String> headers = new HashMap<>();
                                    headers.put("Authorization","Bearer " + apiKey);
                                    return headers;
                                }

                                @Nullable
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<>();
                                    params.put("amount",String.valueOf(half));
                                    params.put("currency","usd");
                                    params.put("destination",accountId);
                                    params.put("transfer_group",group);
                                    return params;
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                            requestQueue.add(stringRequest);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*/
    }

    private void clientTransfer(){
        double half = amount*0.1;
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/transfers",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String transfer = jsonObject.getString("id");
                            Toast.makeText(getActivity(), transfer, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Authorization","Bearer " + apiKey);
                return headers;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("amount",String.valueOf(half));
                params.put("currency","usd");
                params.put("destination","acct_1M8TcwPYTcLBOhet");
                params.put("transfer_group",group);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

}
