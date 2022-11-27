package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
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
import com.moutamid.misscaddie.models.Model_Caddie;
import com.moutamid.misscaddie.models.ServiceListModel;

import java.util.ArrayList;
import java.util.Calendar;
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
    private String state ="";
    private String dob;
    private String day,month,year = "";
    private String status = "willing";
    private String category = "";
    private DatabaseReference db;
    boolean willingState = true, notWillingState = false;
    LinearLayout notWillingLayout, WillingLayout;
    TextView willingTv, notWillingTv;
    ImageView iconsNot, iconWill;
    private String[] states = {"Select State (New York)","Alabama","Alaska","Arizona","Arkansas",
            "California","Colorado", "Connecticut", "Delaware",
            "Florida",
            "Georgia",
            "Hawaii",
            "Idaho",
            "Illinois",
            "Indiana",
            "Iowa",
            "Kansas",
            "Kentucky",
            "Louisiana",
            "Maine",
            "Maryland",
            "Massachusetts",
            "Michigan",
            "Minnesota",
            "Mississippi",
            "Missouri",
            "Montana",
            "Nebraska",
            "Nevada",
            "New Hampshire",
            "New Jersey",
            "New Mexico",
            "New York",
            "North Carolina",
            "North Dakota",
            "Ohio",
            "Oklahoma",
            "Oregon",
            "Pennsylvania",
            "Rhode Island",
            "South Carolina",
            "South Dakota",
            "Tennessee",
            "Texas",
            "Utah",
            "Vermont",
            "Virginia",
            "Washington",
            "West Virginia",
            "Wisconsin",
            "Wyoming",
    };
    private String[] categoryString = {"Select Status (Beginner)","Beginner","Intermediate","Experienced"};


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

        b.dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(CaddieDeatilsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int thisyear, int monthOfYear, int dayOfMonth) {
                        dob = dayOfMonth + "/" + (monthOfYear+1) + "/" + thisyear;
                        day = String.valueOf(dayOfMonth);
                        month = String.valueOf(monthOfYear+1);
                        year = String.valueOf(thisyear);
                        b.dob.setText(dob);
                    }
                }, yy, mm, dd);
                datePicker.show();

            }
        });


        almostFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location = b.etLocation.getText().toString();
                String price = b.etPrice.getText().toString();
                String feet = b.feet.getText().toString();
                String inches = b.inches.getText().toString();
                String phone = b.phone.getText().toString();
                if (!TextUtils.isEmpty(location) && !TextUtils.isEmpty(phone)
                        && !TextUtils.isEmpty(feet) && !TextUtils.isEmpty(inches)) {
                    saveData(location,feet,inches,phone);

                    /*for (int i = 0; i < list.size(); i++){
                        ServiceListModel model = list.get(i);
                        db.child(currrentUser.getUid()).child("services").child(String.valueOf(i)).setValue(model);
                    }*/
                }
            }
        });

        addService.setOnClickListener(v -> {
            String serviceName = b.etService.getText().toString();
            String servicePrice = b.etPrice.getText().toString();
            if (!TextUtils.isEmpty(serviceName) && !TextUtils.isEmpty(servicePrice)) {
                String key = db.child(currrentUser.getUid()).child("services").push().getKey();
                ServiceListModel model = new ServiceListModel(key,serviceName, servicePrice);
                db.child(currrentUser.getUid()).child("services").child(key).setValue(model);
                getServices();
            }
            b.etService.setText("");
            b.etPrice.setText("");
         /*   String title = b.etService.getText().toString();
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
                b.etPrice.setText("");*/
        });
        getServices();
        getAddOns();
        getDob();
        checkIfExists();
        changeStatusBarColor(this,R.color.yellow);
    }

    private void checkIfExists() {
        db.child(currrentUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Model_Caddie caddie = snapshot.getValue(Model_Caddie.class);
                            b.feet.setText(String.valueOf(caddie.getFeet()));
                            b.inches.setText(String.valueOf(caddie.getInches()));
                            b.etLocation.setText(caddie.getPlace());
                            b.phone.setText(caddie.getPhone());

                            for (int i = 0; i < states.length; i++){
                                if(states[i].equals(caddie.getState())){
                                    b.spinnerStates.setSelection(i);
                                }
                            }

                            for (int i = 0; i < categoryString.length; i++){

                                if(categoryString[i].equals(caddie.getCatagory())){
                                    b.spinnerStatus.setSelection(i);
                                }
                            }

                            if (caddie.getStatus().equals("willing")) {
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

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void getDob() {
        db.child(currrentUser.getUid()).child("dob")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            day = snapshot.child("day").getValue().toString();
                            month = snapshot.child("month").getValue().toString();
                            year = snapshot.child("year").getValue().toString();
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

    private void saveData(String location, String feet, String inches, String phone) {
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("state",state);
        hashMap.put("place",location);
        hashMap.put("feet",Integer.parseInt(feet));
        hashMap.put("inches",Integer.parseInt(inches));
        hashMap.put("catagory",category);
        hashMap.put("phone",phone);
        hashMap.put("status",status);
        db.child(currrentUser.getUid()).updateChildren(hashMap);

        HashMap<String,Object> hashMap1 = new HashMap<>();

        hashMap1.put("day",day);
        hashMap1.put("month",month);
        hashMap1.put("year",year);
        db.child(currrentUser.getUid()).child("dob").updateChildren(hashMap1);

       Intent intent = new Intent(CaddieDeatilsActivity.this , CaddieAvailabiltyActivity.class);
        startActivity(intent);
        finish();
        Animatoo.animateZoom(CaddieDeatilsActivity.this);
    }
}