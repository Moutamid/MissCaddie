package com.moutamid.misscaddie;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class MainActivity extends AppCompatActivity {

    TextView golfer_btn;
    TextView caddie_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        golfer_btn = findViewById(R.id.golfer_btn);
        golfer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this ,Golfer_Start1.class);
                startActivity(intent);
                Animatoo.animateZoom(MainActivity.this);
            }
        });

        caddie_btn = findViewById(R.id.caddie_btn);
        caddie_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this ,Caddie_Start1.class);
                startActivity(intent);
                Animatoo.animateZoom(MainActivity.this);
            }
        });
    }
}