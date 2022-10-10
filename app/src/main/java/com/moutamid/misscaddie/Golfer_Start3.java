package com.moutamid.misscaddie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class Golfer_Start3 extends AppCompatActivity {

    RelativeLayout gs1;
    TextView skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_golfer_start3);

        gs1 = findViewById(R.id.gs3);
        gs1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Golfer_Start3.this ,Golfer_Start4.class);
                startActivity(intent);
                Animatoo.animateSwipeLeft(Golfer_Start3.this);
            }
        });
        skip = findViewById(R.id.text_skip3);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Golfer_Start3.this ,Golfer_Start4.class);
                startActivity(intent);
                Animatoo.animateSwipeLeft(Golfer_Start3.this);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Golfer_Start3.this , MainActivity.class);
        startActivity(intent);
        Animatoo.animateSwipeRight(Golfer_Start3.this);
        finish();
        super.onBackPressed();
    }
}