package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.misscaddie.adapters.AddOnsListAdapter;
import com.moutamid.misscaddie.adapters.AddServiceAdapter;
import com.moutamid.misscaddie.models.Bonus;
import com.moutamid.misscaddie.models.ServiceListModel;

import java.util.ArrayList;
import java.util.HashMap;

public class CaddieProServicesActivity extends AppCompatActivity {
    ImageView backBtn;
    RecyclerView addRecyclerRC,addOnRecyclerView;
    TextView addService,updateService,addOnBtn;
    AddServiceAdapter adapter;
    private EditText serviceTxt,priceTxt,bonusTxt;
    ArrayList<ServiceListModel> list;
    FirebaseAuth mAuth;
    private ArrayList<Bonus> bonusArrayList = new ArrayList<>();
    FirebaseUser currrentUser;
    private DatabaseReference db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caddie_pro_services);

        backBtn = findViewById(R.id.back_btn);
        addRecyclerRC = findViewById(R.id.addServiceRV);
        addService = findViewById(R.id.addNewService);
        updateService = findViewById(R.id.updateBtn);
        serviceTxt = findViewById(R.id.et_service);
        priceTxt = findViewById(R.id.et_price);
        addOnRecyclerView = findViewById(R.id.addonsRV);
        bonusTxt = findViewById(R.id.add_ons_txt);
        addOnBtn = findViewById(R.id.addOnBtn);

        mAuth = FirebaseAuth.getInstance();
        currrentUser = mAuth.getCurrentUser();
        list = new ArrayList<>();
        db = FirebaseDatabase.getInstance().getReference().child("Caddie");
        addRecyclerRC.setLayoutManager(new LinearLayoutManager(this));
        addRecyclerRC.setHasFixedSize(false);

        backBtn.setOnClickListener(v -> {
            onBackPressed();
            Animatoo.animateSwipeLeft(CaddieProServicesActivity.this);
        });

        addOnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bonus = bonusTxt.getText().toString();
                if(!TextUtils.isEmpty(bonus)){
                    String key = db.child(currrentUser.getUid()).child("bonus").push().getKey();
                    HashMap<String,Object> hashMap1 = new HashMap<>();
                    hashMap1.put("id",key);
                    hashMap1.put("bonus",bonus);
                    db.child(currrentUser.getUid()).child("bonus").child(key).updateChildren(hashMap1);
                    getAddOns();
                }
                bonusTxt.setText("");
            }
        });

        addService.setOnClickListener(v -> {
            String serviceName = serviceTxt.getText().toString();
            String servicePrice = priceTxt.getText().toString();
            if (!TextUtils.isEmpty(serviceName) && !TextUtils.isEmpty(servicePrice)) {
                String key = db.child(currrentUser.getUid()).child("services").push().getKey();
                ServiceListModel model = new ServiceListModel(key,serviceName, servicePrice);
                db.child(currrentUser.getUid()).child("services").child(key).setValue(model);
                getServices();
            }
            serviceTxt.setText("");
            priceTxt.setText("");
        });

        updateService.setOnClickListener(v -> {
            /*String title = serviceTxt.getText().toString();
            String price = priceTxt.getText().toString();
            if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(price)) {
                String key = db.push().getKey();
                ServiceListModel model = new ServiceListModel(key,title, price);
                db.child(key).setValue(model);
//                adapter.notifyDataSetChanged();
            }
            serviceTxt.setText("");
            priceTxt.setText("");*/
            for (int i = 0; i < list.size(); i++){
                ServiceListModel model = list.get(i);
                db.child(String.valueOf(i)).setValue(model);
            }
            serviceTxt.setText("");
            priceTxt.setText("");
        });

        getServices();
        getAddOns();
        changeStatusBarColor(this,R.color.yellow);
    }


    private void getAddOns() {
        db.child(currrentUser.getUid()).child("bonus")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            bonusArrayList.clear();
                            for (DataSnapshot ds : snapshot.getChildren()){
                                Bonus model= ds.getValue(Bonus.class);
                                bonusArrayList.add(model);
                            }
                            AddOnsListAdapter addOnsListAdapter = new AddOnsListAdapter(
                                    CaddieProServicesActivity.this, bonusArrayList);
                            addOnRecyclerView.setAdapter(addOnsListAdapter);
                            addOnsListAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    public void changeStatusBarColor(Activity activity, int id) {

        // Changing the color of status bar
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(activity.getResources().getColor(id));
        }

        // CHANGE STATUS BAR TO TRANSPARENT
        //window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    private void getServices() {
        db.child(currrentUser.getUid()).child("services").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            list.clear();
                            for (DataSnapshot ds : snapshot.getChildren()){
                                ServiceListModel model= ds.getValue(ServiceListModel.class);
                                list.add(model);
                            }
                            adapter = new AddServiceAdapter(CaddieProServicesActivity.this, list);
                            adapter.notifyItemInserted(list.size() - 1);
                            addRecyclerRC.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}