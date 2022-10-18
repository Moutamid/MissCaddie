package com.moutamid.misscaddie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class Splash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1500;
    private SharedPreferencesManager manager;
    private String module = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        manager = new SharedPreferencesManager(Splash.this);
        module = manager.retrieveString("module","");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() { //MainActivity
                if (module.equals("")) {
                    Intent homeIntent = new Intent(Splash.this, MainActivity.class);
                    startActivity(homeIntent);
                    Animatoo.animateZoom(Splash.this);
                    finish();
                }else if (module.equals("golfer")){
                    Intent homeIntent = new Intent(Splash.this, Dashboard_Golfer.class);
                    startActivity(homeIntent);
                    Animatoo.animateZoom(Splash.this);
                    finish();
                }else {
                    Intent homeIntent = new Intent(Splash.this, CaddieDashboardActivity.class);
                    startActivity(homeIntent);
                    Animatoo.animateZoom(Splash.this);
                    finish();
                }
            }
        },SPLASH_TIME_OUT);
    }
}