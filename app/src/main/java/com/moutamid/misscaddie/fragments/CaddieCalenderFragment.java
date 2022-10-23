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

import java.util.ArrayList;
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
    private ArrayList<String> dates = new ArrayList<>();

    TextView octTv1, octTv2, octTv3, octTv4, octTv5, octTv6, octTv7, octTv8, octTv9,
            octTv10, octTv11, octTv12, octTv13, octTv14, octTv15, octTv16, octTv17, octTv18, octTv19,
            octTv20, octTv21, octTv22, octTv23, octTv24, octTv25, octTv26, octTv27, octTv28, octTv29,
            octTv30, octTv31;

    TextView novTv1, novTv2, novTv3, novTv4, novTv5, novTv6, novTv7, novTv8, novTv9,
            novTv10, novTv11, novTv12, novTv13, novTv14, novTv15, novTv16, novTv17, novTv18, novTv19,
            novTv20, novTv21, novTv22, novTv23, novTv24, novTv25, novTv26, novTv27, novTv28, novTv29,
            novTv30;

    TextView decTv1, decTv2, decTv3, decTv4, decTv5, decTv6, decTv7, decTv8, decTv9,
            decTv10, decTv11, decTv12, decTv13, decTv14, decTv15, decTv16, decTv17, decTv18, decTv19,
            decTv20, decTv21, decTv22, decTv23, decTv24, decTv25, decTv26, decTv27, decTv28, decTv29,
            decTv30, decTv31;

    public CaddieCalenderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_caddie_calender, container, false);
        welcomeText = view.findViewById(R.id.text_heading);
        datetext = view.findViewById(R.id.date);
        greetingMessage();
        mAuth = FirebaseAuth.getInstance();
        currrentUser = mAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference().child("Caddie");
        checkAvailability();

        initOctTextViews();
        initNovTextViews();
        initDecTextViews();
        
        return view;
    }

    private void checkAvailability() {
        if (isAdded()) {
            db.child(currrentUser.getUid()).child("availability").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            String date = ds.child("date").getValue().toString();
                            int last = date.lastIndexOf(" ");
                            String month = date.substring(last,date.length());
                           // Toast.makeText(getActivity(), lastname, Toast.LENGTH_SHORT).show();
                            if (month.equals(" Oct")) {
                                String day = date.substring(0, 2);
                                setOctDatesRound(day);
                            }else if (month.equals(" Nov")) {
                                String day = date.substring(0, 2);
                                setNovDatesRound(day);
                            }else if (month.equals(" Dec")) {
                                String day = date.substring(0, 2);
                                setDecDatesRound(day);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void setDecDatesRound(String day) {
        if (decTv1.getText().equals(day)){
            decTv1.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            decTv1.setTextColor(getResources().getColor(R.color.white));
        }
        if (decTv2.getText().equals(day)){
            decTv2.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            decTv2.setTextColor(getResources().getColor(R.color.white));
        }
        if (decTv3.getText().equals(day)){
            decTv3.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            decTv3.setTextColor(getResources().getColor(R.color.white));
        }
        if (decTv4.getText().equals(day)){
            decTv4.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            decTv4.setTextColor(getResources().getColor(R.color.white));
        }
        if (decTv5.getText().equals(day)){
            decTv5.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            decTv5.setTextColor(getResources().getColor(R.color.white));
        }
        if (decTv6.getText().equals(day)){
            decTv6.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            decTv6.setTextColor(getResources().getColor(R.color.white));
        }
        if (decTv7.getText().equals(day)){
            decTv7.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            decTv7.setTextColor(getResources().getColor(R.color.white));
        }
        if (decTv8.getText().equals(day)){
            decTv8.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            decTv8.setTextColor(getResources().getColor(R.color.white));
        }
        if (decTv9.getText().equals(day)){
            decTv9.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            decTv9.setTextColor(getResources().getColor(R.color.white));
        }
        if (decTv10.getText().equals(day)){
            decTv10.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            decTv10.setTextColor(getResources().getColor(R.color.white));
        }
        if (decTv11.getText().equals(day)){
            decTv11.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            decTv11.setTextColor(getResources().getColor(R.color.white));
        }
        if (decTv12.getText().equals(day)){
            decTv12.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            decTv12.setTextColor(getResources().getColor(R.color.white));
        }
        if (decTv13.getText().equals(day)){
            decTv13.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            decTv13.setTextColor(getResources().getColor(R.color.white));
        }
        if (decTv14.getText().equals(day)){
            decTv14.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            decTv14.setTextColor(getResources().getColor(R.color.white));
        }
        if (decTv15.getText().equals(day)){
            decTv15.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            decTv15.setTextColor(getResources().getColor(R.color.white));
        }
        if (decTv16.getText().equals(day)){
            decTv16.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            decTv16.setTextColor(getResources().getColor(R.color.white));
        }
        if (decTv17.getText().equals(day)){
            decTv17.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            decTv17.setTextColor(getResources().getColor(R.color.white));
        }
        if (decTv18.getText().equals(day)){
            decTv18.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            decTv18.setTextColor(getResources().getColor(R.color.white));
        }
        if (decTv19.getText().equals(day)){
            decTv19.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            decTv19.setTextColor(getResources().getColor(R.color.white));
        }
        if (decTv20.getText().equals(day)){
            decTv20.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            decTv20.setTextColor(getResources().getColor(R.color.white));
        }
        if (decTv21.getText().equals(day)){
            decTv21.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            decTv21.setTextColor(getResources().getColor(R.color.white));
        }
        if (decTv22.getText().equals(day)){
            decTv22.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            decTv22.setTextColor(getResources().getColor(R.color.white));
        }
        if (decTv23.getText().equals(day)){
            decTv23.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            decTv23.setTextColor(getResources().getColor(R.color.white));
        }
        if (decTv24.getText().equals(day)){
            decTv24.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            decTv24.setTextColor(getResources().getColor(R.color.white));
        }
        if (decTv25.getText().equals(day)){
            decTv25.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            decTv25.setTextColor(getResources().getColor(R.color.white));
        }
        if (decTv26.getText().equals(day)){
            decTv26.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            decTv26.setTextColor(getResources().getColor(R.color.white));
        }
        if (decTv27.getText().equals(day)){
            decTv27.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            decTv27.setTextColor(getResources().getColor(R.color.white));
        }
        if (decTv28.getText().equals(day)){
            decTv28.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            decTv28.setTextColor(getResources().getColor(R.color.white));
        }
        if (decTv29.getText().equals(day)){
            decTv29.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            decTv29.setTextColor(getResources().getColor(R.color.white));
        }
        if (decTv30.getText().equals(day)){
            decTv30.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            decTv30.setTextColor(getResources().getColor(R.color.white));
        }
        if (decTv31.getText().equals(day)){
            decTv31.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            decTv31.setTextColor(getResources().getColor(R.color.white));
        }

    }

    private void setNovDatesRound(String day) {
        if (novTv1.getText().equals(day)){
            novTv1.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            novTv1.setTextColor(getResources().getColor(R.color.white));
        }
        if (novTv2.getText().equals(day)){
            novTv2.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            novTv2.setTextColor(getResources().getColor(R.color.white));
        }
        if (novTv3.getText().equals(day)){
            novTv3.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            novTv3.setTextColor(getResources().getColor(R.color.white));
        }
        if (novTv4.getText().equals(day)){
            novTv4.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            novTv4.setTextColor(getResources().getColor(R.color.white));
        }
        if (novTv5.getText().equals(day)){
            novTv5.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            novTv5.setTextColor(getResources().getColor(R.color.white));
        }
        if (novTv6.getText().equals(day)){
            novTv6.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            novTv6.setTextColor(getResources().getColor(R.color.white));
        }
        if (novTv7.getText().equals(day)){
            novTv7.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            novTv7.setTextColor(getResources().getColor(R.color.white));
        }
        if (novTv8.getText().equals(day)){
            novTv8.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            novTv8.setTextColor(getResources().getColor(R.color.white));
        }
        if (novTv9.getText().equals(day)){
            novTv9.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            novTv9.setTextColor(getResources().getColor(R.color.white));
        }
        if (novTv10.getText().equals(day)){
            novTv10.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            novTv10.setTextColor(getResources().getColor(R.color.white));
        }
        if (novTv11.getText().equals(day)){
            novTv11.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            novTv11.setTextColor(getResources().getColor(R.color.white));
        }
        if (novTv12.getText().equals(day)){
            novTv12.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            novTv12.setTextColor(getResources().getColor(R.color.white));
        }
        if (novTv13.getText().equals(day)){
            novTv13.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            novTv13.setTextColor(getResources().getColor(R.color.white));
        }
        if (novTv14.getText().equals(day)){
            novTv14.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            novTv14.setTextColor(getResources().getColor(R.color.white));
        }
        if (novTv15.getText().equals(day)){
            novTv15.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            novTv15.setTextColor(getResources().getColor(R.color.white));
        }
        if (novTv16.getText().equals(day)){
            novTv16.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            novTv16.setTextColor(getResources().getColor(R.color.white));
        }
        if (novTv17.getText().equals(day)){
            novTv17.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            novTv17.setTextColor(getResources().getColor(R.color.white));
        }
        if (novTv18.getText().equals(day)){
            novTv18.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            novTv18.setTextColor(getResources().getColor(R.color.white));
        }
        if (novTv19.getText().equals(day)){
            novTv19.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            novTv19.setTextColor(getResources().getColor(R.color.white));
        }
        if (novTv20.getText().equals(day)){
            novTv20.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            novTv20.setTextColor(getResources().getColor(R.color.white));
        }
        if (novTv21.getText().equals(day)){
            novTv21.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            novTv21.setTextColor(getResources().getColor(R.color.white));
        }
        if (novTv22.getText().equals(day)){
            novTv22.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            novTv22.setTextColor(getResources().getColor(R.color.white));
        }
        if (novTv23.getText().equals(day)){
            novTv23.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            novTv23.setTextColor(getResources().getColor(R.color.white));
        }
        if (novTv24.getText().equals(day)){
            novTv24.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            novTv24.setTextColor(getResources().getColor(R.color.white));
        }
        if (novTv25.getText().equals(day)){
            novTv25.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            novTv25.setTextColor(getResources().getColor(R.color.white));
        }
        if (novTv26.getText().equals(day)){
            novTv26.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            novTv26.setTextColor(getResources().getColor(R.color.white));
        }
        if (novTv27.getText().equals(day)){
            novTv27.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            novTv27.setTextColor(getResources().getColor(R.color.white));
        }
        if (novTv28.getText().equals(day)){
            novTv28.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            novTv28.setTextColor(getResources().getColor(R.color.white));
        }
        if (novTv29.getText().equals(day)){
            novTv29.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            novTv29.setTextColor(getResources().getColor(R.color.white));
        }
        if (novTv30.getText().equals(day)){
            novTv30.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            novTv30.setTextColor(getResources().getColor(R.color.white));
        }

    }

    private void setOctDatesRound(String day) {
        if (octTv1.getText().equals(day)){
            octTv1.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            octTv1.setTextColor(getResources().getColor(R.color.white));
        }
        if (octTv2.getText().equals(day)){
            octTv2.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            octTv2.setTextColor(getResources().getColor(R.color.white));
        }
        if (octTv3.getText().equals(day)){
            octTv3.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            octTv3.setTextColor(getResources().getColor(R.color.white));
        }
        if (octTv4.getText().equals(day)){
            octTv4.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            octTv4.setTextColor(getResources().getColor(R.color.white));
        }
        if (octTv5.getText().equals(day)){
            octTv5.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            octTv5.setTextColor(getResources().getColor(R.color.white));
        }
        if (octTv6.getText().equals(day)){
            octTv6.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            octTv6.setTextColor(getResources().getColor(R.color.white));
        }
        if (octTv7.getText().equals(day)){
            octTv7.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            octTv7.setTextColor(getResources().getColor(R.color.white));
        }
        if (octTv8.getText().equals(day)){
            octTv8.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            octTv8.setTextColor(getResources().getColor(R.color.white));
        }
        if (octTv9.getText().equals(day)){
            octTv9.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            octTv9.setTextColor(getResources().getColor(R.color.white));
        }
        if (octTv10.getText().equals(day)){
            octTv10.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            octTv10.setTextColor(getResources().getColor(R.color.white));
        }
        if (octTv11.getText().equals(day)){
            octTv11.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            octTv11.setTextColor(getResources().getColor(R.color.white));
        }
        if (octTv12.getText().equals(day)){
            octTv12.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            octTv12.setTextColor(getResources().getColor(R.color.white));
        }
        if (octTv13.getText().equals(day)){
            octTv13.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            octTv13.setTextColor(getResources().getColor(R.color.white));
        }
        if (octTv14.getText().equals(day)){
            octTv14.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            octTv14.setTextColor(getResources().getColor(R.color.white));
        }
        if (octTv15.getText().equals(day)){
            octTv15.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            octTv15.setTextColor(getResources().getColor(R.color.white));
        }
        if (octTv16.getText().equals(day)){
            octTv16.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            octTv16.setTextColor(getResources().getColor(R.color.white));
        }
        if (octTv17.getText().equals(day)){
            octTv17.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            octTv17.setTextColor(getResources().getColor(R.color.white));
        }
        if (octTv18.getText().equals(day)){
            octTv18.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            octTv18.setTextColor(getResources().getColor(R.color.white));
        }
        if (octTv19.getText().equals(day)){
            octTv19.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            octTv19.setTextColor(getResources().getColor(R.color.white));
        }
        if (octTv20.getText().equals(day)){
            octTv20.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            octTv20.setTextColor(getResources().getColor(R.color.white));
        }
        if (octTv21.getText().equals(day)){
            octTv21.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            octTv21.setTextColor(getResources().getColor(R.color.white));
        }
        if (octTv22.getText().equals(day)){
            octTv22.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            octTv22.setTextColor(getResources().getColor(R.color.white));
        }
        if (octTv23.getText().equals(day)){
            octTv23.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            octTv23.setTextColor(getResources().getColor(R.color.white));
        }
        if (octTv24.getText().equals(day)){
            octTv24.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            octTv24.setTextColor(getResources().getColor(R.color.white));
        }
        if (octTv25.getText().equals(day)){
            octTv25.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            octTv25.setTextColor(getResources().getColor(R.color.white));
        }
        if (octTv26.getText().equals(day)){
            octTv26.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            octTv26.setTextColor(getResources().getColor(R.color.white));
        }
        if (octTv27.getText().equals(day)){
            octTv27.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            octTv27.setTextColor(getResources().getColor(R.color.white));
        }
        if (octTv28.getText().equals(day)){
            octTv28.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            octTv28.setTextColor(getResources().getColor(R.color.white));
        }
        if (octTv29.getText().equals(day)){
            octTv29.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            octTv29.setTextColor(getResources().getColor(R.color.white));
        }
        if (octTv30.getText().equals(day)){
            octTv30.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            octTv30.setTextColor(getResources().getColor(R.color.white));
        }
        if (octTv31.getText().equals(day)){
            octTv31.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            octTv31.setTextColor(getResources().getColor(R.color.white));
        }

    }


    boolean octClick = false;
    @SuppressLint("NewApi")
    public void OctoberDatesClick(View view) {
        if (isAdded()) {
            TextView t = (TextView) view;
            availabilty = t.getText() + " Oct";
            if (!octClick) {
               // String key = t.getText().toString();
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("date", availabilty);
                db.child(currrentUser.getUid()).child("availability").child(availabilty).updateChildren(hashMap);
                if (t.getBackground() != null) {
                    t.setBackgroundResource(0);
                    t.setTextColor(getResources().getColor(R.color.black));
                } else {
                    t.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
                    t.setTextColor(getResources().getColor(R.color.white));
                }
                octClick = true;
            } else {
                //String key = t.getText().toString();
                db.child(currrentUser.getUid()).child("availability").child(availabilty).removeValue();
                t.setBackgroundResource(0);
                t.setTextColor(getResources().getColor(R.color.black));
                octClick = false;
            }
        }
    }
    boolean novClick = false;
    public void NovemberDatesClick(View view) {
        if (isAdded()) {
            TextView t = (TextView) view;
            availabilty = t.getText() + " Nov";
            if (!novClick) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("date", availabilty);
                db.child(currrentUser.getUid()).child("availability").child(availabilty).updateChildren(hashMap);
                if (t.getBackground() != null) {
                    t.setBackgroundResource(0);
                    t.setTextColor(getResources().getColor(R.color.black));
                } else {
                    t.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
                    t.setTextColor(getResources().getColor(R.color.white));
                    //    }
                }
                novClick = true;
            }else {
                db.child(currrentUser.getUid()).child("availability").child(availabilty).removeValue();
                t.setBackgroundResource(0);
                t.setTextColor(getResources().getColor(R.color.black));
                novClick = false;
            }
        }
    }
    boolean decClick = false;
    public void DecemberDatesClick(View view) {
        if (isAdded()) {
            TextView t = (TextView) view;
            availabilty = t.getText() + " Dec";
            if (!decClick) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("date", availabilty);
                db.child(currrentUser.getUid()).child("availability").child(availabilty).updateChildren(hashMap);
                if (t.getBackground() != null) {
                    t.setBackgroundResource(0);
                    t.setTextColor(getResources().getColor(R.color.black));
                } else {
                    t.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
                    t.setTextColor(getResources().getColor(R.color.white));
                    //    }
                }
                decClick = true;
            }else {
                db.child(currrentUser.getUid()).child("availability").child(availabilty).removeValue();
                t.setBackgroundResource(0);
                t.setTextColor(getResources().getColor(R.color.black));
                decClick = false;
            }
        }
    }

    private void greetingMessage() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        DateFormat df = new DateFormat();
        String d = df.format("dd MMM, yyyy", new Date()).toString();

        datetext.setText(d);

        if (timeOfDay >= 0 && timeOfDay < 12) {
            welcomeText.setText("Good Morning");
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            welcomeText.setText("Good Afternoon");
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            welcomeText.setText("Good Evening");
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            welcomeText.setText("Good Night");
        }
    }

    private void initOctTextViews() {
        octTv1 = view.findViewById(R.id.oct_tv_1);
        octTv2 = view.findViewById(R.id.oct_tv_2);
        octTv3 = view.findViewById(R.id.oct_tv_3);
        octTv4 = view.findViewById(R.id.oct_tv_4);
        octTv5 = view.findViewById(R.id.oct_tv_5);
        octTv6 = view.findViewById(R.id.oct_tv_6);
        octTv7 = view.findViewById(R.id.oct_tv_7);
        octTv8 = view.findViewById(R.id.oct_tv_8);
        octTv9 = view.findViewById(R.id.oct_tv_9);
        octTv10 = view.findViewById(R.id.oct_tv_10);
        octTv11 = view.findViewById(R.id.oct_tv_11);
        octTv12 = view.findViewById(R.id.oct_tv_12);
        octTv13 = view.findViewById(R.id.oct_tv_13);
        octTv14 = view.findViewById(R.id.oct_tv_14);
        octTv15 = view.findViewById(R.id.oct_tv_15);
        octTv16 = view.findViewById(R.id.oct_tv_16);
        octTv17 = view.findViewById(R.id.oct_tv_17);
        octTv18 = view.findViewById(R.id.oct_tv_18);
        octTv19 = view.findViewById(R.id.oct_tv_19);
        octTv20 = view.findViewById(R.id.oct_tv_20);
        octTv21 = view.findViewById(R.id.oct_tv_21);
        octTv22 = view.findViewById(R.id.oct_tv_22);
        octTv23 = view.findViewById(R.id.oct_tv_23);
        octTv24 = view.findViewById(R.id.oct_tv_24);
        octTv25 = view.findViewById(R.id.oct_tv_25);
        octTv26 = view.findViewById(R.id.oct_tv_26);
        octTv27 = view.findViewById(R.id.oct_tv_27);
        octTv28 = view.findViewById(R.id.oct_tv_28);
        octTv29 = view.findViewById(R.id.oct_tv_29);
        octTv30 = view.findViewById(R.id.oct_tv_30);
        octTv31 = view.findViewById(R.id.oct_tv_31);
    }

    private void initNovTextViews() {
        novTv1 = view.findViewById(R.id.nov_tv_1);
        novTv2 = view.findViewById(R.id.nov_tv_2);
        novTv3 = view.findViewById(R.id.nov_tv_3);
        novTv4 = view.findViewById(R.id.nov_tv_4);
        novTv5 = view.findViewById(R.id.nov_tv_5);
        novTv6 = view.findViewById(R.id.nov_tv_6);
        novTv7 = view.findViewById(R.id.nov_tv_7);
        novTv8 = view.findViewById(R.id.nov_tv_8);
        novTv9 = view.findViewById(R.id.nov_tv_9);
        novTv10 = view.findViewById(R.id.nov_tv_10);
        novTv11 = view.findViewById(R.id.nov_tv_11);
        novTv12 = view.findViewById(R.id.nov_tv_12);
        novTv13 = view.findViewById(R.id.nov_tv_13);
        novTv14 = view.findViewById(R.id.nov_tv_14);
        novTv15 = view.findViewById(R.id.nov_tv_15);
        novTv16 = view.findViewById(R.id.nov_tv_16);
        novTv17 = view.findViewById(R.id.nov_tv_17);
        novTv18 = view.findViewById(R.id.nov_tv_18);
        novTv19 = view.findViewById(R.id.nov_tv_19);
        novTv20 = view.findViewById(R.id.nov_tv_20);
        novTv21 = view.findViewById(R.id.nov_tv_21);
        novTv22 = view.findViewById(R.id.nov_tv_22);
        novTv23 = view.findViewById(R.id.nov_tv_23);
        novTv24 = view.findViewById(R.id.nov_tv_24);
        novTv25 = view.findViewById(R.id.nov_tv_25);
        novTv26 = view.findViewById(R.id.nov_tv_26);
        novTv27 = view.findViewById(R.id.nov_tv_27);
        novTv28 = view.findViewById(R.id.nov_tv_28);
        novTv29 = view.findViewById(R.id.nov_tv_29);
        novTv30 = view.findViewById(R.id.nov_tv_30);
    }

    private void initDecTextViews() {
        decTv1 = view.findViewById(R.id.dec_tv_1);
        decTv2 = view.findViewById(R.id.dec_tv_2);
        decTv3 = view.findViewById(R.id.dec_tv_3);
        decTv4 = view.findViewById(R.id.dec_tv_4);
        decTv5 = view.findViewById(R.id.dec_tv_5);
        decTv6 = view.findViewById(R.id.dec_tv_6);
        decTv7 = view.findViewById(R.id.dec_tv_7);
        decTv8 = view.findViewById(R.id.dec_tv_8);
        decTv9 = view.findViewById(R.id.dec_tv_9);
        decTv10 = view.findViewById(R.id.dec_tv_10);
        decTv11 = view.findViewById(R.id.dec_tv_11);
        decTv12 = view.findViewById(R.id.dec_tv_12);
        decTv13 = view.findViewById(R.id.dec_tv_13);
        decTv14 = view.findViewById(R.id.dec_tv_14);
        decTv15 = view.findViewById(R.id.dec_tv_15);
        decTv16 = view.findViewById(R.id.dec_tv_16);
        decTv17 = view.findViewById(R.id.dec_tv_17);
        decTv18 = view.findViewById(R.id.dec_tv_18);
        decTv19 = view.findViewById(R.id.dec_tv_19);
        decTv20 = view.findViewById(R.id.dec_tv_20);
        decTv21 = view.findViewById(R.id.dec_tv_21);
        decTv22 = view.findViewById(R.id.dec_tv_22);
        decTv23 = view.findViewById(R.id.dec_tv_23);
        decTv24 = view.findViewById(R.id.dec_tv_24);
        decTv25 = view.findViewById(R.id.dec_tv_25);
        decTv26 = view.findViewById(R.id.dec_tv_26);
        decTv27 = view.findViewById(R.id.dec_tv_27);
        decTv28 = view.findViewById(R.id.dec_tv_28);
        decTv29 = view.findViewById(R.id.dec_tv_29);
        decTv30 = view.findViewById(R.id.dec_tv_30);
        decTv31 = view.findViewById(R.id.dec_tv_31);
    }

}