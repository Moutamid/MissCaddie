package com.moutamid.misscaddie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class Caddie_Start2 extends AppCompatActivity {


    RelativeLayout gs1;
    TextView skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caddie_start2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusbarYellow));
        }

        gs1 = findViewById(R.id.cs2);
        gs1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Caddie_Start2.this ,Caddie_Start3.class);
                startActivity(intent);
                Animatoo.animateSwipeLeft(Caddie_Start2.this);
            }
        });
        skip = findViewById(R.id.text_skip5);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Caddie_Start2.this ,Caddie_Start4.class);
                startActivity(intent);
                Animatoo.animateSwipeLeft(Caddie_Start2.this);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Caddie_Start2.this , MainActivity.class);
        startActivity(intent);
        Animatoo.animateSwipeRight(Caddie_Start2.this);
        finish();
        super.onBackPressed();
    }
}