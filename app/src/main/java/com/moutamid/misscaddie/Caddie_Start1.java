package com.moutamid.misscaddie;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class Caddie_Start1 extends AppCompatActivity {

    RelativeLayout cs1;
    TextView nextBtn,backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caddie_start1);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusbarYellow));
        }

        cs1 = findViewById(R.id.cs1);
        cs1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Caddie_Start1.this ,Caddie_Start2.class);
                startActivity(intent);
                Animatoo.animateSwipeLeft(Caddie_Start1.this);
            }
        });
        nextBtn = findViewById(R.id.text_next);
        backBtn = findViewById(R.id.text_back);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Caddie_Start1.this ,Caddie_Start2.class);
                startActivity(intent);
                Animatoo.animateSwipeLeft(Caddie_Start1.this);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Caddie_Start1.this ,MainActivity.class);
                startActivity(intent);
                Animatoo.animateSwipeLeft(Caddie_Start1.this);
            }
        });
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Caddie_Start1.this , MainActivity.class);
        startActivity(intent);
        Animatoo.animateSwipeRight(Caddie_Start1.this);
        finish();
        super.onBackPressed();
    }
}