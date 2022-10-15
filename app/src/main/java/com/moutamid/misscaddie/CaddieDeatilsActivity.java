package com.moutamid.misscaddie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class CaddieDeatilsActivity extends AppCompatActivity {

    TextView almostFinished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caddie_deatils);

        almostFinished = findViewById(R.id.almostFinished);
        almostFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CaddieDeatilsActivity.this , CaddieAvailabiltyActivity.class);
                startActivity(intent);
                Animatoo.animateZoom(CaddieDeatilsActivity.this);
            }
        });
    }
}