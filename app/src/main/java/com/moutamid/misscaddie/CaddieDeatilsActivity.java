package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.moutamid.misscaddie.databinding.ActivityCaddieDeatilsBinding;
import com.moutamid.misscaddie.models.Bonus;
import com.moutamid.misscaddie.models.ServiceListModel;

import java.util.ArrayList;
import java.util.HashMap;

public class CaddieDeatilsActivity extends AppCompatActivity {

    TextView almostFinished;
    RecyclerView addRecyclerRC;
    TextView addService;
    AddServiceAdapter adapter;
    ArrayList<ServiceListModel> list;
    ArrayList<Bonus> bonusArrayList = new ArrayList<>();
    private ActivityCaddieDeatilsBinding b;
    FirebaseAuth mAuth;
    FirebaseUser currrentUser;
    ProgressDialog dialog;
    private String state ="";
    private String status = "willing";
    private String category = "";
    private DatabaseReference db;
    boolean willingState = true, notWillingState = false;
    LinearLayout notWillingLayout, WillingLayout;
    TextView willingTv, notWillingTv;
    ImageView iconsNot, iconWill;
    private boolean range = false;
    private boolean video = false;
    private boolean travel = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityCaddieDeatilsBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        notWillingLayout = findViewById(R.id.notWilling);
        WillingLayout = findViewById(R.id.willing);
        iconsNot = findViewById(R.id.notWilling_icon);
        iconWill = findViewById(R.id.willing_icon);
        almostFinished = findViewById(R.id.almostFinished);
        addRecyclerRC = findViewById(R.id.addServiceRV);
        addService = findViewById(R.id.addNewService);
        willingTv = findViewById(R.id.willing_tv);
        notWillingTv = findViewById(R.id.notwilling_tv);

        list = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        currrentUser = mAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference().child("Caddie");
        //serviceDb = FirebaseDatabase.getInstance().getReference().child("Services");
        addRecyclerRC.setLayoutManager(new LinearLayoutManager(this));
        addRecyclerRC.setHasFixedSize(false);

        b.spinnerStates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                state = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        b.spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        b.willingLayoutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (willingState){
                    WillingLayout.setBackground(getDrawable(R.drawable.unselected_box));
                    //iconWill.setBackground(getDrawable(R.drawable.ic_charm_tick));
                    willingState = false;
                    notWillingLayout.setBackground(getDrawable(R.drawable.selected_box));
                    //iconsNot.setBackground(getDrawable(R.drawable.ic_charm_cross));
                    notWillingState = true;
                    willingTv.setTextColor(getResources().getColor(R.color.black_light));
                    notWillingTv.setTextColor(getResources().getColor(R.color.black));
                } else {
                    WillingLayout.setBackground(getDrawable(R.drawable.selected_box));
                    //iconWill.setBackground(getDrawable(R.drawable.ic_charm_tick1));
                    notWillingLayout.setBackground(getDrawable(R.drawable.unselected_box));
                    //iconsNot.setBackground(getDrawable(R.drawable.ic_charm_cross2));
                    willingState = true;
                    willingTv.setTextColor(getResources().getColor(R.color.black));
                    notWillingTv.setTextColor(getResources().getColor(R.color.black_light));
                    notWillingState = false;
                }
                status = "willing";
            }
        });
        b.notWillingLayoutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notWillingState) {
                    WillingLayout.setBackground(getDrawable(R.drawable.selected_box));
                    //iconWill.setBackground(getDrawable(R.drawable.ic_charm_tick1));
                    notWillingLayout.setBackground(getDrawable(R.drawable.unselected_box));
                    //iconsNot.setBackground(getDrawable(R.drawable.ic_charm_cross2));
                    willingState = true;
                    willingTv.setTextColor(getResources().getColor(R.color.black));
                    notWillingTv.setTextColor(getResources().getColor(R.color.black_light));
                    notWillingState = false;
                } else {
                    notWillingLayout.setBackground(getDrawable(R.drawable.selected_box));
                    //iconsNot.setBackground(getDrawable(R.drawable.ic_charm_cross));
                    notWillingState = true;
                    WillingLayout.setBackground(getDrawable(R.drawable.unselected_box));
                    //iconWill.setBackground(getDrawable(R.drawable.ic_charm_tick));
                    willingTv.setTextColor(getResources().getColor(R.color.black_light));
                    notWillingTv.setTextColor(getResources().getColor(R.color.black));
                    willingState = false;
                }
                status = "not willing";
            }
        });
        b.addOnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bonus = b.addOnsTxt.getText().toString();
                if(!TextUtils.isEmpty(bonus)){
                    String key = db.child(currrentUser.getUid()).child("bonus").push().getKey();
                    HashMap<String,Object> hashMap1 = new HashMap<>();
                    hashMap1.put("id",key);
                    hashMap1.put("bonus",bonus);
                    db.child(currrentUser.getUid()).child("bonus").child(key).updateChildren(hashMap1);
                    getAddOns();
                }
                b.addOnsTxt.setText("");
            }
        });


        almostFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location = b.etLocation.getText().toString();
                String service = b.etService.getText().toString();
                String price = b.etPrice.getText().toString();
                String feet = b.feet.getText().toString();
                String inches = b.inches.getText().toString();
                if (!TextUtils.isEmpty(location) && !TextUtils.isEmpty(price)
                        && !TextUtils.isEmpty(feet) && !TextUtils.isEmpty(inches)) {
                    saveData(location, service, price,feet,inches);

                    for (int i = 0; i < list.size(); i++){
                        ServiceListModel model = list.get(i);
                        db.child(currrentUser.getUid()).child("services").child(String.valueOf(i)).setValue(model);
                    }
                }
            }
        });

        addService.setOnClickListener(v -> {
          /*  String serviceName = b.etService.getText().toString();
            String servicePrice = b.etPrice.getText().toString();
            if (!TextUtils.isEmpty(serviceName) && !TextUtils.isEmpty(servicePrice)) {
                String key = db.push().getKey();
                ServiceListModel model = new ServiceListModel(key,serviceName, servicePrice);
                db.child(currrentUser.getUid()).child("services").child(key).setValue(model);
            }
            b.etService.setText("");
            b.etPrice.setText("");*/
            String title = b.etService.getText().toString();
            String price = b.etPrice.getText().toString();
            if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(price)) {
                //String key = db.push().getKey();
                ServiceListModel model = new ServiceListModel(title, price);
                list.add(model);

                adapter = new AddServiceAdapter(CaddieDeatilsActivity.this, list);
                adapter.notifyItemInserted(list.size() - 1);
                addRecyclerRC.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                b.etService.setText("");
                b.etPrice.setText("");
            }
        });
        getServices();
        getAddOns();
        changeStatusBarColor(this,R.color.yellow);
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
        db.child(currrentUser.getUid()).child("services")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            list.clear();
                            for (DataSnapshot ds : snapshot.getChildren()){
                                ServiceListModel model= ds.getValue(ServiceListModel.class);
                                list.add(model);
                            }
                            adapter = new AddServiceAdapter(CaddieDeatilsActivity.this, list);
                            adapter.notifyItemInserted(list.size() - 1);
                            addRecyclerRC.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void getAddOns() {
        db.child(currrentUser.getUid()).child("bonus")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            bonusArrayList.clear();
                            for (DataSnapshot ds : snapshot.getChildren()){
                                Bonus model= ds.getValue(Bonus.class);
                                bonusArrayList.add(model);
                            }
                            AddOnsListAdapter addOnsListAdapter = new AddOnsListAdapter(
                                    CaddieDeatilsActivity.this, bonusArrayList);
                           // addOnsListAdapter.notifyItemInserted(bonusArrayList.size() - 1);
                            b.addonsRV.setAdapter(addOnsListAdapter);
                            addOnsListAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void saveData(String location, String service, String price,String feet,String inches) {
        String height = feet +"'" + inches+"''";
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("state",state);
        hashMap.put("place",location);
        hashMap.put("length",height);
        hashMap.put("catagory",category);
        hashMap.put("status",status);
        db.child(currrentUser.getUid()).updateChildren(hashMap);
       /* if (range){
            String key = db.child(currrentUser.getUid()).child("bonus").push().getKey();
            HashMap<String,Object> hashMap1 = new HashMap<>();
            hashMap1.put("name",b.range.getText().toString());
            db.child(currrentUser.getUid()).child("bonus").child(key).updateChildren(hashMap1);
        }
        if (video){
            String key = db.child(currrentUser.getUid()).child("bonus").push().getKey();
            HashMap<String,Object> hashMap1 = new HashMap<>();
            hashMap1.put("name",b.video.getText().toString());
            db.child(currrentUser.getUid()).child("bonus").child(key).updateChildren(hashMap1);
        }
        if (travel){
            String key = db.child(currrentUser.getUid()).child("bonus").push().getKey();
            HashMap<String,Object> hashMap1 = new HashMap<>();
            hashMap1.put("name",b.travel.getText().toString());
            db.child(currrentUser.getUid()).child("bonus").child(key).updateChildren(hashMap1);
        }*/
     /*   String key = db.push().getKey();
        ServiceListModel model = new ServiceListModel(service, price);
        db.child(currrentUser.getUid()).child("services").child(key).setValue(model);*/
        Intent intent = new Intent(CaddieDeatilsActivity.this , CaddieAvailabiltyActivity.class);
        startActivity(intent);
        finish();
        Animatoo.animateZoom(CaddieDeatilsActivity.this);
    }
}