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
                            String day = date.substring(0, 2);
                            dates.add(day);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }


    @SuppressLint("NewApi")
    public void OctoberDatesClick(View view) {
        if (isAdded()) {
            TextView t = (TextView) view;
            availabilty = t.getText() + " Oct";

            String key = db.child(currrentUser.getUid()).child("availability").push().getKey();
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("date", availabilty);
            db.child(currrentUser.getUid()).child("availability").child(key).updateChildren(hashMap);
            if (t.getBackground() != null) {
                t.setBackgroundResource(0);
                t.setTextColor(getResources().getColor(R.color.black));
            } else {
                t.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
                t.setTextColor(getResources().getColor(R.color.white));
            }
        }
    }

    public void NovemberDatesClick(View view) {
        if (isAdded()) {
            TextView t = (TextView) view;
            availabilty = t.getText() + " Nov";
            String key = db.child(currrentUser.getUid()).child("availability").push().getKey();
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("date", availabilty);
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
    }

    public void DecemberDatesClick(View view) {
        if (isAdded()) {
            TextView t = (TextView) view;
            availabilty = t.getText() + " Dec";
            String key = db.child(currrentUser.getUid()).child("availability").push().getKey();
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("date", availabilty);
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