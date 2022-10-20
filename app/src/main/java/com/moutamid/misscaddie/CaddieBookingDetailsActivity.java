package com.moutamid.misscaddie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moutamid.misscaddie.databinding.ActivityCaddieBookingDetailsBinding;
import com.moutamid.misscaddie.models.RequestsModel;

import java.util.HashMap;

public class CaddieBookingDetailsActivity extends AppCompatActivity {

    private ActivityCaddieBookingDetailsBinding b;
    private RequestsModel model;
    private String name,image;
    private DatabaseReference requestsDb;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         b = ActivityCaddieBookingDetailsBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        model = getIntent().getParcelableExtra("requestModel");
        name = getIntent().getStringExtra("personName");
        image = getIntent().getStringExtra("personImage");
        b.backBtn.setOnClickListener(v -> {
            onBackPressed();
            Animatoo.animateSwipeLeft(CaddieBookingDetailsActivity.this);
        });
        requestsDb = FirebaseDatabase.getInstance().getReference().child("Requests");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        b.name.setText(name);
        Glide.with(CaddieBookingDetailsActivity.this)
                .load(image)
                .placeholder(R.drawable.img3)
                .into(b.profileImg);
        
        b.date.setText(model.getDate());
        b.serviceList.setText(model.getService());
        b.place.setText(model.getAddress());
        b.message.setText(model.getMessage());
        b.totalPrice.setText("Total : US$ " + model.getPrice());
        b.acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptBooking();
            }
        });
        b.declineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                declineBooking();
            }
        });
    }

    private void declineBooking() {
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("status_title","Decline");
        requestsDb.child(model.getId()).updateChildren(hashMap);

        startActivity(new Intent(CaddieBookingDetailsActivity.this,CaddieDashboardActivity.class));
        finish();
        Animatoo.animateZoom(CaddieBookingDetailsActivity.this);
    }

    private void acceptBooking() {
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("status_title","Accepted");
      //  hashMap.put("caddieId",user.getUid());
        requestsDb.child(model.getId()).updateChildren(hashMap);
        startActivity(new Intent(CaddieBookingDetailsActivity.this,CaddieDashboardActivity.class));
        finish();
        Animatoo.animateZoom(CaddieBookingDetailsActivity.this);
    }
}