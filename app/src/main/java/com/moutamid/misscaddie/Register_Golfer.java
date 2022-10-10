package com.moutamid.misscaddie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class Register_Golfer extends AppCompatActivity {

    TextView cont_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_golfer);

        cont_btn = findViewById(R.id.cont_btn);
        cont_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register_Golfer.this , SignUp_Golfer.class);
                startActivity(intent);
                Animatoo.animateZoom(Register_Golfer.this);
            }
        });
    }
}