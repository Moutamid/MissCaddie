package com.moutamid.misscaddie;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class Caddie_Start4 extends AppCompatActivity {

    TextView getStrarted;
    private SharedPreferencesManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caddie_start4);

        getStrarted = findViewById(R.id.getStrarted2);
        manager = new SharedPreferencesManager(Caddie_Start4.this);

        getStrarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Caddie_Start4.this , Register_Caddie.class);
                startActivity(intent);
                Animatoo.animateZoom(Caddie_Start4.this);
            }
        });

        if(isOpenAlread()) {
            Intent intent=new Intent(Caddie_Start4.this, Register_Caddie.class);
            startActivity(intent);
            finish();
        }
        else
        {
            SharedPreferences.Editor editor =getSharedPreferences("slide", MODE_PRIVATE).edit();
            editor.putBoolean("slide",true);
            editor.commit();
        }
        changeStatusBarColor(this,R.color.yellow);
    }

    public void changeStatusBarColor(Activity activity, int id) {

        // Changing the color of status bar
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(activity.getResources().getColor(id));
        }

        // CHANGE STATUS BAR TO TRANSPARENT
        //window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    private boolean isOpenAlread() {
        SharedPreferences sharedPreferences=getSharedPreferences("slide", MODE_PRIVATE);
        boolean result= sharedPreferences.getBoolean("slide", false);
        return result;
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Caddie_Start4.this , MainActivity.class);
        startActivity(intent);
        Animatoo.animateSwipeRight(Caddie_Start4.this);
        finish();
        super.onBackPressed();
    }
}