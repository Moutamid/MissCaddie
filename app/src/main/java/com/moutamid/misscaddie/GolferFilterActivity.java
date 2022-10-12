package com.moutamid.misscaddie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class GolferFilterActivity extends AppCompatActivity {
    View close_btn;
    LinearLayout notWillingLayout, WillingLayout;
    boolean willingState = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_golfer_filter);

        close_btn = findViewById(R.id.close);
        notWillingLayout = findViewById(R.id.notWilling_layout);
        WillingLayout = findViewById(R.id.willing_layout);

        close_btn.setOnClickListener(v -> {
            Intent intent = new Intent(GolferFilterActivity.this , Dashboard_Golfer.class);
            startActivity(intent);
            Animatoo.animateZoom(GolferFilterActivity.this);
        });

        notWillingLayout.setOnClickListener(v ->{
            if (willingState){
                notWillingLayout.setBackground(getDrawable(R.drawable.unselected_box));
                willingState = false;
            } else {
                notWillingLayout.setBackground(getDrawable(R.drawable.selected_box));
                willingState = true;
            }
        });
        WillingLayout.setOnClickListener(v ->{
            if (willingState){
                WillingLayout.setBackground(getDrawable(R.drawable.unselected_box));
                willingState = false;
            } else {
                WillingLayout.setBackground(getDrawable(R.drawable.selected_box));
                willingState = true;
            }
        });
    }
}