package com.moutamid.misscaddie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class SignUp_Caddie extends AppCompatActivity {

    TextView signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_caddie);

        signUpBtn = findViewById(R.id.signUpBtn2);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp_Caddie.this , Login_Caddie.class);
                startActivity(intent);
                Animatoo.animateZoom(SignUp_Caddie.this);
            }
        });
    }
}