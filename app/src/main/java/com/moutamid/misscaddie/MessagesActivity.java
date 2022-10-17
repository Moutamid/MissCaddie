package com.moutamid.misscaddie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.moutamid.misscaddie.adapters.IncomingMessageAdapter;
import com.moutamid.misscaddie.adapters.RequestesAdapter;
import com.moutamid.misscaddie.models.IncomingMessageModel;
import com.moutamid.misscaddie.models.RequestsModel;

import java.util.ArrayList;
import java.util.List;

public class MessagesActivity extends AppCompatActivity {
    ImageView back_btn, profile_btn;
    TextView myRequest;
    RecyclerView messges_golfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        profile_btn = findViewById(R.id.profiles);
        myRequest = findViewById(R.id.myRequest);
        messges_golfer = findViewById(R.id.messges_golfer);

        IncomingMessageAdapter adapter = new IncomingMessageAdapter(this, RequestItems());

        messges_golfer.setAdapter(adapter);
        messges_golfer.setLayoutManager(new LinearLayoutManager(this));
        messges_golfer.setHasFixedSize(false);

        profile_btn.setOnClickListener(v -> {
            Intent intent = new Intent(MessagesActivity.this , GolferProfileEditActivity.class);
            startActivity(intent);
            Animatoo.animateSlideLeft(MessagesActivity.this);
        });

        myRequest.setOnClickListener(v -> {
            Intent intent = new Intent(MessagesActivity.this , CaddiesRequestsActivity.class);
            startActivity(intent);
            Animatoo.animateSlideLeft(MessagesActivity.this);
        });


    }

    private ArrayList<IncomingMessageModel> RequestItems() {
        ArrayList<IncomingMessageModel> itemList = new ArrayList<>();

        IncomingMessageModel item = new IncomingMessageModel(null, "Suleman Ijaz", "Ok Good Bye", "06/11/2022");
        itemList.add(item);

        IncomingMessageModel item2 = new IncomingMessageModel(null, "M. Moutamid", "Ok Good Bye", "25/04/2022");
        itemList.add(item2);

        IncomingMessageModel item3 = new IncomingMessageModel(null, "Karl", "See you soon", "15/05/2022");
        itemList.add(item3);

        return itemList;
    }
}