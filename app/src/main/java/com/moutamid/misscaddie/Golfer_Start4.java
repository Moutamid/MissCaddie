package com.moutamid.misscaddie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusbarYellow));
        }

        getStrarted = findViewById(R.id.getStrarted);
        getStrarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Golfer_Start4.this , Register_Golfer.class);
                startActivity(intent);
                Animatoo.animateZoom(Golfer_Start4.this);
            }
        });

        if(isOpenAlread()) {
            Intent intent=new Intent(Golfer_Start4.this, Register_Golfer.class);
            startActivity(intent);
            finish();
        }
        else
        {
            SharedPreferences.Editor editor =getSharedPreferences("slide", MODE_PRIVATE).edit();
            editor.putBoolean("slide",true);
            editor.commit();
        }
    }

    private boolean isOpenAlread() {
        SharedPreferences sharedPreferences=getSharedPreferences("slide", MODE_PRIVATE);
        boolean result= sharedPreferences.getBoolean("slide", false);
        return result;
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