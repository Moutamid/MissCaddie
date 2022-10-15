package com.moutamid.misscaddie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class GolferFilterActivity extends AppCompatActivity {
    View close_btn;
    LinearLayout notWillingLayout, WillingLayout;
    TextView willingTv, notWillingTv;
    ImageView iconsNot, iconWill;
    boolean willingState = true, notWillingState = false;
    CardView willingCard, notWillingCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_golfer_filter);

        close_btn = findViewById(R.id.close);
        notWillingLayout = findViewById(R.id.notWilling);
        WillingLayout = findViewById(R.id.willing);
        iconsNot = findViewById(R.id.notWilling_icon);
        iconWill = findViewById(R.id.willing_icon);
        willingCard = findViewById(R.id.willing_layoutCard);
        notWillingCard = findViewById(R.id.notWilling_layoutCard);
        willingTv = findViewById(R.id.willing_tv);
        notWillingTv = findViewById(R.id.notwilling_tv);

        close_btn.setOnClickListener(v -> {
            Intent intent = new Intent(GolferFilterActivity.this , Dashboard_Golfer.class);
            startActivity(intent);
            Animatoo.animateZoom(GolferFilterActivity.this);
        });

        notWillingCard.setOnClickListener(v -> {
                    if (notWillingState) {
                        WillingLayout.setBackground(getDrawable(R.drawable.selected_box));
                        //iconWill.setBackground(getDrawable(R.drawable.ic_charm_tick1));
                        notWillingLayout.setBackground(getDrawable(R.drawable.unselected_box));
                        //iconsNot.setBackground(getDrawable(R.drawable.ic_charm_cross2));
                        willingState = true;
                        willingTv.setTextColor(getResources().getColor(R.color.black));
                        notWillingTv.setTextColor(getResources().getColor(R.color.black_light));
                        notWillingState = false;
                    } else {
                        notWillingLayout.setBackground(getDrawable(R.drawable.selected_box));
                        //iconsNot.setBackground(getDrawable(R.drawable.ic_charm_cross));
                        notWillingState = true;
                        WillingLayout.setBackground(getDrawable(R.drawable.unselected_box));
                        //iconWill.setBackground(getDrawable(R.drawable.ic_charm_tick));
                        willingTv.setTextColor(getResources().getColor(R.color.black_light));
                        notWillingTv.setTextColor(getResources().getColor(R.color.black));
                        willingState = false;
                    }
                });
        willingCard.setOnClickListener(v ->{
            if (willingState){
                WillingLayout.setBackground(getDrawable(R.drawable.unselected_box));
                //iconWill.setBackground(getDrawable(R.drawable.ic_charm_tick));
                willingState = false;
                notWillingLayout.setBackground(getDrawable(R.drawable.selected_box));
                //iconsNot.setBackground(getDrawable(R.drawable.ic_charm_cross));
                notWillingState = true;
                willingTv.setTextColor(getResources().getColor(R.color.black_light));
                notWillingTv.setTextColor(getResources().getColor(R.color.black));
            } else {
                WillingLayout.setBackground(getDrawable(R.drawable.selected_box));
                //iconWill.setBackground(getDrawable(R.drawable.ic_charm_tick1));
                notWillingLayout.setBackground(getDrawable(R.drawable.unselected_box));
                //iconsNot.setBackground(getDrawable(R.drawable.ic_charm_cross2));
                willingState = true;
                willingTv.setTextColor(getResources().getColor(R.color.black));
                notWillingTv.setTextColor(getResources().getColor(R.color.black_light));
                notWillingState = false;
            }
        });
    }
}