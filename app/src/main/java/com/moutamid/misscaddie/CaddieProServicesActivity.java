package com.moutamid.misscaddie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.ArrayList;

public class CaddieProServicesActivity extends AppCompatActivity {
    ImageView backBtn;
    RecyclerView addRecyclerRC;
    TextView addService;
    AddServiceAdapter adapter;
    ArrayList<ServiceListModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caddie_pro_services);

        backBtn = findViewById(R.id.back_btn);
        addRecyclerRC = findViewById(R.id.addServiceRV);
        addService = findViewById(R.id.addNewService);

        list = new ArrayList<>();

        addRecyclerRC.setLayoutManager(new LinearLayoutManager(this));
        addRecyclerRC.setHasFixedSize(false);

        backBtn.setOnClickListener(v -> {
            onBackPressed();
            Animatoo.animateSwipeLeft(CaddieProServicesActivity.this);
        });

        addService.setOnClickListener(v -> {
            ServiceListModel model = new ServiceListModel("", "");
            list.add(model);
            adapter = new AddServiceAdapter(CaddieProServicesActivity.this, list);
            addRecyclerRC.setAdapter(adapter);
        });

    }
}