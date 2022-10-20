package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.moutamid.misscaddie.adapters.AddServiceAdapter;
import com.moutamid.misscaddie.models.ServiceListModel;

import java.util.ArrayList;

public class CaddieProServicesActivity extends AppCompatActivity {
    ImageView backBtn;
    RecyclerView addRecyclerRC;
    TextView addService,updateService;
    AddServiceAdapter adapter;
    private EditText serviceTxt,priceTxt;
    ArrayList<ServiceListModel> list;
    FirebaseAuth mAuth;
    FirebaseUser currrentUser;
    private DatabaseReference db;

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

        mAuth = FirebaseAuth.getInstance();
        currrentUser = mAuth.getCurrentUser();
        list = new ArrayList<>();
        db = FirebaseDatabase.getInstance().getReference().child("Caddie")
                .child(currrentUser.getUid()).child("services");
        addRecyclerRC.setLayoutManager(new LinearLayoutManager(this));
        addRecyclerRC.setHasFixedSize(false);

        backBtn.setOnClickListener(v -> {
            onBackPressed();
            Animatoo.animateSwipeLeft(CaddieProServicesActivity.this);
        });

        addService.setOnClickListener(v -> {
            String title = serviceTxt.getText().toString();
            String price = priceTxt.getText().toString();
            if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(price)) {
                ServiceListModel model = new ServiceListModel(title, price);
                String key = db.push().getKey();
                db.child(key).setValue(model);
                adapter.notifyDataSetChanged();
            }
        });

        updateService.setOnClickListener(v -> {
            String title = serviceTxt.getText().toString();
            String price = priceTxt.getText().toString();

        });

        getServices();
    }

    private void getServices() {
        db.addValueEventListener(new ValueEventListener() {
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