package com.moutamid.misscaddie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class CaddieContactActivity extends AppCompatActivity {
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caddie_contact);
        backBtn = findViewById(R.id.back_btn);

        backBtn.setOnClickListener(v -> {
            onBackPressed();
            Animatoo.animateSwipeLeft(CaddieContactActivity.this);
        });

    }
}