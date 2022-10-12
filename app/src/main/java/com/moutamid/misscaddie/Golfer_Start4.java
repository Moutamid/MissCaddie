package com.moutamid.misscaddie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class Golfer_Start4 extends AppCompatActivity {

    TextView getStrarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_golfer_start4);

        getStrarted = findViewById(R.id.getStrarted);
        getStrarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Golfer_Start4.this , Register_Golfer.class);
                startActivity(intent);
                Animatoo.animateZoom(Golfer_Start4.this);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Golfer_Start4.this , MainActivity.class);
        startActivity(intent);
        Animatoo.animateSwipeRight(Golfer_Start4.this);
        finish();
        super.onBackPressed();
    }
}