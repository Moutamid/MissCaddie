package com.moutamid.misscaddie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.moutamid.misscaddie.fragments.CaddiesRequestsActivity;

public class MessagesActivity extends AppCompatActivity {
    ImageView back_btn, profile_btn;
    TextView myRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        profile_btn = findViewById(R.id.profiles);
        myRequest = findViewById(R.id.myRequest);

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
}