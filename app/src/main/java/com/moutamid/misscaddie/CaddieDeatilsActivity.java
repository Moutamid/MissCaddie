package com.moutamid.misscaddie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.moutamid.misscaddie.adapters.AddServiceAdapter;
import com.moutamid.misscaddie.models.ServiceListModel;

import java.util.ArrayList;

public class CaddieDeatilsActivity extends AppCompatActivity {

    TextView almostFinished;
    RecyclerView addRecyclerRC;
    TextView addService;
    AddServiceAdapter adapter;
    ArrayList<ServiceListModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caddie_deatils);

        almostFinished = findViewById(R.id.almostFinished);
        addRecyclerRC = findViewById(R.id.addServiceRV);
        addService = findViewById(R.id.addNewService);

        list = new ArrayList<>();

        addRecyclerRC.setLayoutManager(new LinearLayoutManager(this));
        addRecyclerRC.setHasFixedSize(false);


        almostFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CaddieDeatilsActivity.this , CaddieAvailabiltyActivity.class);
                startActivity(intent);
                Animatoo.animateZoom(CaddieDeatilsActivity.this);
            }
        });

        addService.setOnClickListener(v -> {
            ServiceListModel model = new ServiceListModel("", "");
            list.add(model);
            adapter = new AddServiceAdapter(CaddieDeatilsActivity.this, list);
            adapter.notifyItemInserted(list.size() - 1);
            addRecyclerRC.setAdapter(adapter);
        });
    }
}