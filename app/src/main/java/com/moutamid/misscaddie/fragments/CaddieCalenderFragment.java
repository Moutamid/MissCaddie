package com.moutamid.misscaddie.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
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
   // View view;
    FirebaseAuth mAuth;
    FirebaseUser currrentUser;
    private DatabaseReference db;
    private String availabilty = "";

    TextView janTv1, janTv2, janTv3, janTv4, janTv5, janTv6, janTv7, janTv8, janTv9,
            janTv10, janTv11, janTv12, janTv13, janTv14, janTv15, janTv16, janTv17, janTv18, janTv19,
            janTv20, janTv21, janTv22, janTv23, janTv24, janTv25, janTv26, janTv27, janTv28, janTv29,
            janTv30, janTv31;

    TextView febTv1, febTv2, febTv3, febTv4, febTv5, febTv6, febTv7, febTv8, febTv9,
            febTv10, febTv11, febTv12, febTv13, febTv14, febTv15, febTv16, febTv17, febTv18, febTv19,
            febTv20, febTv21, febTv22, febTv23, febTv24, febTv25, febTv26, febTv27, febTv28;

    TextView marTv1, marTv2, marTv3, marTv4, marTv5, marTv6, marTv7, marTv8, marTv9,
            marTv10,marTv11, marTv12, marTv13, marTv14, marTv15, marTv16, marTv17, marTv18, marTv19,
            marTv20, marTv21, marTv22, marTv23, marTv24, marTv25, marTv26, marTv27, marTv28, marTv29,
            marTv30, marTv31;

    TextView aprTv1, aprTv2, aprTv3, aprTv4, aprTv5, aprTv6, aprTv7, aprTv8, aprTv9,
            aprTv10, aprTv11, aprTv12, aprTv13, aprTv14, aprTv15, aprTv16, aprTv17, aprTv18, aprTv19,
            aprTv20, aprTv21, aprTv22, aprTv23, aprTv24, aprTv25, aprTv26, aprTv27, aprTv28, aprTv29,
            aprTv30;

    TextView mayTv1, mayTv2, mayTv3, mayTv4, mayTv5, mayTv6, mayTv7, mayTv8, mayTv9,
            mayTv10, mayTv11, mayTv12, mayTv13, mayTv14, mayTv15, mayTv16, mayTv17, mayTv18, mayTv19,
            mayTv20, mayTv21, mayTv22, mayTv23, mayTv24, mayTv25, mayTv26, mayTv27, mayTv28, mayTv29,
            mayTv30, mayTv31;

    TextView junTv1, junTv2, junTv3, junTv4, junTv5, junTv6, junTv7, junTv8, junTv9,
            junTv10, junTv11, junTv12, junTv13, junTv14, junTv15, junTv16, junTv17, junTv18, junTv19,
            junTv20, junTv21, junTv22, junTv23, junTv24, junTv25, junTv26, junTv27, junTv28, junTv29,
            junTv30;

    TextView julTv1, julTv2, julTv3, julTv4, julTv5, julTv6, julTv7, julTv8, julTv9,
            julTv10, julTv11, julTv12, julTv13, julTv14, julTv15, julTv16, julTv17, julTv18, julTv19,
            julTv20, julTv21, julTv22, julTv23, julTv24, julTv25, julTv26, julTv27, julTv28, julTv29,
            julTv30,julTv31;

    TextView augTv1, augTv2, augTv3, augTv4, augTv5, augTv6, augTv7, augTv8, augTv9,
            augTv10, augTv11, augTv12, augTv13, augTv14, augTv15, augTv16, augTv17, augTv18, augTv19,
            augTv20, augTv21, augTv22, augTv23, augTv24, augTv25, augTv26, augTv27, augTv28, augTv29,
            augTv30,augTv31;

    TextView sepTv1, sepTv2, sepTv3, sepTv4, sepTv5, sepTv6, sepTv7, sepTv8, sepTv9,
            sepTv10, sepTv11, sepTv12, sepTv13, sepTv14, sepTv15, sepTv16, sepTv17, sepTv18, sepTv19,
            sepTv20, sepTv21, sepTv22, sepTv23, sepTv24, sepTv25, sepTv26, sepTv27, sepTv28, sepTv29,
            sepTv30;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_caddie_calender, container, false);
        if (isAdded()) {
            welcomeText = view.findViewById(R.id.text_heading);
            datetext = view.findViewById(R.id.date);
            greetingMessage();
            mAuth = FirebaseAuth.getInstance();
            currrentUser = mAuth.getCurrentUser();
            db = FirebaseDatabase.getInstance().getReference().child("Caddie");
            checkAvailability();

            initJanTextViews(view);
            initFebTextViews(view);
            initMarTextViews(view);
            initAprTextViews(view);
            initMayTextViews(view);
            initJunTextViews(view);
            initJulTextViews(view);
            initAugTextViews(view);
            initSepTextViews(view);
            initOctTextViews(view);
            initNovTextViews(view);
            initDecTextViews(view);
        }
        
        return view;
    }

    private void checkAvailability() {
        if (isAdded()) {
            db.child(currrentUser.getUid()).child("availability")
                    .addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            String date = ds.child("date").getValue().toString();
                            //int last = date.indexOf("3");
                            //String month = date.substring(last,date.length());
                            String month = date.substring(0, 3);
                          //  Toast.makeText(getActivity(), month, Toast.LENGTH_SHORT).show();
                            if (month.equals("Jan")) {
                                if (date.length() == 5){
                                    String day = date.substring(date.length() - 1);
                                    setJanDatesRound(day);
                                }else {
                                    String day = date.substring(date.length() - 2);
                                    setJanDatesRound(day);
                                }

                            }
                            else if (month.equals("Feb")) {
                                if (date.length() == 5){
                                    String day = date.substring(date.length() - 1);
                                    setFebDatesRound(day);
                                }else {
                                    String day = date.substring(date.length() - 2);
                                    setFebDatesRound(day);
                                }

                            }
                            else if (month.equals("Mar")) {
                                if (date.length() == 5){
                                    String day = date.substring(date.length() - 1);
                                    setMarDatesRound(day);
                                }else {
                                    String day = date.substring(date.length() - 2);
                                    setMarDatesRound(day);
                                }

                            }
                            else if (month.equals("Apr")) {
                                if (date.length() == 5){
                                    String day = date.substring(date.length() - 1);
                                    setAprDatesRound(day);
                                }else {
                                    String day = date.substring(date.length() - 2);
                                    setAprDatesRound(day);
                                }

                            }
                            else if (month.equals("May")) {
                                if (date.length() == 5){
                                    String day = date.substring(date.length() - 1);
                                    setMayDatesRound(day);
                                }else {
                                    String day = date.substring(date.length() - 2);
                                    setMayDatesRound(day);
                                }

                            }
                            else if (month.equals("Jun")) {
                                if (date.length() == 5){
                                    String day = date.substring(date.length() - 1);
                                    setJunDatesRound(day);
                                }else {
                                    String day = date.substring(date.length() - 2);
                                    setJunDatesRound(day);
                                }

                            }
                            else if (month.equals("Jul")) {
                                if (date.length() == 5){
                                    String day = date.substring(date.length() - 1);
                                    setJulDatesRound(day);
                                }else {
                                    String day = date.substring(date.length() - 2);
                                    setJulDatesRound(day);
                                }

                            }
                            else if (month.equals("Aug")) {
                                if (date.length() == 5){
                                    String day = date.substring(date.length() - 1);
                                    setAugDatesRound(day);
                                }else {
                                    String day = date.substring(date.length() - 2);
                                    setAugDatesRound(day);
                                }

                            }
                            else if (month.equals("Sep")) {
                                if (date.length() == 5){
                                    String day = date.substring(date.length() - 1);
                                    setSepDatesRound(day);
                                }else {
                                    String day = date.substring(date.length() - 2);
                                    setSepDatesRound(day);
                                }

                            }

                            else if (month.equals("Oct")) {
                                if (date.length() == 5){
                                    String day = date.substring(date.length() - 1);
                                    setOctDatesRound(day);
                                }else {
                                    String day = date.substring(date.length() - 2);
                                    setOctDatesRound(day);
                                }

                            }else if (month.equals("Nov")) {
                                if (date.length() == 5){
                                    String day = date.substring(date.length() - 1);
                                    setNovDatesRound(day);
                                }else {
                                    String day = date.substring(date.length() - 2);
                                    setNovDatesRound(day);
                                }
                            }else if (month.equals("Dec")) {
                                if (date.length() == 5){
                                    String day = date.substring(date.length() - 1);
                                    setDecDatesRound(day);
                                }else {
                                    String day = date.substring(date.length() - 2);
                                    setDecDatesRound(day);
                                }
                            }
                        }
                    }else {
                      //  storeAvailabilty();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void initDecTextViews(View view) {
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



    private void setDecDatesRound(String day) {
        if (isAdded()) {
            if (decTv1.getText().equals(day)) {
                decTv1.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                decTv1.setTextColor(getResources().getColor(R.color.white));

            }
            if (decTv2.getText().equals(day)) {
                decTv2.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                decTv2.setTextColor(getResources().getColor(R.color.white));

            }
            if (decTv3.getText().equals(day)) {
                decTv3.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                decTv3.setTextColor(getResources().getColor(R.color.white));

            }
            if (decTv4.getText().equals(day)) {
                decTv4.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                decTv4.setTextColor(getResources().getColor(R.color.white));

            }
            if (decTv5.getText().equals(day)) {
                decTv5.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                decTv5.setTextColor(getResources().getColor(R.color.white));

            }
            if (decTv6.getText().equals(day)) {
                decTv6.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                decTv6.setTextColor(getResources().getColor(R.color.white));

            }
            if (decTv7.getText().equals(day)) {
                decTv7.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                decTv7.setTextColor(getResources().getColor(R.color.white));

            }
            if (decTv8.getText().equals(day)) {
                decTv8.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                decTv8.setTextColor(getResources().getColor(R.color.white));

            }
            if (decTv9.getText().equals(day)) {
                decTv9.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                decTv9.setTextColor(getResources().getColor(R.color.white));

            }
            if (decTv10.getText().equals(day)) {
                decTv10.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                decTv10.setTextColor(getResources().getColor(R.color.white));

            }
            if (decTv11.getText().equals(day)) {
                decTv11.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                decTv11.setTextColor(getResources().getColor(R.color.white));

            }
            if (decTv12.getText().equals(day)) {
                decTv12.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                decTv12.setTextColor(getResources().getColor(R.color.white));

            }
            if (decTv13.getText().equals(day)) {
                decTv13.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                decTv13.setTextColor(getResources().getColor(R.color.white));

            }
            if (decTv14.getText().equals(day)) {
                decTv14.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                decTv14.setTextColor(getResources().getColor(R.color.white));

            }
            if (decTv15.getText().equals(day)) {
                decTv15.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                decTv15.setTextColor(getResources().getColor(R.color.white));

            }
            if (decTv16.getText().equals(day)) {
                decTv16.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                decTv16.setTextColor(getResources().getColor(R.color.white));

            }
            if (decTv17.getText().equals(day)) {
                decTv17.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                decTv17.setTextColor(getResources().getColor(R.color.white));

            }
            if (decTv18.getText().equals(day)) {
                decTv18.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                decTv18.setTextColor(getResources().getColor(R.color.white));

            }
            if (decTv19.getText().equals(day)) {
                decTv19.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                decTv19.setTextColor(getResources().getColor(R.color.white));

            }
            if (decTv20.getText().equals(day)) {
                decTv20.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                decTv20.setTextColor(getResources().getColor(R.color.white));

            }
            if (decTv21.getText().equals(day)) {
                decTv21.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                decTv21.setTextColor(getResources().getColor(R.color.white));

            }
            if (decTv22.getText().equals(day)) {
                decTv22.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                decTv22.setTextColor(getResources().getColor(R.color.white));

            }
            if (decTv23.getText().equals(day)) {
                decTv23.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                decTv23.setTextColor(getResources().getColor(R.color.white));

            }
            if (decTv24.getText().equals(day)) {
                decTv24.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                decTv24.setTextColor(getResources().getColor(R.color.white));

            }
            if (decTv25.getText().equals(day)) {
                decTv25.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                decTv25.setTextColor(getResources().getColor(R.color.white));

            }
            if (decTv26.getText().equals(day)) {
                decTv26.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                decTv26.setTextColor(getResources().getColor(R.color.white));

            }
            if (decTv27.getText().equals(day)) {
                decTv27.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                decTv27.setTextColor(getResources().getColor(R.color.white));

            }
            if (decTv28.getText().equals(day)) {
                decTv28.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                decTv28.setTextColor(getResources().getColor(R.color.white));
            }
            if (decTv29.getText().equals(day)) {
                decTv29.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                decTv29.setTextColor(getResources().getColor(R.color.white));

            }
            if (decTv30.getText().equals(day)) {
                decTv30.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                decTv30.setTextColor(getResources().getColor(R.color.white));

            }
            if (decTv31.getText().equals(day)) {
                decTv31.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                decTv31.setTextColor(getResources().getColor(R.color.white));

            }
        }
    }

    private void storeAvailabilty() {

        for (int i = 1; i <=31; i++){
            availabilty = "Oct " + i;
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("date", availabilty);
            hashMap.put("available", true);
            db.child(currrentUser.getUid()).child("availability").child(availabilty).updateChildren(hashMap);
        }

        for (int i = 1; i <=30; i++){
            availabilty = "Nov " + i ;
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("date", availabilty);
            hashMap.put("available", true);
            db.child(currrentUser.getUid()).child("availability").child(availabilty).updateChildren(hashMap);
        }
        for (int i = 1; i <=31; i++){
            availabilty = "Dec " + i;
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("date", availabilty);
            hashMap.put("available", true);
            db.child(currrentUser.getUid()).child("availability").child(availabilty).updateChildren(hashMap);
        }

    }


    private void setNovDatesRound(String day) {
        if (isAdded()) {
            if (novTv1.getText().equals(day)) {
                novTv1.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                novTv1.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (novTv2.getText().equals(day)) {
                novTv2.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                novTv2.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (novTv3.getText().equals(day)) {
                novTv3.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                novTv3.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (novTv4.getText().equals(day)) {
                novTv4.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                novTv4.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (novTv5.getText().equals(day)) {
                novTv5.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                novTv5.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (novTv6.getText().equals(day)) {
                novTv6.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                novTv6.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (novTv7.getText().equals(day)) {
                novTv7.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                novTv7.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (novTv8.getText().equals(day)) {
                novTv8.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                novTv8.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (novTv9.getText().equals(day)) {
                novTv9.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                novTv9.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (novTv10.getText().equals(day)) {
                novTv10.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                novTv10.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (novTv11.getText().equals(day)) {
                novTv11.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                novTv11.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (novTv12.getText().equals(day)) {
                novTv12.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                novTv12.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (novTv13.getText().equals(day)) {
                novTv13.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                novTv13.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (novTv14.getText().equals(day)) {
                novTv14.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                novTv14.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (novTv15.getText().equals(day)) {
                novTv15.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                novTv15.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (novTv16.getText().equals(day)) {
                novTv16.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                novTv16.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (novTv17.getText().equals(day)) {
                novTv17.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                novTv17.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (novTv18.getText().equals(day)) {
                novTv18.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                novTv18.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (novTv19.getText().equals(day)) {
                novTv19.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                novTv19.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (novTv20.getText().equals(day)) {
                novTv20.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                novTv20.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (novTv21.getText().equals(day)) {
                novTv21.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                novTv21.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (novTv22.getText().equals(day)) {
                novTv22.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                novTv22.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (novTv23.getText().equals(day)) {
                novTv23.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                novTv23.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (novTv24.getText().equals(day)) {
                novTv24.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                novTv24.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (novTv25.getText().equals(day)) {
                novTv25.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                novTv25.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (novTv26.getText().equals(day)) {
                novTv26.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                novTv26.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (novTv27.getText().equals(day)) {
                novTv27.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                novTv27.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (novTv28.getText().equals(day)) {
                novTv28.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                novTv28.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (novTv29.getText().equals(day)) {
                novTv29.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                novTv29.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (novTv30.getText().equals(day)) {
                novTv30.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                novTv30.setTextColor(getResources().getColor(R.color.white));
                
            }
        }
    }

    private void setOctDatesRound(String day) {
        if(isAdded()) {
            if (octTv1.getText().equals(day)) {
                octTv1.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                octTv1.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (octTv2.getText().equals(day)) {
                octTv2.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                octTv2.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (octTv3.getText().equals(day)) {
                octTv3.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                octTv3.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (octTv4.getText().equals(day)) {
                octTv4.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                octTv4.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (octTv5.getText().equals(day)) {
                octTv5.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                octTv5.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (octTv6.getText().equals(day)) {
                octTv6.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                octTv6.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (octTv7.getText().equals(day)) {
                octTv7.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                octTv7.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (octTv8.getText().equals(day)) {
                octTv8.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                octTv8.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (octTv9.getText().equals(day)) {
                octTv9.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                octTv9.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (octTv10.getText().equals(day)) {
                octTv10.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                octTv10.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (octTv11.getText().equals(day)) {
                octTv11.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                octTv11.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (octTv12.getText().equals(day)) {
                octTv12.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                octTv12.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (octTv13.getText().equals(day)) {
                octTv13.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                octTv13.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (octTv14.getText().equals(day)) {
                octTv14.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                octTv14.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (octTv15.getText().equals(day)) {
                octTv15.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                octTv15.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (octTv16.getText().equals(day)) {
                octTv16.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                octTv16.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (octTv17.getText().equals(day)) {
                octTv17.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                octTv17.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (octTv18.getText().equals(day)) {
                octTv18.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                octTv18.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (octTv19.getText().equals(day)) {
                octTv19.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                octTv19.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (octTv20.getText().equals(day)) {
                octTv20.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                octTv20.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (octTv21.getText().equals(day)) {
                octTv21.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                octTv21.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (octTv22.getText().equals(day)) {
                octTv22.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                octTv22.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (octTv23.getText().equals(day)) {
                octTv23.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                octTv23.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (octTv24.getText().equals(day)) {
                octTv24.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                octTv24.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (octTv25.getText().equals(day)) {
                octTv25.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                octTv25.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (octTv26.getText().equals(day)) {
                octTv26.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                octTv26.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (octTv27.getText().equals(day)) {
                octTv27.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                octTv27.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (octTv28.getText().equals(day)) {
                octTv28.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                octTv28.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (octTv29.getText().equals(day)) {
                octTv29.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                octTv29.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (octTv30.getText().equals(day)) {
                octTv30.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                octTv30.setTextColor(getResources().getColor(R.color.white));
                
            }
            if (octTv31.getText().equals(day)) {
                octTv31.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                octTv31.setTextColor(getResources().getColor(R.color.white));
                
            }
        }
    }

    private void setSepDatesRound(String day) {
        if (sepTv1.getText().equals(day)) {
            sepTv1.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            sepTv1.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv2.getText().equals(day)) {
            sepTv2.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            sepTv2.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv3.getText().equals(day)) {
            sepTv3.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            sepTv3.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv4.getText().equals(day)) {
            sepTv4.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            sepTv4.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv5.getText().equals(day)) {
            sepTv5.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            sepTv5.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv6.getText().equals(day)) {
            sepTv6.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            sepTv6.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv7.getText().equals(day)) {
            sepTv7.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            sepTv7.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv8.getText().equals(day)) {
            sepTv8.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            sepTv8.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv9.getText().equals(day)) {
            sepTv9.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            sepTv9.setTextColor(getResources().getColor(R.color.white));


        }
        if (sepTv10.getText().equals(day)) {
            sepTv10.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            sepTv10.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv11.getText().equals(day)) {
            sepTv11.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            sepTv11.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv12.getText().equals(day)) {
            sepTv12.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            sepTv12.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv13.getText().equals(day)) {
            sepTv13.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            sepTv13.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv14.getText().equals(day)) {
            sepTv14.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            sepTv14.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv15.getText().equals(day)) {
            sepTv15.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            sepTv15.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv16.getText().equals(day)) {
            sepTv16.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            sepTv16.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv17.getText().equals(day)) {
            sepTv17.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            sepTv17.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv18.getText().equals(day)) {
            sepTv18.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            sepTv18.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv19.getText().equals(day)) {
            sepTv19.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            sepTv19.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv20.getText().equals(day)) {
            sepTv20.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            sepTv20.setTextColor(getResources().getColor(R.color.white));

        }

        if (sepTv21.getText().equals(day)) {
            sepTv21.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            sepTv21.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv22.getText().equals(day)) {
            sepTv22.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            sepTv22.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv23.getText().equals(day)) {
            sepTv23.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            sepTv23.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv24.getText().equals(day)) {
            sepTv24.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            sepTv24.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv25.getText().equals(day)) {
            sepTv25.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            sepTv25.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv26.getText().equals(day)) {
            sepTv26.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            sepTv26.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv27.getText().equals(day)) {
            sepTv27.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            sepTv27.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv28.getText().equals(day)) {
            sepTv28.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            sepTv28.setTextColor(getResources().getColor(R.color.white));
        }
        if (sepTv29.getText().equals(day)) {
            sepTv29.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            sepTv29.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv30.getText().equals(day)) {
            sepTv30.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            sepTv30.setTextColor(getResources().getColor(R.color.white));

        }

    }

    private void setAugDatesRound(String day) {
        if (augTv1.getText().equals(day)) {
            augTv1.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            augTv1.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv2.getText().equals(day)) {
            augTv2.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            augTv2.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv3.getText().equals(day)) {
            augTv3.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            augTv3.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv4.getText().equals(day)) {
            augTv4.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            augTv4.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv5.getText().equals(day)) {
            augTv5.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            augTv5.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv6.getText().equals(day)) {
            augTv6.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            augTv6.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv7.getText().equals(day)) {
            augTv7.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            augTv7.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv8.getText().equals(day)) {
            augTv8.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            augTv8.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv9.getText().equals(day)) {
            augTv9.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            augTv9.setTextColor(getResources().getColor(R.color.white));


        }
        if (augTv10.getText().equals(day)) {
            augTv10.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            augTv10.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv11.getText().equals(day)) {
            augTv11.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            augTv11.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv12.getText().equals(day)) {
            augTv12.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            augTv12.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv13.getText().equals(day)) {
            augTv13.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            augTv13.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv14.getText().equals(day)) {
            augTv14.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            augTv14.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv15.getText().equals(day)) {
            augTv15.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            augTv15.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv16.getText().equals(day)) {
            augTv16.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            augTv16.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv17.getText().equals(day)) {
            augTv17.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            augTv17.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv18.getText().equals(day)) {
            augTv18.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            augTv18.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv19.getText().equals(day)) {
            augTv19.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            augTv19.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv20.getText().equals(day)) {
            augTv20.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            augTv20.setTextColor(getResources().getColor(R.color.white));

        }

        if (augTv21.getText().equals(day)) {
            augTv21.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            augTv21.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv22.getText().equals(day)) {
            augTv22.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            augTv22.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv23.getText().equals(day)) {
            augTv23.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            augTv23.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv24.getText().equals(day)) {
            augTv24.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            augTv24.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv25.getText().equals(day)) {
            augTv25.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            augTv25.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv26.getText().equals(day)) {
            augTv26.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            augTv26.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv27.getText().equals(day)) {
            augTv27.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            augTv27.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv28.getText().equals(day)) {
            augTv28.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            augTv28.setTextColor(getResources().getColor(R.color.white));
        }
        if (augTv29.getText().equals(day)) {
            augTv29.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            augTv29.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv30.getText().equals(day)) {
            augTv30.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            augTv30.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv31.getText().equals(day)) {
            augTv31.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            augTv31.setTextColor(getResources().getColor(R.color.white));

        }
    }

    private void setJulDatesRound(String day) {
        if (julTv1.getText().equals(day)) {
            julTv1.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            julTv1.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv2.getText().equals(day)) {
            julTv2.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            julTv2.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv3.getText().equals(day)) {
            julTv3.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            julTv3.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv4.getText().equals(day)) {
            julTv4.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            julTv4.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv5.getText().equals(day)) {
            julTv5.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            julTv5.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv6.getText().equals(day)) {
            julTv6.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            julTv6.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv7.getText().equals(day)) {
            julTv7.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            julTv7.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv8.getText().equals(day)) {
            julTv8.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            julTv8.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv9.getText().equals(day)) {
            julTv9.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            julTv9.setTextColor(getResources().getColor(R.color.white));


        }
        if (julTv10.getText().equals(day)) {
            julTv10.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            julTv10.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv11.getText().equals(day)) {
            julTv11.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            julTv11.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv12.getText().equals(day)) {
            julTv12.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            julTv12.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv13.getText().equals(day)) {
            julTv13.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            julTv13.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv14.getText().equals(day)) {
            julTv14.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            julTv14.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv15.getText().equals(day)) {
            julTv15.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            julTv15.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv16.getText().equals(day)) {
            julTv16.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            julTv16.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv17.getText().equals(day)) {
            julTv17.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            julTv17.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv18.getText().equals(day)) {
            julTv18.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            julTv18.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv19.getText().equals(day)) {
            julTv19.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            julTv19.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv20.getText().equals(day)) {
            julTv20.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            julTv20.setTextColor(getResources().getColor(R.color.white));

        }

        if (julTv21.getText().equals(day)) {
            julTv21.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            julTv21.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv22.getText().equals(day)) {
            julTv22.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            julTv22.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv23.getText().equals(day)) {
            julTv23.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            julTv23.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv24.getText().equals(day)) {
            julTv24.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            julTv24.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv25.getText().equals(day)) {
            julTv25.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            julTv25.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv26.getText().equals(day)) {
            julTv26.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            julTv26.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv27.getText().equals(day)) {
            julTv27.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            julTv27.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv28.getText().equals(day)) {
            julTv28.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            julTv28.setTextColor(getResources().getColor(R.color.white));
        }
        if (julTv29.getText().equals(day)) {
            julTv29.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            julTv29.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv30.getText().equals(day)) {
            julTv30.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            julTv30.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv31.getText().equals(day)) {
            julTv31.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            julTv31.setTextColor(getResources().getColor(R.color.white));

        }
    }

    private void setJunDatesRound(String day) {
        if (junTv1.getText().equals(day)) {
            junTv1.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            junTv1.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv2.getText().equals(day)) {
            junTv2.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            junTv2.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv3.getText().equals(day)) {
            junTv3.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            junTv3.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv4.getText().equals(day)) {
            junTv4.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            junTv4.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv5.getText().equals(day)) {
            junTv5.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            junTv5.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv6.getText().equals(day)) {
            junTv6.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            junTv6.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv7.getText().equals(day)) {
            junTv7.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            junTv7.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv8.getText().equals(day)) {
            junTv8.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            junTv8.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv9.getText().equals(day)) {
            junTv9.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            junTv9.setTextColor(getResources().getColor(R.color.white));


        }
        if (junTv10.getText().equals(day)) {
            junTv10.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            junTv10.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv11.getText().equals(day)) {
            junTv11.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            junTv11.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv12.getText().equals(day)) {
            junTv12.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            junTv12.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv13.getText().equals(day)) {
            junTv13.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            junTv13.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv14.getText().equals(day)) {
            junTv14.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            junTv14.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv15.getText().equals(day)) {
            junTv15.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            junTv15.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv16.getText().equals(day)) {
            junTv16.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            junTv16.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv17.getText().equals(day)) {
            junTv17.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            junTv17.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv18.getText().equals(day)) {
            junTv18.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            junTv18.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv19.getText().equals(day)) {
            junTv19.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            junTv19.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv20.getText().equals(day)) {
            junTv20.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            junTv20.setTextColor(getResources().getColor(R.color.white));

        }

        if (junTv21.getText().equals(day)) {
            junTv21.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            junTv21.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv22.getText().equals(day)) {
            junTv22.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            junTv22.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv23.getText().equals(day)) {
            junTv23.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            junTv23.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv24.getText().equals(day)) {
            junTv24.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            junTv24.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv25.getText().equals(day)) {
            junTv25.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            junTv25.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv26.getText().equals(day)) {
            junTv26.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            junTv26.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv27.getText().equals(day)) {
            junTv27.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            junTv27.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv28.getText().equals(day)) {
            junTv28.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            junTv28.setTextColor(getResources().getColor(R.color.white));
        }
        if (junTv29.getText().equals(day)) {
            junTv29.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            junTv29.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv30.getText().equals(day)) {
            junTv30.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            junTv30.setTextColor(getResources().getColor(R.color.white));

        }
    }

    private void setMayDatesRound(String day) {
        if (mayTv1.getText().equals(day)) {
            mayTv1.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            mayTv1.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv2.getText().equals(day)) {
            mayTv2.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            mayTv2.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv3.getText().equals(day)) {
            mayTv3.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            mayTv3.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv4.getText().equals(day)) {
            mayTv4.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            mayTv4.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv5.getText().equals(day)) {
            mayTv5.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            mayTv5.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv6.getText().equals(day)) {
            mayTv6.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            mayTv6.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv7.getText().equals(day)) {
            mayTv7.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            mayTv7.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv8.getText().equals(day)) {
            mayTv8.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            mayTv8.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv9.getText().equals(day)) {
            mayTv9.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            mayTv9.setTextColor(getResources().getColor(R.color.white));


        }
        if (mayTv10.getText().equals(day)) {
            mayTv10.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            mayTv10.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv11.getText().equals(day)) {
            mayTv11.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            mayTv11.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv12.getText().equals(day)) {
            mayTv12.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            mayTv12.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv13.getText().equals(day)) {
            mayTv13.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            mayTv13.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv14.getText().equals(day)) {
            mayTv14.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            mayTv14.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv15.getText().equals(day)) {
            mayTv15.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            mayTv15.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv16.getText().equals(day)) {
            mayTv16.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            mayTv16.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv17.getText().equals(day)) {
            mayTv17.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            mayTv17.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv18.getText().equals(day)) {
            mayTv18.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            mayTv18.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv19.getText().equals(day)) {
            mayTv19.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            mayTv19.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv20.getText().equals(day)) {
            mayTv20.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            mayTv20.setTextColor(getResources().getColor(R.color.white));

        }

        if (mayTv21.getText().equals(day)) {
            mayTv21.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            mayTv21.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv22.getText().equals(day)) {
            mayTv22.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            mayTv22.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv23.getText().equals(day)) {
            mayTv23.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            mayTv23.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv24.getText().equals(day)) {
            mayTv24.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            mayTv24.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv25.getText().equals(day)) {
            mayTv25.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            mayTv25.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv26.getText().equals(day)) {
            mayTv26.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            mayTv26.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv27.getText().equals(day)) {
            mayTv27.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            mayTv27.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv28.getText().equals(day)) {
            mayTv28.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            mayTv28.setTextColor(getResources().getColor(R.color.white));
        }
        if (mayTv29.getText().equals(day)) {
            mayTv29.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            mayTv29.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv30.getText().equals(day)) {
            mayTv30.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            mayTv30.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv31.getText().equals(day)) {
            mayTv31.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            mayTv31.setTextColor(getResources().getColor(R.color.white));

        }
    }

    private void setAprDatesRound(String day) {
        if (aprTv1.getText().equals(day)) {
            aprTv1.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            aprTv1.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv2.getText().equals(day)) {
            aprTv2.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            aprTv2.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv3.getText().equals(day)) {
            aprTv3.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            aprTv3.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv4.getText().equals(day)) {
            aprTv4.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            aprTv4.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv5.getText().equals(day)) {
            aprTv5.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            aprTv5.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv6.getText().equals(day)) {
            aprTv6.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            aprTv6.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv7.getText().equals(day)) {
            aprTv7.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            aprTv7.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv8.getText().equals(day)) {
            aprTv8.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            aprTv8.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv9.getText().equals(day)) {
            aprTv9.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            aprTv9.setTextColor(getResources().getColor(R.color.white));


        }
        if (aprTv10.getText().equals(day)) {
            aprTv10.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            aprTv10.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv11.getText().equals(day)) {
            aprTv11.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            aprTv11.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv12.getText().equals(day)) {
            aprTv12.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            aprTv12.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv13.getText().equals(day)) {
            aprTv13.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            aprTv13.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv14.getText().equals(day)) {
            aprTv14.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            aprTv14.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv15.getText().equals(day)) {
            aprTv15.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            aprTv15.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv16.getText().equals(day)) {
            aprTv16.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            aprTv16.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv17.getText().equals(day)) {
            aprTv17.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            aprTv17.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv18.getText().equals(day)) {
            aprTv18.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            aprTv18.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv19.getText().equals(day)) {
            aprTv19.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            aprTv19.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv20.getText().equals(day)) {
            aprTv20.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            aprTv20.setTextColor(getResources().getColor(R.color.white));

        }

        if (aprTv21.getText().equals(day)) {
            aprTv21.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            aprTv21.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv22.getText().equals(day)) {
            aprTv22.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            aprTv22.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv23.getText().equals(day)) {
            aprTv23.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            aprTv23.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv24.getText().equals(day)) {
            aprTv24.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            aprTv24.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv25.getText().equals(day)) {
            aprTv25.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            aprTv25.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv26.getText().equals(day)) {
            aprTv26.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            aprTv26.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv27.getText().equals(day)) {
            aprTv27.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            aprTv27.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv28.getText().equals(day)) {
            aprTv28.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            aprTv28.setTextColor(getResources().getColor(R.color.white));
        }
        if (aprTv29.getText().equals(day)) {
            aprTv29.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            aprTv29.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv30.getText().equals(day)) {
            aprTv30.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            aprTv30.setTextColor(getResources().getColor(R.color.white));

        }
    }

    private void setMarDatesRound(String day) {
    //    if (isAdded()) {
            if (marTv1.getText().equals(day)) {
                marTv1.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                marTv1.setTextColor(getResources().getColor(R.color.white));

            }
            if (marTv2.getText().equals(day)) {
                marTv2.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                marTv2.setTextColor(getResources().getColor(R.color.white));

            }
            if (marTv3.getText().equals(day)) {
                marTv3.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                marTv3.setTextColor(getResources().getColor(R.color.white));

            }
            if (marTv4.getText().equals(day)) {
                marTv4.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                marTv4.setTextColor(getResources().getColor(R.color.white));

            }
            if (marTv5.getText().equals(day)) {
                marTv5.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                marTv5.setTextColor(getResources().getColor(R.color.white));

            }
            if (marTv6.getText().equals(day)) {
                marTv6.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                marTv6.setTextColor(getResources().getColor(R.color.white));

            }
            if (marTv7.getText().equals(day)) {
                marTv7.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                marTv7.setTextColor(getResources().getColor(R.color.white));

            }
            if (marTv8.getText().equals(day)) {
                marTv8.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                marTv8.setTextColor(getResources().getColor(R.color.white));

            }
            if (marTv9.getText().equals(day)) {
                marTv9.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                marTv9.setTextColor(getResources().getColor(R.color.white));


            }
            if (marTv10.getText().equals(day)) {
                marTv10.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                marTv10.setTextColor(getResources().getColor(R.color.white));

            }
            if (marTv11.getText().equals(day)) {
                marTv11.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                marTv11.setTextColor(getResources().getColor(R.color.white));

            }
            if (marTv12.getText().equals(day)) {
                marTv12.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                marTv12.setTextColor(getResources().getColor(R.color.white));

            }
            if (marTv13.getText().equals(day)) {
                marTv13.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                marTv13.setTextColor(getResources().getColor(R.color.white));

            }
            if (marTv14.getText().equals(day)) {
                marTv14.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                marTv14.setTextColor(getResources().getColor(R.color.white));

            }
            if (marTv15.getText().equals(day)) {
                marTv15.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                marTv15.setTextColor(getResources().getColor(R.color.white));

            }
            if (marTv16.getText().equals(day)) {
                marTv16.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                marTv16.setTextColor(getResources().getColor(R.color.white));

            }
            if (marTv17.getText().equals(day)) {
                marTv17.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                marTv17.setTextColor(getResources().getColor(R.color.white));

            }
            if (marTv18.getText().equals(day)) {
                marTv18.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                marTv18.setTextColor(getResources().getColor(R.color.white));

            }
            if (marTv19.getText().equals(day)) {
                marTv19.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                marTv19.setTextColor(getResources().getColor(R.color.white));

            }
            if (marTv20.getText().equals(day)) {
                marTv20.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                marTv20.setTextColor(getResources().getColor(R.color.white));

            }

        if (marTv21.getText().equals(day)) {
            marTv21.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            marTv21.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv22.getText().equals(day)) {
            marTv22.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            marTv22.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv23.getText().equals(day)) {
            marTv23.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            marTv23.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv24.getText().equals(day)) {
            marTv24.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            marTv24.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv25.getText().equals(day)) {
            marTv25.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            marTv25.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv26.getText().equals(day)) {
            marTv26.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            marTv26.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv27.getText().equals(day)) {
            marTv27.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            marTv27.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv28.getText().equals(day)) {
            marTv28.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            marTv28.setTextColor(getResources().getColor(R.color.white));
        }
        if (marTv29.getText().equals(day)) {
            marTv29.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            marTv29.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv30.getText().equals(day)) {
            marTv30.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            marTv30.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv31.getText().equals(day)) {
            marTv31.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
            marTv31.setTextColor(getResources().getColor(R.color.white));

        }

        //  }
    }

    private void setFebDatesRound(String day) {
       // if (isAdded()) {
            if (febTv1.getText().equals(day)) {
                febTv1.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                febTv1.setTextColor(getResources().getColor(R.color.white));

            }
            if (febTv2.getText().equals(day)) {
                febTv2.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                febTv2.setTextColor(getResources().getColor(R.color.white));

            }
            if (febTv3.getText().equals(day)) {
                febTv3.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                febTv3.setTextColor(getResources().getColor(R.color.white));

            }
            if (febTv4.getText().equals(day)) {
                febTv4.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                febTv4.setTextColor(getResources().getColor(R.color.white));

            }
            if (febTv5.getText().equals(day)) {
                febTv5.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                febTv5.setTextColor(getResources().getColor(R.color.white));

            }
            if (febTv6.getText().equals(day)) {
                febTv6.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                febTv6.setTextColor(getResources().getColor(R.color.white));

            }
            if (febTv7.getText().equals(day)) {
                febTv7.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                febTv7.setTextColor(getResources().getColor(R.color.white));

            }
            if (febTv8.getText().equals(day)) {
                febTv8.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                febTv8.setTextColor(getResources().getColor(R.color.white));

            }
            if (febTv9.getText().equals(day)) {
                febTv9.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                febTv9.setTextColor(getResources().getColor(R.color.white));

            }
            if (febTv10.getText().equals(day)) {
                febTv10.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                febTv10.setTextColor(getResources().getColor(R.color.white));

            }
            if (febTv11.getText().equals(day)) {
                febTv11.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                febTv11.setTextColor(getResources().getColor(R.color.white));

            }
            if (febTv12.getText().equals(day)) {
                febTv12.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                febTv12.setTextColor(getResources().getColor(R.color.white));

            }
            if (febTv13.getText().equals(day)) {
                febTv13.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                febTv13.setTextColor(getResources().getColor(R.color.white));

            }
            if (febTv14.getText().equals(day)) {
                febTv14.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                febTv14.setTextColor(getResources().getColor(R.color.white));

            }
            if (febTv15.getText().equals(day)) {
                febTv15.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                febTv15.setTextColor(getResources().getColor(R.color.white));

            }
            if (febTv16.getText().equals(day)) {
                febTv16.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                febTv16.setTextColor(getResources().getColor(R.color.white));

            }
            if (febTv17.getText().equals(day)) {
                febTv17.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                febTv17.setTextColor(getResources().getColor(R.color.white));

            }
            if (febTv18.getText().equals(day)) {
                febTv18.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                febTv18.setTextColor(getResources().getColor(R.color.white));

            }
            if (febTv19.getText().equals(day)) {
                febTv19.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                febTv19.setTextColor(getResources().getColor(R.color.white));

            }
            if (febTv20.getText().equals(day)) {
                febTv20.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                febTv20.setTextColor(getResources().getColor(R.color.white));

            }
            if (febTv21.getText().equals(day)) {
                febTv21.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                febTv21.setTextColor(getResources().getColor(R.color.white));

            }
            if (febTv22.getText().equals(day)) {
                febTv22.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                febTv22.setTextColor(getResources().getColor(R.color.white));

            }
            if (febTv23.getText().equals(day)) {
                febTv23.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                febTv23.setTextColor(getResources().getColor(R.color.white));

            }
            if (febTv24.getText().equals(day)) {
                febTv24.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                febTv24.setTextColor(getResources().getColor(R.color.white));

            }
            if (febTv25.getText().equals(day)) {
                febTv25.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                febTv25.setTextColor(getResources().getColor(R.color.white));

            }
            if (febTv26.getText().equals(day)) {
                febTv26.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                febTv26.setTextColor(getResources().getColor(R.color.white));

            }
            if (febTv27.getText().equals(day)) {
                febTv27.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                febTv27.setTextColor(getResources().getColor(R.color.white));

            }
            if (febTv28.getText().equals(day)) {
                febTv28.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                febTv28.setTextColor(getResources().getColor(R.color.white));
            }
        //}

    }

    private void setJanDatesRound(String day) {
        //if (isAdded()) {
            if (janTv1.getText().equals(day)) {
                janTv1.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                janTv1.setTextColor(getResources().getColor(R.color.white));

            }
            if (janTv2.getText().equals(day)) {
                janTv2.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                janTv2.setTextColor(getResources().getColor(R.color.white));

            }
            if (janTv3.getText().equals(day)) {
                janTv3.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                janTv3.setTextColor(getResources().getColor(R.color.white));

            }
            if (janTv4.getText().equals(day)) {
                janTv4.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                janTv4.setTextColor(getResources().getColor(R.color.white));

            }
            if (janTv5.getText().equals(day)) {
                janTv5.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                janTv5.setTextColor(getResources().getColor(R.color.white));

            }
            if (janTv6.getText().equals(day)) {
                janTv6.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                janTv6.setTextColor(getResources().getColor(R.color.white));

            }
            if (janTv7.getText().equals(day)) {
                janTv7.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                janTv7.setTextColor(getResources().getColor(R.color.white));

            }
            if (janTv8.getText().equals(day)) {
                janTv8.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                janTv8.setTextColor(getResources().getColor(R.color.white));

            }
            if (janTv9.getText().equals(day)) {
                janTv9.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                janTv9.setTextColor(getResources().getColor(R.color.white));

            }
            if (janTv10.getText().equals(day)) {
                janTv10.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                janTv10.setTextColor(getResources().getColor(R.color.white));

            }
            if (janTv11.getText().equals(day)) {
                janTv11.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                janTv11.setTextColor(getResources().getColor(R.color.white));

            }
            if (janTv12.getText().equals(day)) {
                janTv12.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                janTv12.setTextColor(getResources().getColor(R.color.white));

            }
            if (janTv13.getText().equals(day)) {
                janTv13.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                janTv13.setTextColor(getResources().getColor(R.color.white));

            }
            if (janTv14.getText().equals(day)) {
                janTv14.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                janTv14.setTextColor(getResources().getColor(R.color.white));

            }
            if (janTv15.getText().equals(day)) {
                janTv15.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                janTv15.setTextColor(getResources().getColor(R.color.white));

            }
            if (janTv16.getText().equals(day)) {
                janTv16.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                janTv16.setTextColor(getResources().getColor(R.color.white));

            }
            if (janTv17.getText().equals(day)) {
                janTv17.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                janTv17.setTextColor(getResources().getColor(R.color.white));

            }
            if (janTv18.getText().equals(day)) {
                janTv18.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                janTv18.setTextColor(getResources().getColor(R.color.white));

            }
            if (janTv19.getText().equals(day)) {
                janTv19.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                janTv19.setTextColor(getResources().getColor(R.color.white));

            }
            if (janTv20.getText().equals(day)) {
                janTv20.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                janTv20.setTextColor(getResources().getColor(R.color.white));

            }
            if (janTv21.getText().equals(day)) {
                janTv21.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                janTv21.setTextColor(getResources().getColor(R.color.white));

            }
            if (janTv22.getText().equals(day)) {
                janTv22.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                janTv22.setTextColor(getResources().getColor(R.color.white));

            }
            if (janTv23.getText().equals(day)) {
                janTv23.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                janTv23.setTextColor(getResources().getColor(R.color.white));

            }
            if (janTv24.getText().equals(day)) {
                janTv24.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                janTv24.setTextColor(getResources().getColor(R.color.white));

            }
            if (janTv25.getText().equals(day)) {
                janTv25.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                janTv25.setTextColor(getResources().getColor(R.color.white));

            }
            if (janTv26.getText().equals(day)) {
                janTv26.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                janTv26.setTextColor(getResources().getColor(R.color.white));

            }
            if (janTv27.getText().equals(day)) {
                janTv27.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                janTv27.setTextColor(getResources().getColor(R.color.white));

            }
            if (janTv28.getText().equals(day)) {
                janTv28.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                janTv28.setTextColor(getResources().getColor(R.color.white));
            }
            if (janTv29.getText().equals(day)) {
                janTv29.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                janTv29.setTextColor(getResources().getColor(R.color.white));

            }
            if (janTv30.getText().equals(day)) {
                janTv30.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                janTv30.setTextColor(getResources().getColor(R.color.white));

            }
            if (janTv31.getText().equals(day)) {
                janTv31.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(), R.drawable.circle_yellow, null));
                janTv31.setTextColor(getResources().getColor(R.color.white));

            }
        //}
    }


    @SuppressLint("NewApi")
    public void JanuaryDatesClick(View view1) {
        if (isAdded()) {
            TextView t = (TextView) view1;
            availabilty =  "Jan " + t.getText();
            if (t.getBackground() == null) {
                // String key = t.getText().toString();
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("date", availabilty);
                db.child(currrentUser.getUid()).child("availability").child(availabilty).updateChildren(hashMap);

                t.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(),R.drawable.circle_yellow,null));
                t.setTextColor(getResources().getColor(R.color.white));


            } else {
                //String key = t.getText().toString();
                db.child(currrentUser.getUid()).child("availability").child(availabilty).removeValue();
                t.setBackgroundResource(0);
                t.setTextColor(getResources().getColor(R.color.black));

            }
        }
    }


    @SuppressLint("NewApi")
    public void FebruaryDatesClick(View view1) {
        if (isAdded()) {
            TextView t = (TextView) view1;
            availabilty =  "Feb " + t.getText();
            if (t.getBackground() == null) {
                // String key = t.getText().toString();
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("date", availabilty);
                db.child(currrentUser.getUid()).child("availability").child(availabilty).updateChildren(hashMap);

                t.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(),R.drawable.circle_yellow,null));
                t.setTextColor(getResources().getColor(R.color.white));
                /*if (t.getBackground() != null) {
                    t.setBackgroundResource(0);
                    t.setTextColor(getResources().getColor(R.color.black));
                } else {
                    t.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(),R.drawable.circle_yellow,null));
                    t.setTextColor(getResources().getColor(R.color.white));
                }*/

            } else {
                //String key = t.getText().toString();
                db.child(currrentUser.getUid()).child("availability").child(availabilty).removeValue();
                t.setBackgroundResource(0);
                t.setTextColor(getResources().getColor(R.color.black));

            }
        }
    }


    @SuppressLint("NewApi")
    public void MarchDatesClick(View view1) {
        if (isAdded()) {
            TextView t = (TextView) view1;
            availabilty =  "Mar " + t.getText();
            if (t.getBackground() == null) {
                // String key = t.getText().toString();
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("date", availabilty);
                db.child(currrentUser.getUid()).child("availability").child(availabilty).updateChildren(hashMap);

                t.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(),R.drawable.circle_yellow,null));
                t.setTextColor(getResources().getColor(R.color.white));
                /*if (t.getBackground() != null) {
                    t.setBackgroundResource(0);
                    t.setTextColor(getResources().getColor(R.color.black));
                } else {
                    t.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(),R.drawable.circle_yellow,null));
                    t.setTextColor(getResources().getColor(R.color.white));
                }*/

            } else {
                //String key = t.getText().toString();
                db.child(currrentUser.getUid()).child("availability").child(availabilty).removeValue();
                t.setBackgroundResource(0);
                t.setTextColor(getResources().getColor(R.color.black));

            }
        }
    }

    @SuppressLint("NewApi")
    public void AprilDatesClick(View view1) {
        if (isAdded()) {
            TextView t = (TextView) view1;
            availabilty =  "Apr " + t.getText();
            if (t.getBackground() == null) {
                // String key = t.getText().toString();
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("date", availabilty);
                db.child(currrentUser.getUid()).child("availability").child(availabilty).updateChildren(hashMap);

                t.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(),R.drawable.circle_yellow,null));
                t.setTextColor(getResources().getColor(R.color.white));
                /*if (t.getBackground() != null) {
                    t.setBackgroundResource(0);
                    t.setTextColor(getResources().getColor(R.color.black));
                } else {
                    t.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(),R.drawable.circle_yellow,null));
                    t.setTextColor(getResources().getColor(R.color.white));
                }*/

            } else {
                //String key = t.getText().toString();
                db.child(currrentUser.getUid()).child("availability").child(availabilty).removeValue();
                t.setBackgroundResource(0);
                t.setTextColor(getResources().getColor(R.color.black));

            }
        }
    }
    @SuppressLint("NewApi")
    public void MayDatesClick(View view1) {
        if (isAdded()) {
            TextView t = (TextView) view1;
            availabilty =  "May " + t.getText();
            if (t.getBackground() == null) {
                // String key = t.getText().toString();
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("date", availabilty);
                db.child(currrentUser.getUid()).child("availability").child(availabilty).updateChildren(hashMap);

                t.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(),R.drawable.circle_yellow,null));
                t.setTextColor(getResources().getColor(R.color.white));
                /*if (t.getBackground() != null) {
                    t.setBackgroundResource(0);
                    t.setTextColor(getResources().getColor(R.color.black));
                } else {
                    t.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(),R.drawable.circle_yellow,null));
                    t.setTextColor(getResources().getColor(R.color.white));
                }*/

            } else {
                //String key = t.getText().toString();
                db.child(currrentUser.getUid()).child("availability").child(availabilty).removeValue();
                t.setBackgroundResource(0);
                t.setTextColor(getResources().getColor(R.color.black));

            }
        }
    }
    @SuppressLint("NewApi")
    public void JuneDatesClick(View view1) {
        if (isAdded()) {
            TextView t = (TextView) view1;
            availabilty =  "Jun " + t.getText();
            if (t.getBackground() == null) {
                // String key = t.getText().toString();
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("date", availabilty);
                db.child(currrentUser.getUid()).child("availability").child(availabilty).updateChildren(hashMap);

                t.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(),R.drawable.circle_yellow,null));
                t.setTextColor(getResources().getColor(R.color.white));
                /*if (t.getBackground() != null) {
                    t.setBackgroundResource(0);
                    t.setTextColor(getResources().getColor(R.color.black));
                } else {
                    t.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(),R.drawable.circle_yellow,null));
                    t.setTextColor(getResources().getColor(R.color.white));
                }*/

            } else {
                //String key = t.getText().toString();
                db.child(currrentUser.getUid()).child("availability").child(availabilty).removeValue();
                t.setBackgroundResource(0);
                t.setTextColor(getResources().getColor(R.color.black));

            }
        }
    }
    @SuppressLint("NewApi")
    public void JulyDatesClick(View view1) {
        if (isAdded()) {
            TextView t = (TextView) view1;
            availabilty =  "Jul " + t.getText();
            if (t.getBackground() == null) {
                // String key = t.getText().toString();
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("date", availabilty);
                db.child(currrentUser.getUid()).child("availability").child(availabilty).updateChildren(hashMap);

                t.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(),R.drawable.circle_yellow,null));
                t.setTextColor(getResources().getColor(R.color.white));
                /*if (t.getBackground() != null) {
                    t.setBackgroundResource(0);
                    t.setTextColor(getResources().getColor(R.color.black));
                } else {
                    t.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(),R.drawable.circle_yellow,null));
                    t.setTextColor(getResources().getColor(R.color.white));
                }*/

            } else {
                //String key = t.getText().toString();
                db.child(currrentUser.getUid()).child("availability").child(availabilty).removeValue();
                t.setBackgroundResource(0);
                t.setTextColor(getResources().getColor(R.color.black));

            }
        }
    }
    @SuppressLint("NewApi")
    public void AugustDatesClick(View view1) {
        if (isAdded()) {
            TextView t = (TextView) view1;
            availabilty =  "Aug " + t.getText();
            if (t.getBackground() == null) {
                // String key = t.getText().toString();
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("date", availabilty);
                db.child(currrentUser.getUid()).child("availability").child(availabilty).updateChildren(hashMap);

                t.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(),R.drawable.circle_yellow,null));
                t.setTextColor(getResources().getColor(R.color.white));
                /*if (t.getBackground() != null) {
                    t.setBackgroundResource(0);
                    t.setTextColor(getResources().getColor(R.color.black));
                } else {
                    t.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(),R.drawable.circle_yellow,null));
                    t.setTextColor(getResources().getColor(R.color.white));
                }*/

            } else {
                //String key = t.getText().toString();
                db.child(currrentUser.getUid()).child("availability").child(availabilty).removeValue();
                t.setBackgroundResource(0);
                t.setTextColor(getResources().getColor(R.color.black));

            }
        }
    }
    @SuppressLint("NewApi")
    public void SeptemberDatesClick(View view1) {
        if (isAdded()) {
            TextView t = (TextView) view1;
            availabilty =  "Sep " + t.getText();
            if (t.getBackground() == null) {
                // String key = t.getText().toString();
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("date", availabilty);
                db.child(currrentUser.getUid()).child("availability").child(availabilty).updateChildren(hashMap);

                t.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(),R.drawable.circle_yellow,null));
                t.setTextColor(getResources().getColor(R.color.white));
                /*if (t.getBackground() != null) {
                    t.setBackgroundResource(0);
                    t.setTextColor(getResources().getColor(R.color.black));
                } else {
                    t.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(),R.drawable.circle_yellow,null));
                    t.setTextColor(getResources().getColor(R.color.white));
                }*/

            } else {
                //String key = t.getText().toString();
                db.child(currrentUser.getUid()).child("availability").child(availabilty).removeValue();
                t.setBackgroundResource(0);
                t.setTextColor(getResources().getColor(R.color.black));

            }
        }
    }

    @SuppressLint("NewApi")
    public void OctoberDatesClick(View view1) {
        if (isAdded()) {
            TextView t = (TextView) view1;
            availabilty =  "Oct " + t.getText();
            if (t.getBackground() == null) {
               // String key = t.getText().toString();
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("date", availabilty);
                db.child(currrentUser.getUid()).child("availability").child(availabilty).updateChildren(hashMap);

                t.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(),R.drawable.circle_yellow,null));
                t.setTextColor(getResources().getColor(R.color.white));
                /*if (t.getBackground() != null) {
                    t.setBackgroundResource(0);
                    t.setTextColor(getResources().getColor(R.color.black));
                } else {
                    t.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(),R.drawable.circle_yellow,null));
                    t.setTextColor(getResources().getColor(R.color.white));
                }*/
                
            } else {
                //String key = t.getText().toString();
                db.child(currrentUser.getUid()).child("availability").child(availabilty).removeValue();
                t.setBackgroundResource(0);
                t.setTextColor(getResources().getColor(R.color.black));
                
            }
        }
    }

    public void NovemberDatesClick(View view1) {
        if (isAdded()) {
            TextView t = (TextView) view1;
            availabilty =  "Nov " + t.getText();
            if (t.getBackground() == null) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("date", availabilty);
                db.child(currrentUser.getUid()).child("availability").child(availabilty).updateChildren(hashMap);
                /*if (t.getBackground() != null) {
                    t.setBackgroundResource(0);
                    t.setTextColor(getResources().getColor(R.color.black));
                } else {
                    t.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(),R.drawable.circle_yellow,null));
                    t.setTextColor(getResources().getColor(R.color.white));
                    //    }
                }*/

                t.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(),R.drawable.circle_yellow,null));
                t.setTextColor(getResources().getColor(R.color.white));
                
            }else {
                db.child(currrentUser.getUid()).child("availability").child(availabilty).removeValue();
                t.setBackgroundResource(0);
                t.setTextColor(getResources().getColor(R.color.black));
                
            }
        }
    }
    public void DecemberDatesClick(View view1) {
        if (isAdded()) {
            TextView t = (TextView) view1;
            availabilty =  "Dec " + t.getText();

            if (t.getBackground() == null) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("date", availabilty);
                db.child(currrentUser.getUid()).child("availability").child(availabilty).updateChildren(hashMap);
                /*if (t.getBackground() != null) {
                    t.setBackgroundResource(0);
                    t.setTextColor(getResources().getColor(R.color.black));
                } else {
                    t.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(),R.drawable.circle_yellow,null));
                    t.setTextColor(getResources().getColor(R.color.white));
                    //    }
                }*/

                t.setBackground(ResourcesCompat.getDrawable(requireContext().getResources(),R.drawable.circle_yellow,null));
                t.setTextColor(getResources().getColor(R.color.white));
                
            }else {
                db.child(currrentUser.getUid()).child("availability").child(availabilty).removeValue();
                t.setBackgroundResource(0);
                t.setTextColor(getResources().getColor(R.color.black));
                
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

    private void initJanTextViews(View view) {
        janTv1 = view.findViewById(R.id.jan_tv_1);
        janTv2 = view.findViewById(R.id.jan_tv_2);
        janTv3 = view.findViewById(R.id.jan_tv_3);
        janTv4 = view.findViewById(R.id.jan_tv_4);
        janTv5 = view.findViewById(R.id.jan_tv_5);
        janTv6 = view.findViewById(R.id.jan_tv_6);
        janTv7 = view.findViewById(R.id.jan_tv_7);
        janTv8 = view.findViewById(R.id.jan_tv_8);
        janTv9 = view.findViewById(R.id.jan_tv_9);
        janTv10 = view.findViewById(R.id.jan_tv_10);
        janTv11 = view.findViewById(R.id.jan_tv_11);
        janTv12 = view.findViewById(R.id.jan_tv_12);
        janTv13 = view.findViewById(R.id.jan_tv_13);
        janTv14 = view.findViewById(R.id.jan_tv_14);
        janTv15 = view.findViewById(R.id.jan_tv_15);
        janTv16 = view.findViewById(R.id.jan_tv_16);
        janTv17 = view.findViewById(R.id.jan_tv_17);
        janTv18 = view.findViewById(R.id.jan_tv_18);
        janTv19 = view.findViewById(R.id.jan_tv_19);
        janTv20 = view.findViewById(R.id.jan_tv_20);
        janTv21 = view.findViewById(R.id.jan_tv_21);
        janTv22 = view.findViewById(R.id.jan_tv_22);
        janTv23 = view.findViewById(R.id.jan_tv_23);
        janTv24 = view.findViewById(R.id.jan_tv_24);
        janTv25 = view.findViewById(R.id.jan_tv_25);
        janTv26 = view.findViewById(R.id.jan_tv_26);
        janTv27 = view.findViewById(R.id.jan_tv_27);
        janTv28 = view.findViewById(R.id.jan_tv_28);
        janTv29 = view.findViewById(R.id.jan_tv_29);
        janTv30 = view.findViewById(R.id.jan_tv_30);
        janTv31 = view.findViewById(R.id.jan_tv_31);
    }

    private void initFebTextViews(View view) {
        febTv1 = view.findViewById(R.id.feb_tv_1);
        febTv2 = view.findViewById(R.id.feb_tv_2);
        febTv3 = view.findViewById(R.id.feb_tv_3);
        febTv4 = view.findViewById(R.id.feb_tv_4);
        febTv5 = view.findViewById(R.id.feb_tv_5);
        febTv6 = view.findViewById(R.id.feb_tv_6);
        febTv7 = view.findViewById(R.id.feb_tv_7);
        febTv8 = view.findViewById(R.id.feb_tv_8);
        febTv9 = view.findViewById(R.id.feb_tv_9);
        febTv10 = view.findViewById(R.id.feb_tv_10);
        febTv11 = view.findViewById(R.id.feb_tv_11);
        febTv12 = view.findViewById(R.id.feb_tv_12);
        febTv13 = view.findViewById(R.id.feb_tv_13);
        febTv14 = view.findViewById(R.id.feb_tv_14);
        febTv15 = view.findViewById(R.id.feb_tv_15);
        febTv16 = view.findViewById(R.id.feb_tv_16);
        febTv17 = view.findViewById(R.id.feb_tv_17);
        febTv18 = view.findViewById(R.id.feb_tv_18);
        febTv19 = view.findViewById(R.id.feb_tv_19);
        febTv20 = view.findViewById(R.id.feb_tv_20);
        febTv21 = view.findViewById(R.id.feb_tv_21);
        febTv22 = view.findViewById(R.id.feb_tv_22);
        febTv23 = view.findViewById(R.id.feb_tv_23);
        febTv24 = view.findViewById(R.id.feb_tv_24);
        febTv25 = view.findViewById(R.id.feb_tv_25);
        febTv26 = view.findViewById(R.id.feb_tv_26);
        febTv27 = view.findViewById(R.id.feb_tv_27);
        febTv28 = view.findViewById(R.id.feb_tv_28);
    }

    private void initMarTextViews(View view) {
        marTv1 = view.findViewById(R.id.mar_tv_1);
        marTv2 = view.findViewById(R.id.mar_tv_2);
        marTv3 = view.findViewById(R.id.mar_tv_3);
        marTv4 = view.findViewById(R.id.mar_tv_4);
        marTv5 = view.findViewById(R.id.mar_tv_5);
        marTv6 = view.findViewById(R.id.mar_tv_6);
        marTv7 = view.findViewById(R.id.mar_tv_7);
        marTv8 = view.findViewById(R.id.mar_tv_8);
        marTv9 = view.findViewById(R.id.mar_tv_9);
        marTv10 = view.findViewById(R.id.mar_tv_10);
        marTv11 = view.findViewById(R.id.mar_tv_11);
        marTv12 = view.findViewById(R.id.mar_tv_12);
        marTv13 = view.findViewById(R.id.mar_tv_13);
        marTv14 = view.findViewById(R.id.mar_tv_14);
        marTv15 = view.findViewById(R.id.mar_tv_15);
        marTv16 = view.findViewById(R.id.mar_tv_16);
        marTv17 = view.findViewById(R.id.mar_tv_17);
        marTv18 = view.findViewById(R.id.mar_tv_18);
        marTv19 = view.findViewById(R.id.mar_tv_19);
        marTv20 = view.findViewById(R.id.mar_tv_20);
        marTv21 = view.findViewById(R.id.mar_tv_21);
        marTv22 = view.findViewById(R.id.mar_tv_22);
        marTv23 = view.findViewById(R.id.mar_tv_23);
        marTv24 = view.findViewById(R.id.mar_tv_24);
        marTv25 = view.findViewById(R.id.mar_tv_25);
        marTv26 = view.findViewById(R.id.mar_tv_26);
        marTv27 = view.findViewById(R.id.mar_tv_27);
        marTv28 = view.findViewById(R.id.mar_tv_28);
        marTv29 = view.findViewById(R.id.mar_tv_29);
        marTv30 = view.findViewById(R.id.mar_tv_30);
        marTv31 = view.findViewById(R.id.mar_tv_31);
    }

    private void initAprTextViews(View view) {
        aprTv1 = view.findViewById(R.id.apr_tv_1);
        aprTv2 = view.findViewById(R.id.apr_tv_2);
        aprTv3= view.findViewById(R.id.apr_tv_3);
        aprTv4 = view.findViewById(R.id.apr_tv_4);
        aprTv5 = view.findViewById(R.id.apr_tv_5);
        aprTv6 = view.findViewById(R.id.apr_tv_6);
        aprTv7 = view.findViewById(R.id.apr_tv_7);
        aprTv8 = view.findViewById(R.id.apr_tv_8);
        aprTv9 = view.findViewById(R.id.apr_tv_9);
        aprTv10 = view.findViewById(R.id.apr_tv_10);
        aprTv11 = view.findViewById(R.id.apr_tv_11);
        aprTv12 = view.findViewById(R.id.apr_tv_12);
        aprTv13 = view.findViewById(R.id.apr_tv_13);
        aprTv14 = view.findViewById(R.id.apr_tv_14);
        aprTv15 = view.findViewById(R.id.apr_tv_15);
        aprTv16 = view.findViewById(R.id.apr_tv_16);
        aprTv17 = view.findViewById(R.id.apr_tv_17);
        aprTv18 = view.findViewById(R.id.apr_tv_18);
        aprTv19 = view.findViewById(R.id.apr_tv_19);
        aprTv20 = view.findViewById(R.id.apr_tv_20);
        aprTv21 = view.findViewById(R.id.apr_tv_21);
        aprTv22 = view.findViewById(R.id.apr_tv_22);
        aprTv23 = view.findViewById(R.id.apr_tv_23);
        aprTv24 = view.findViewById(R.id.apr_tv_24);
        aprTv25 = view.findViewById(R.id.apr_tv_25);
        aprTv26 = view.findViewById(R.id.apr_tv_26);
        aprTv27 = view.findViewById(R.id.apr_tv_27);
        aprTv28 = view.findViewById(R.id.apr_tv_28);
        aprTv29 = view.findViewById(R.id.apr_tv_29);
        aprTv30 = view.findViewById(R.id.apr_tv_30);
    }

    private void initMayTextViews(View view) {
        mayTv1 = view.findViewById(R.id.may_tv_1);
        mayTv2 = view.findViewById(R.id.may_tv_2);
        mayTv3 = view.findViewById(R.id.may_tv_3);
        mayTv4 = view.findViewById(R.id.may_tv_4);
        mayTv5 = view.findViewById(R.id.may_tv_5);
        mayTv6 = view.findViewById(R.id.may_tv_6);
        mayTv7 = view.findViewById(R.id.may_tv_7);
        mayTv8 = view.findViewById(R.id.may_tv_8);
        mayTv9 = view.findViewById(R.id.may_tv_9);
        mayTv10 = view.findViewById(R.id.may_tv_10);
        mayTv11 = view.findViewById(R.id.may_tv_11);
        mayTv12 = view.findViewById(R.id.may_tv_12);
        mayTv13 = view.findViewById(R.id.may_tv_13);
        mayTv14 = view.findViewById(R.id.may_tv_14);
        mayTv15 = view.findViewById(R.id.may_tv_15);
        mayTv16 = view.findViewById(R.id.may_tv_16);
        mayTv17 = view.findViewById(R.id.may_tv_17);
        mayTv18 = view.findViewById(R.id.may_tv_18);
        mayTv19 = view.findViewById(R.id.may_tv_19);
        mayTv20 = view.findViewById(R.id.may_tv_20);
        mayTv21 = view.findViewById(R.id.may_tv_21);
        mayTv22 = view.findViewById(R.id.may_tv_22);
        mayTv23 = view.findViewById(R.id.may_tv_23);
        mayTv24 = view.findViewById(R.id.may_tv_24);
        mayTv25 = view.findViewById(R.id.may_tv_25);
        mayTv26 = view.findViewById(R.id.may_tv_26);
        mayTv27 = view.findViewById(R.id.may_tv_27);
        mayTv28 = view.findViewById(R.id.may_tv_28);
        mayTv29 = view.findViewById(R.id.may_tv_29);
        mayTv30 = view.findViewById(R.id.may_tv_30);
        mayTv31 = view.findViewById(R.id.may_tv_31);
    }

    private void initJunTextViews(View view) {
        junTv1 = view.findViewById(R.id.jun_tv_1);
        junTv2 = view.findViewById(R.id.jun_tv_2);
        junTv3 = view.findViewById(R.id.jun_tv_3);
        junTv4 = view.findViewById(R.id.jun_tv_4);
        junTv5 = view.findViewById(R.id.jun_tv_5);
        junTv6 = view.findViewById(R.id.jun_tv_6);
        junTv7= view.findViewById(R.id.jun_tv_7);
        junTv8 = view.findViewById(R.id.jun_tv_8);
        junTv9 = view.findViewById(R.id.jun_tv_9);
        junTv10 = view.findViewById(R.id.jun_tv_10);
        junTv11 = view.findViewById(R.id.jun_tv_11);
        junTv12 = view.findViewById(R.id.jun_tv_12);
        junTv13 = view.findViewById(R.id.jun_tv_13);
        junTv14 = view.findViewById(R.id.jun_tv_14);
        junTv15 = view.findViewById(R.id.jun_tv_15);
        junTv16 = view.findViewById(R.id.jun_tv_16);
        junTv17 = view.findViewById(R.id.jun_tv_17);
        junTv18 = view.findViewById(R.id.jun_tv_18);
        junTv19 = view.findViewById(R.id.jun_tv_19);
        junTv20 = view.findViewById(R.id.jun_tv_20);
        junTv21 = view.findViewById(R.id.jun_tv_21);
        junTv22 = view.findViewById(R.id.jun_tv_22);
        junTv23 = view.findViewById(R.id.jun_tv_23);
        junTv24 = view.findViewById(R.id.jun_tv_24);
        junTv25 = view.findViewById(R.id.jun_tv_25);
        junTv26 = view.findViewById(R.id.jun_tv_26);
        junTv27 = view.findViewById(R.id.jun_tv_27);
        junTv28 = view.findViewById(R.id.jun_tv_28);
        junTv29 = view.findViewById(R.id.jun_tv_29);
        junTv30 = view.findViewById(R.id.jun_tv_30);
    }

    private void initJulTextViews(View view) {
        julTv1 = view.findViewById(R.id.jul_tv_1);
        julTv2 = view.findViewById(R.id.jul_tv_2);
        julTv3 = view.findViewById(R.id.jul_tv_3);
        julTv4 = view.findViewById(R.id.jul_tv_4);
        julTv5 = view.findViewById(R.id.jul_tv_5);
        julTv6 = view.findViewById(R.id.jul_tv_6);
        julTv7 = view.findViewById(R.id.jul_tv_7);
        julTv8 = view.findViewById(R.id.jul_tv_8);
        julTv9 = view.findViewById(R.id.jul_tv_9);
        julTv10 = view.findViewById(R.id.jul_tv_10);
        julTv11 = view.findViewById(R.id.jul_tv_11);
        julTv12 = view.findViewById(R.id.jul_tv_12);
        julTv13 = view.findViewById(R.id.jul_tv_13);
        julTv14 = view.findViewById(R.id.jul_tv_14);
        julTv15 = view.findViewById(R.id.jul_tv_15);
        julTv16 = view.findViewById(R.id.jul_tv_16);
        julTv17 = view.findViewById(R.id.jul_tv_17);
        julTv18 = view.findViewById(R.id.jul_tv_18);
        julTv19 = view.findViewById(R.id.jul_tv_19);
        julTv20 = view.findViewById(R.id.jul_tv_20);
        julTv21 = view.findViewById(R.id.jul_tv_21);
        julTv22 = view.findViewById(R.id.jul_tv_22);
        julTv23 = view.findViewById(R.id.jul_tv_23);
        julTv24 = view.findViewById(R.id.jul_tv_24);
        julTv25 = view.findViewById(R.id.jul_tv_25);
        julTv26 = view.findViewById(R.id.jul_tv_26);
        julTv27 = view.findViewById(R.id.jul_tv_27);
        julTv28 = view.findViewById(R.id.jul_tv_28);
        julTv29 = view.findViewById(R.id.jul_tv_29);
        julTv30 = view.findViewById(R.id.jul_tv_30);
        julTv31 = view.findViewById(R.id.jul_tv_31);
    }

    private void initAugTextViews(View view) {
        augTv1 = view.findViewById(R.id.aug_tv_1);
        augTv2 = view.findViewById(R.id.aug_tv_2);
        augTv3 = view.findViewById(R.id.aug_tv_3);
        augTv4 = view.findViewById(R.id.aug_tv_4);
        augTv5 = view.findViewById(R.id.aug_tv_5);
        augTv6 = view.findViewById(R.id.aug_tv_6);
        augTv7 = view.findViewById(R.id.aug_tv_7);
        augTv8 = view.findViewById(R.id.aug_tv_8);
        augTv9 = view.findViewById(R.id.aug_tv_9);
        augTv10 = view.findViewById(R.id.aug_tv_10);
        augTv11 = view.findViewById(R.id.aug_tv_11);
        augTv12 = view.findViewById(R.id.aug_tv_12);
        augTv13 = view.findViewById(R.id.aug_tv_13);
        augTv14 = view.findViewById(R.id.aug_tv_14);
        augTv15 = view.findViewById(R.id.aug_tv_15);
        augTv16 = view.findViewById(R.id.aug_tv_16);
        augTv17 = view.findViewById(R.id.aug_tv_17);
        augTv18 = view.findViewById(R.id.aug_tv_18);
        augTv19 = view.findViewById(R.id.aug_tv_19);
        augTv20 = view.findViewById(R.id.aug_tv_20);
        augTv21 = view.findViewById(R.id.aug_tv_21);
        augTv22 = view.findViewById(R.id.aug_tv_22);
        augTv23 = view.findViewById(R.id.aug_tv_23);
        augTv24 = view.findViewById(R.id.aug_tv_24);
        augTv25 = view.findViewById(R.id.aug_tv_25);
        augTv26 = view.findViewById(R.id.aug_tv_26);
        augTv27 = view.findViewById(R.id.aug_tv_27);
        augTv28 = view.findViewById(R.id.aug_tv_28);
        augTv29 = view.findViewById(R.id.aug_tv_29);
        augTv30 = view.findViewById(R.id.aug_tv_30);
        augTv31 = view.findViewById(R.id.aug_tv_31);
    }

    private void initSepTextViews(View view) {
        sepTv1 = view.findViewById(R.id.sept_tv_1);
        sepTv2 = view.findViewById(R.id.sept_tv_2);
        sepTv3 = view.findViewById(R.id.sept_tv_3);
        sepTv4 = view.findViewById(R.id.sept_tv_4);
        sepTv5 = view.findViewById(R.id.sept_tv_5);
        sepTv6 = view.findViewById(R.id.sept_tv_6);
        sepTv7 = view.findViewById(R.id.sept_tv_7);
        sepTv8 = view.findViewById(R.id.sept_tv_8);
        sepTv9 = view.findViewById(R.id.sept_tv_9);
        sepTv10 = view.findViewById(R.id.sept_tv_10);
        sepTv11 = view.findViewById(R.id.sept_tv_11);
        sepTv12 = view.findViewById(R.id.sept_tv_12);
        sepTv13 = view.findViewById(R.id.sept_tv_13);
        sepTv14 = view.findViewById(R.id.sept_tv_14);
        sepTv15 = view.findViewById(R.id.sept_tv_15);
        sepTv16 = view.findViewById(R.id.sept_tv_16);
        sepTv17 = view.findViewById(R.id.sept_tv_17);
        sepTv18 = view.findViewById(R.id.sept_tv_18);
        sepTv19 = view.findViewById(R.id.sept_tv_19);
        sepTv20 = view.findViewById(R.id.sept_tv_20);
        sepTv21 = view.findViewById(R.id.sept_tv_21);
        sepTv22 = view.findViewById(R.id.sept_tv_22);
        sepTv23 = view.findViewById(R.id.sept_tv_23);
        sepTv24 = view.findViewById(R.id.sept_tv_24);
        sepTv25 = view.findViewById(R.id.sept_tv_25);
        sepTv26 = view.findViewById(R.id.sept_tv_26);
        sepTv27 = view.findViewById(R.id.sept_tv_27);
        sepTv28 = view.findViewById(R.id.sept_tv_28);
        sepTv29 = view.findViewById(R.id.sept_tv_29);
        sepTv30 = view.findViewById(R.id.sept_tv_30);
    }

    private void initOctTextViews(View view) {
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

    private void initNovTextViews(View view) {
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
}