package com.moutamid.misscaddie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class Login_Caddie extends AppCompatActivity {
    TextView signInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_caddie);

        signInBtn = findViewById(R.id.signInBtn2);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Caddie.this , CaddieDeatilsActivity.class);
                startActivity(intent);
                Animatoo.animateZoom(Login_Caddie.this);
            }
        });
    }
}