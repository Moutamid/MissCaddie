package com.moutamid.misscaddie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class MessagesActivity extends AppCompatActivity {
    ImageView back_btn, profile_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);


        profile_btn = findViewById(R.id.profiles);

        profile_btn.setOnClickListener(v -> {
            Intent intent = new Intent(MessagesActivity.this , GolferProfileEditActivity.class);
            startActivity(intent);
            Animatoo.animateSlideLeft(MessagesActivity.this);
        });
    }
}