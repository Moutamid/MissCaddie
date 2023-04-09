package com.moutamid.misscaddie.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.misscaddie.PayActivity;
import com.moutamid.misscaddie.R;
import com.moutamid.misscaddie.SharedPreferencesManager;
import com.moutamid.misscaddie.adapters.PaymentRequestesAdapter;
import com.moutamid.misscaddie.listners.ItemClickListener;
import com.moutamid.misscaddie.models.Model_Caddie;
import com.moutamid.misscaddie.models.PayPalSeller;
import com.moutamid.misscaddie.models.RequestsModel;
import com.moutamid.misscaddie.models.ServiceListModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentRequestFragment extends Fragment {

    RecyclerView requestRC;
    private DatabaseReference db,db1;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ArrayList<RequestsModel> itemList;
    private double amount = 0.0;
    private String group;
    private String key = "";
    private ProgressDialog progressDialog;
    private static final int RC_READ_HONE_STATE = 1;
    private static final int RC_PAYPAL_ADAPTIVE = 101;
    private static final String TAG = "paymentExample";

    @SuppressLint("MissingInflatedId")
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
        progressDialog = new ProgressDialog(requireContext());
        //apiKey = manager.retrieveString("apiKey","");
      //  pubKey = manager.retrieveString("publisherKey","");
        itemList = new ArrayList<>();
        if (isAdded()) {
            getRequests();
            requestRC.setLayoutManager(new LinearLayoutManager(getActivity()));
            requestRC.setHasFixedSize(false);
        }
        return view;
    }


    private void getRequests() {
        //Query query = db.orderByChild("payment").equalTo(false);
        Query query = db.orderByChild("status_title").equalTo("payment_request");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    itemList.clear();
                    for (DataSnapshot ds : snapshot.getChildren()){
                        RequestsModel model = ds.getValue(RequestsModel.class);
                        if(model.getUserId().equals(user.getUid())) {
                            itemList.add(model);
                        }
                    }

                    PaymentRequestesAdapter adapter = new PaymentRequestesAdapter(itemList, getActivity());
                    requestRC.setAdapter(adapter);
                    adapter.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onItemClick(int position, View view) {
                            RequestsModel requestsModel = itemList.get(position);
                           // progressDialog.setMessage("Please Wait.....");
                            //progressDialog.show();
                            group = requestsModel.getCaddieId();
                            key = requestsModel.getId();
                            for (ServiceListModel services : requestsModel.getTableRows()){
                                amount += Double.parseDouble(services.getPrice());
                            }

                            Intent intent = new Intent(getActivity(), PayActivity.class);
                            intent.putExtra("price",amount);
                            intent.putExtra("user_id",group);
                            intent.putExtra("id",key);
                            startActivity(intent);
//                            initLibrary();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
