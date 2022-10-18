package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.misscaddie.adapters.Adapter_Golfer;
import com.moutamid.misscaddie.models.Model_Golfer;

import java.util.ArrayList;

public class Dashboard_Golfer extends AppCompatActivity {

    private String[] golfer_name = {"Andrea Carl", "Moutamid", "Osama",};
    private String[] golfer_price = {"US$65", "US$150", "US$200",};
    private String[] golfer_length = {"15 cm", "7+ cm", "39 cm",};
    private String[] golfer_catagory = {"Medium", "Low", "High",};
    private String[] golfer_place = {"Dubai", "Dubai", "UAE",};
    private String[] golfer_reviews = {"10 reviews", "167 reviews", "700 reviews",};
    private String[] gokfer_status = {"Willing to travel", "Willing to travel", "Willing to travel",};
    private int[] images1_golfer = {R.drawable.img1, R.drawable.img2, R.drawable.img3,};
    ImageView filters_btn, mssage_btn;

    private RecyclerView golfer_recycler;
    private ArrayList<Model_Golfer> modelGolferArrayList;
    private Adapter_Golfer adapterGolfer;
    private DatabaseReference db;
    FirebaseAuth mAuth;
    FirebaseUser currrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_golfer);

        filters_btn = findViewById(R.id.filters);
        mssage_btn = findViewById(R.id.mssage_btn);

        mAuth = FirebaseAuth.getInstance();
        currrentUser = mAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference().child("Caddie");
        filters_btn.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard_Golfer.this , GolferFilterActivity.class);
            startActivity(intent);
            Animatoo.animateZoom(Dashboard_Golfer.this);
        });

        mssage_btn.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard_Golfer.this , MessagesActivity.class);
            startActivity(intent);
            Animatoo.animateZoom(Dashboard_Golfer.this);
        });
        modelGolferArrayList = new ArrayList<>();
        golfer_recycler = findViewById(R.id.recyclerView_golfer);
        load_detail();
    }

    private void load_detail() {

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    modelGolferArrayList.clear();
                    for (DataSnapshot ds : snapshot.getChildren()){
                        Model_Golfer model_golfer = ds.getValue(Model_Golfer.class);
                        modelGolferArrayList.add(model_golfer);

                    }

                    adapterGolfer = new Adapter_Golfer(Dashboard_Golfer.this, modelGolferArrayList);
                    golfer_recycler.setAdapter(adapterGolfer);
                    adapterGolfer.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}