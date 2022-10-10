package com.moutamid.misscaddie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class Register_Caddie extends AppCompatActivity {

    TextView cont_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_caddie);

        cont_btn = findViewById(R.id.cont_btn2);
        cont_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register_Caddie.this , SignUp_Caddie.class);
                startActivity(intent);
                Animatoo.animateZoom(Register_Caddie.this);
            }
        });
    }
}