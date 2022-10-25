package com.moutamid.misscaddie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class Golfer_Start2 extends AppCompatActivity {

    RelativeLayout gs1;
    TextView nextBtn,backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_golfer_start2);

        gs1 = findViewById(R.id.gs2);
        gs1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Golfer_Start2.this ,Golfer_Start3.class);
                startActivity(intent);
                Animatoo.animateSwipeLeft(Golfer_Start2.this);
            }
        });
        nextBtn = findViewById(R.id.text_next);
        backBtn = findViewById(R.id.text_back);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Golfer_Start2.this ,Golfer_Start3.class);
                startActivity(intent);
                Animatoo.animateSwipeLeft(Golfer_Start2.this);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Golfer_Start2.this ,Golfer_Start1.class);
                startActivity(intent);
                Animatoo.animateSwipeLeft(Golfer_Start2.this);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Golfer_Start2.this , MainActivity.class);
        startActivity(intent);
        Animatoo.animateSwipeRight(Golfer_Start2.this);
        finish();
        super.onBackPressed();
    }
}