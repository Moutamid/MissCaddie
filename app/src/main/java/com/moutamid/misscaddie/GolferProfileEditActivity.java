package com.moutamid.misscaddie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.moutamid.misscaddie.fragments.CaddiesRequestsActivity;

public class GolferProfileEditActivity extends AppCompatActivity {
    ImageView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_golfer_profile_edit);

        back_btn = findViewById(R.id.back_btn);

        back_btn.setOnClickListener(v -> {
            Intent intent = new Intent(GolferProfileEditActivity.this , CaddiesRequestsActivity.class);
            startActivity(intent);
            Animatoo.animateSlideRight(GolferProfileEditActivity.this);
        });
    }
}