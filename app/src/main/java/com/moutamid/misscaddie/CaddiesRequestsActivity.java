package com.moutamid.misscaddie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class CaddiesRequestsActivity extends AppCompatActivity {
    ImageView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caddies_requests);

        back_btn = findViewById(R.id.back_btn);

        back_btn.setOnClickListener(v -> {
            Intent intent = new Intent(CaddiesRequestsActivity.this , Dashboard_Golfer.class);
            startActivity(intent);
            Animatoo.animateZoom(CaddiesRequestsActivity.this);
        });
    }
}