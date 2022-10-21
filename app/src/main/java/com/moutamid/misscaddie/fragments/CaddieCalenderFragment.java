package com.moutamid.misscaddie.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.misscaddie.R;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class CaddieCalenderFragment extends Fragment {
    TextView welcomeText, datetext;
    View view;
    FirebaseAuth mAuth;
    FirebaseUser currrentUser;
    ProgressDialog dialog;
    private DatabaseReference db;
    private String availabilty = "";

    public CaddieCalenderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_caddie_calender, container, false);
        welcomeText = view.findViewById(R.id.text_heading);
        datetext = view.findViewById(R.id.date);
        greetingMessage();

//        checkAvailability();

        return view;
    }


    @SuppressLint("NewApi")
    public void OctoberDatesClick(View view) {
      //  if (isAdded()) {
            TextView t = (TextView) view;
            availabilty = t.getText() + " Oct";
            mAuth = FirebaseAuth.getInstance();
            currrentUser = mAuth.getCurrentUser();
            db = FirebaseDatabase.getInstance().getReference().child("Caddie");
            String key = db.child(currrentUser.getUid()).child("availability").push().getKey();
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("date",availabilty);
            db.child(currrentUser.getUid()).child("availability").child(key).updateChildren(hashMap);
            if (t.getBackground() != null) {
            t.setBackgroundResource(0);
            t.setTextColor(getResources().getColor(R.color.black));
            } else {
            t.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            t.setTextColor(getResources().getColor(R.color.white));
        //    }
        }
    }

    public void NovemberDatesClick(View view) {
        TextView t = (TextView) view;
        availabilty = t.getText() + " Nov";
        String key = db.child(currrentUser.getUid()).child("availability").push().getKey();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("date",availabilty);
        db.child(currrentUser.getUid()).child("availability").child(key).updateChildren(hashMap);
        if (isAdded()) {
            if (t.getBackground() != null) {
                t.setBackgroundResource(0);
                t.setTextColor(getResources().getColor(R.color.black));
            } else {
                t.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
                t.setTextColor(getResources().getColor(R.color.white));
            }
        }
    }

    public void DecemberDatesClick(View view) {
        TextView t = (TextView) view;
        availabilty = t.getText() + " Dec";
        String key = db.child(currrentUser.getUid()).child("availability").push().getKey();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("date",availabilty);
        db.child(currrentUser.getUid()).child("availability").child(key).updateChildren(hashMap);
        if (isAdded()) {
            if (t.getBackground() != null) {
                t.setBackgroundResource(0);
                t.setTextColor(getResources().getColor(R.color.black));
            } else {
                t.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
                t.setTextColor(getResources().getColor(R.color.white));
            }
        }
    }

    private void greetingMessage() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        DateFormat df = new DateFormat();
        String d = df.format("dd MMM, yyyy", new Date()).toString();

        datetext.setText(d);

        if(timeOfDay >= 0 && timeOfDay < 12){
            welcomeText.setText("Good Morning");
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            welcomeText.setText("Good Afternoon");
        }else if(timeOfDay >= 16 && timeOfDay < 21){
            welcomeText.setText("Good Evening");
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            welcomeText.setText("Good Night");
        }
    }
}