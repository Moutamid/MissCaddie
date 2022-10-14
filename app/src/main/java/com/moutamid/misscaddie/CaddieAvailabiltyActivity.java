package com.moutamid.misscaddie;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

public class CaddieAvailabiltyActivity extends AppCompatActivity{

    RecyclerView calender;
    CalenderAdapter adapter;
    ArrayList<String> months = new ArrayList<>();
    TextView almostFinished;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caddie_availabilty);

        almostFinished = findViewById(R.id.almostFinished);
        almostFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CaddieAvailabiltyActivity.this , CaddieDashboardActivity.class);
                startActivity(intent);
                Animatoo.animateZoom(CaddieAvailabiltyActivity.this);
            }
        });

        /*calender = findViewById(R.id.calenderRV);

        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");

        adapter = new CalenderAdapter(this, months);

        calender.setLayoutManager(new LinearLayoutManager(this));
        calender.setHasFixedSize(false);
        calender.setAdapter(adapter);*/

    }

}