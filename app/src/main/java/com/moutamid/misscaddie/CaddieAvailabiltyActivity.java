package com.moutamid.misscaddie;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
    private String availabilty = "";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caddie_availabilty);

        almostFinished = findViewById(R.id.almostFinished);
        //picker = findViewById(R.id.datePicker);
        changeStatusBarColor(this,R.color.yellow);
        mAuth = FirebaseAuth.getInstance();
        currrentUser = mAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference().child("Caddie");
        almostFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

    public void OctoberDatesClick(View view) {
        TextView t = (TextView) view;
        availabilty = t.getText() + " Oct";
        String key = db.child(currrentUser.getUid()).child("availability").push().getKey();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("date",availabilty);
        db.child(currrentUser.getUid()).child("availability").child(key).updateChildren(hashMap);

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
        availabilty = t.getText() + " Nov";
        String key = db.child(currrentUser.getUid()).child("availability").push().getKey();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("date",availabilty);
        db.child(currrentUser.getUid()).child("availability").child(key).updateChildren(hashMap);

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
        availabilty = t.getText() + " Dec";
        String key = db.child(currrentUser.getUid()).child("availability").push().getKey();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("date",availabilty);
        db.child(currrentUser.getUid()).child("availability").child(key).updateChildren(hashMap);
        if (t.getBackground() != null){
            t.setBackgroundResource(0);
            t.setTextColor(getResources().getColor(R.color.black));
        } else {
            t.setBackground(getDrawable(R.drawable.circle_yellow));
            t.setTextColor(getResources().getColor(R.color.white));
        }
    }
}