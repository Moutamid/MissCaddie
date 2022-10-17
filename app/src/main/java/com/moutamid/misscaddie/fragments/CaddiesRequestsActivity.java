package com.moutamid.misscaddie.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caddies_requests);

        back_btn = findViewById(R.id.back_btn);
        requestRC = findViewById(R.id.recyclerView_golfer);

        RequestesAdapter adapter = new RequestesAdapter(RequestItems(), this);

        requestRC.setAdapter(adapter);
        requestRC.setLayoutManager(new LinearLayoutManager(this));
        requestRC.setHasFixedSize(false);

        back_btn.setOnClickListener(v -> {
            Intent intent = new Intent(CaddiesRequestsActivity.this , Dashboard_Golfer.class);
            startActivity(intent);
            Animatoo.animateZoom(CaddiesRequestsActivity.this);
        });

    }


    private List<RequestsModel> RequestItems()
    {
        List<RequestsModel> itemList = new ArrayList<>();

        RequestsModel item = new RequestsModel(null, "Suleman Ijaz", "5", "Accepted", "5 Oct - 15 Nov", "Dean St, Brooklyn, NY, USA", tableMessage());
        itemList.add(item);

        RequestsModel item2 = new RequestsModel(null, "M. Moutamid", "65", "Declined", "15 Oct - 27 Nov", "Dean St, Brooklyn, NY, USA", tableMessage2() );
        itemList.add(item2);

        RequestsModel item3 = new RequestsModel(null, "Andrea Carl", "165", "Pending", "15 Oct - 27 Nov", "Dean St, Brooklyn, NY, USA", tableMessage() );
        itemList.add(item3);

        return itemList;
    }

    private List<ServiceListModel> tableMessage()
    {
        List<ServiceListModel> ChildItemList = new ArrayList<>();

        ChildItemList.add(new ServiceListModel("Ride Along", "80"));
        ChildItemList.add(new ServiceListModel("Caddie Party", "30"));
        ChildItemList.add(new ServiceListModel("Service 3", "450"));
        ChildItemList.add(new ServiceListModel("Service 5", "10"));

        return ChildItemList;
    }

    private List<ServiceListModel> tableMessage2()
    {
        List<ServiceListModel> ChildItemList = new ArrayList<>();

        ChildItemList.add(new ServiceListModel("Ride Along", "80"));
        ChildItemList.add(new ServiceListModel("Caddie Party", "30"));

        return ChildItemList;
    }
}