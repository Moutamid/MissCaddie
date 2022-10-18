package com.moutamid.misscaddie;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moutamid.misscaddie.adapters.CalenderAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class CaddieAvailabiltyActivity extends AppCompatActivity{

    RecyclerView calender;
    CalenderAdapter adapter;
    ArrayList<String> months = new ArrayList<>();
    TextView almostFinished;
    private DatePicker picker;
    FirebaseAuth mAuth;
    FirebaseUser currrentUser;
    ProgressDialog dialog;
    private DatabaseReference db;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caddie_availabilty);

        almostFinished = findViewById(R.id.almostFinished);
        picker = findViewById(R.id.datePicker);

        mAuth = FirebaseAuth.getInstance();
        currrentUser = mAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference().child("Caddie");
        almostFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String availabilty = picker.getDayOfMonth() + "/" + (picker.getMonth() + 1) + "/" + picker.getYear();
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("availability",availabilty);
                db.child(currrentUser.getUid()).updateChildren(hashMap);
                //Toast.makeText(CaddieAvailabiltyActivity.this,availabilty,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CaddieAvailabiltyActivity.this ,
                        CaddieDashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
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

    /*public void OctoberDatesClick(View view) {
        TextView t = (TextView) view;
        Toast.makeText(this, "October " + t.getText() + ", 2022", Toast.LENGTH_SHORT).show();
        if (t.getBackground() != null){
            t.setBackgroundResource(0);
            t.setTextColor(getResources().getColor(R.color.black));
        } else {
            t.setBackground(getDrawable(R.drawable.circle_yellow));
            t.setTextColor(getResources().getColor(R.color.white));
        }
    }

    public void NovemberDatesClick(View view) {
        TextView t = (TextView) view;
        Toast.makeText(this, "November " + t.getText() + ", 2022", Toast.LENGTH_SHORT).show();
        if (t.getBackground() != null){
            t.setBackgroundResource(0);
            t.setTextColor(getResources().getColor(R.color.black));
        } else {
            t.setBackground(getDrawable(R.drawable.circle_yellow));
            t.setTextColor(getResources().getColor(R.color.white));
        }
    }

    public void DecemberDatesClick(View view) {
        TextView t = (TextView) view;
        Toast.makeText(this, "December " + t.getText() + ", 2022", Toast.LENGTH_SHORT).show();
        if (t.getBackground() != null){
            t.setBackgroundResource(0);
            t.setTextColor(getResources().getColor(R.color.black));
        } else {
            t.setBackground(getDrawable(R.drawable.circle_yellow));
            t.setTextColor(getResources().getColor(R.color.white));
        }
    }*/
}