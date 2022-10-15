package com.moutamid.misscaddie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.ArrayList;
import java.util.List;

public class CaddiesRequestsActivity extends AppCompatActivity {
    ImageView back_btn, profile_btn;
    RecyclerView requestRC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caddies_requests);

        back_btn = findViewById(R.id.back_btn);
        profile_btn = findViewById(R.id.profiles);
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

        profile_btn.setOnClickListener(v -> {
            Intent intent = new Intent(CaddiesRequestsActivity.this , GolferProfileEditActivity.class);
            startActivity(intent);
            Animatoo.animateSlideLeft(CaddiesRequestsActivity.this);
        });
    }


    private List<RequestsModel> RequestItems()
    {
        List<RequestsModel> itemList = new ArrayList<>();

        RequestsModel item = new RequestsModel(null, "Suleman Ijaz", "US$45", "Accepted", "5 Oct - 15 Nov", "Dean St, Brooklyn, NY, USA", tableMessage());
        itemList.add(item);

        RequestsModel item2 = new RequestsModel(null, "M. Moutamid", "US$65", "Declined", "15 Oct - 27 Nov", "Dean St, Brooklyn, NY, USA", tableMessage2() );
        itemList.add(item2);

        RequestsModel item3 = new RequestsModel(null, "Andrea Carl", "US$165", "Pending", "15 Oct - 27 Nov", "Dean St, Brooklyn, NY, USA", tableMessage() );
        itemList.add(item3);

        return itemList;
    }

    // Method to pass the arguments
    // for the elements
    // of child RecyclerView
    private List<TableRowModel> tableMessage()
    {
        List<TableRowModel> ChildItemList = new ArrayList<>();

        ChildItemList.add(new TableRowModel("Card 1"));
        ChildItemList.add(new TableRowModel("Card 2"));
        ChildItemList.add(new TableRowModel("Card 3"));
        ChildItemList.add(new TableRowModel("Card 4"));

        return ChildItemList;
    }

    private List<TableRowModel> tableMessage2()
    {
        List<TableRowModel> ChildItemList = new ArrayList<>();

        ChildItemList.add(new TableRowModel("Card 1"));
        ChildItemList.add(new TableRowModel("Card 2"));

        return ChildItemList;
    }
}