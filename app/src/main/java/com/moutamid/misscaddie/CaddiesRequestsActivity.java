package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.misscaddie.Dashboard_Golfer;
import com.moutamid.misscaddie.R;
import com.moutamid.misscaddie.models.RequestsModel;
import com.moutamid.misscaddie.models.ServiceListModel;
import com.moutamid.misscaddie.adapters.RequestesAdapter;

import java.util.ArrayList;
import java.util.List;

public class CaddiesRequestsActivity extends AppCompatActivity {
    ImageView back_btn;
    RecyclerView requestRC;
    private DatabaseReference db;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ArrayList<RequestsModel> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caddies_requests);

        back_btn = findViewById(R.id.back_btn);
        requestRC = findViewById(R.id.recyclerView_golfer);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference().child("Requests");
        itemList = new ArrayList<>();
        getRequests();
        requestRC.setLayoutManager(new LinearLayoutManager(this));
        requestRC.setHasFixedSize(false);
        changeStatusBarColor(this,R.color.yellow);
        back_btn.setOnClickListener(v -> {
            Intent intent = new Intent(CaddiesRequestsActivity.this , Dashboard_Golfer.class);
            startActivity(intent);
            Animatoo.animateZoom(CaddiesRequestsActivity.this);
        });

    }

    private void getRequests() {
        Query query = db.orderByChild("userId").equalTo(user.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    itemList.clear();
                    for (DataSnapshot ds : snapshot.getChildren()){
                        RequestsModel model = ds.getValue(RequestsModel.class);
                        itemList.add(model);
                    }

                    RequestesAdapter adapter = new RequestesAdapter(itemList, CaddiesRequestsActivity.this);
                    requestRC.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
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

}