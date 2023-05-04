package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CaddieAvailabiltyActivity extends AppCompatActivity {

    TextView almostFinished;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caddie_availabilty);
        almostFinished = findViewById(R.id.almostFinished);
        //picker = findViewById(R.id.datePicker);
        changeStatusBarColor(this,R.color.yellow);
        mAuth = FirebaseAuth.getInstance();
        currrentUser = mAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference().child("Caddie");

        checkAvailability();

        initJanTextViews();
        initFebTextViews();
        initMarTextViews();
        initAprTextViews();
        initMayTextViews();
        initJunTextViews();
        initJulTextViews();
        initAugTextViews();
        initSepTextViews();
        initOctTextViews();
        initNovTextViews();
        initDecTextViews();
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


    private void checkAvailability() {
        db.child(currrentUser.getUid()).child("availability").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String date = ds.child("date").getValue().toString();
                        String month = date.substring(date.length() - 3);
                        if (month.equals("Jan")) {
                            if (date.length() == 5){
                                //String day = date.substring(date.length() - 1);
                                String day = date.substring(0, 1);
                                setJanDatesRound(day);
                            }else {
                                String day = date.substring(0, 2);
                                setJanDatesRound(day);
                            }

                        }
                        else if (month.equals("Feb")) {
                            if (date.length() == 5){
                                String day = date.substring(0, 1);
                                setFebDatesRound(day);
                            }else {
                                String day = date.substring(0, 2);
                                setFebDatesRound(day);
                            }

                        }
                        else if (month.equals("Mar")) {
                            if (date.length() == 5){
                                String day = date.substring(0, 1);
                                setMarDatesRound(day);
                            }else {
                                String day = date.substring(0, 2);
                                setMarDatesRound(day);
                            }

                        }
                        else if (month.equals("Apr")) {
                            if (date.length() == 5){
                                String day = date.substring(0, 1);
                                setAprDatesRound(day);
                            }else {
                                String day = date.substring(0, 2);
                                setAprDatesRound(day);
                            }

                        }
                        else if (month.equals("May")) {
                            if (date.length() == 5){
                                String day = date.substring(0, 1);
                                setMayDatesRound(day);
                            }else {
                                String day = date.substring(0, 2);
                                setMayDatesRound(day);
                            }

                        }
                        else if (month.equals("Jun")) {
                            if (date.length() == 5){
                                String day = date.substring(0, 1);
                                setJunDatesRound(day);
                            }else {
                                String day = date.substring(0, 2);
                                setJunDatesRound(day);
                            }

                        }
                        else if (month.equals("Jul")) {
                            if (date.length() == 5){
                                String day = date.substring(0, 1);
                                setJulDatesRound(day);
                            }else {
                                String day = date.substring(0, 2);
                                setJulDatesRound(day);
                            }

                        }
                        else if (month.equals("Aug")) {
                            if (date.length() == 5){
                                String day = date.substring(0, 1);
                                setAugDatesRound(day);
                            }else {
                                String day = date.substring(0, 2);
                                setAugDatesRound(day);
                            }

                        }
                        else if (month.equals("Sep")) {
                            if (date.length() == 5){
                                String day = date.substring(0, 1);
                                setSepDatesRound(day);
                            }else {
                                String day = date.substring(0, 2);
                                setSepDatesRound(day);
                            }

                        }

                        else if (month.equals("Oct")) {
                            if (date.length() == 5){
                                String day = date.substring(0, 1);
                                setOctDatesRound(day);
                            }else {
                                String day = date.substring(0, 2);
                                setOctDatesRound(day);
                            }

                        }else if (month.equals("Nov")) {
                            if (date.length() == 5){
                                String day = date.substring(0, 1);
                                setNovDatesRound(day);
                            }else {
                                String day = date.substring(0, 2);
                                setNovDatesRound(day);
                            }
                        }else if (month.equals("Dec")) {
                            if (date.length() == 5){
                                String day = date.substring(0, 1);
                                setDecDatesRound(day);
                            }else {
                                String day = date.substring(0, 2);
                                setDecDatesRound(day);
                            }
                        }
                    }
                }else {
                    //    storeAvailabilty();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void setJanDatesRound(String day) {
        //if (isAdded()) {
        if (janTv1.getText().equals(day)) {
            janTv1.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            janTv1.setTextColor(getResources().getColor(R.color.white));

        }
        if (janTv2.getText().equals(day)) {
            janTv2.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            janTv2.setTextColor(getResources().getColor(R.color.white));

        }
        if (janTv3.getText().equals(day)) {
            janTv3.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            janTv3.setTextColor(getResources().getColor(R.color.white));

        }
        if (janTv4.getText().equals(day)) {
            janTv4.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            janTv4.setTextColor(getResources().getColor(R.color.white));

        }
        if (janTv5.getText().equals(day)) {
            janTv5.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            janTv5.setTextColor(getResources().getColor(R.color.white));

        }
        if (janTv6.getText().equals(day)) {
            janTv6.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            janTv6.setTextColor(getResources().getColor(R.color.white));

        }
        if (janTv7.getText().equals(day)) {
            janTv7.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            janTv7.setTextColor(getResources().getColor(R.color.white));

        }
        if (janTv8.getText().equals(day)) {
            janTv8.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            janTv8.setTextColor(getResources().getColor(R.color.white));

        }
        if (janTv9.getText().equals(day)) {
            janTv9.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            janTv9.setTextColor(getResources().getColor(R.color.white));

        }
        if (janTv10.getText().equals(day)) {
            janTv10.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            janTv10.setTextColor(getResources().getColor(R.color.white));

        }
        if (janTv11.getText().equals(day)) {
            janTv11.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            janTv11.setTextColor(getResources().getColor(R.color.white));

        }
        if (janTv12.getText().equals(day)) {
            janTv12.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            janTv12.setTextColor(getResources().getColor(R.color.white));

        }
        if (janTv13.getText().equals(day)) {
            janTv13.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            janTv13.setTextColor(getResources().getColor(R.color.white));

        }
        if (janTv14.getText().equals(day)) {
            janTv14.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            janTv14.setTextColor(getResources().getColor(R.color.white));

        }
        if (janTv15.getText().equals(day)) {
            janTv15.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            janTv15.setTextColor(getResources().getColor(R.color.white));

        }
        if (janTv16.getText().equals(day)) {
            janTv16.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            janTv16.setTextColor(getResources().getColor(R.color.white));

        }
        if (janTv17.getText().equals(day)) {
            janTv17.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            janTv17.setTextColor(getResources().getColor(R.color.white));

        }
        if (janTv18.getText().equals(day)) {
            janTv18.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            janTv18.setTextColor(getResources().getColor(R.color.white));

        }
        if (janTv19.getText().equals(day)) {
            janTv19.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            janTv19.setTextColor(getResources().getColor(R.color.white));

        }
        if (janTv20.getText().equals(day)) {
            janTv20.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            janTv20.setTextColor(getResources().getColor(R.color.white));

        }
        if (janTv21.getText().equals(day)) {
            janTv21.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            janTv21.setTextColor(getResources().getColor(R.color.white));

        }
        if (janTv22.getText().equals(day)) {
            janTv22.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            janTv22.setTextColor(getResources().getColor(R.color.white));

        }
        if (janTv23.getText().equals(day)) {
            janTv23.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            janTv23.setTextColor(getResources().getColor(R.color.white));

        }
        if (janTv24.getText().equals(day)) {
            janTv24.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            janTv24.setTextColor(getResources().getColor(R.color.white));

        }
        if (janTv25.getText().equals(day)) {
            janTv25.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            janTv25.setTextColor(getResources().getColor(R.color.white));

        }
        if (janTv26.getText().equals(day)) {
            janTv26.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            janTv26.setTextColor(getResources().getColor(R.color.white));

        }
        if (janTv27.getText().equals(day)) {
            janTv27.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            janTv27.setTextColor(getResources().getColor(R.color.white));

        }
        if (janTv28.getText().equals(day)) {
            janTv28.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            janTv28.setTextColor(getResources().getColor(R.color.white));
        }
        if (janTv29.getText().equals(day)) {
            janTv29.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            janTv29.setTextColor(getResources().getColor(R.color.white));

        }
        if (janTv30.getText().equals(day)) {
            janTv30.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            janTv30.setTextColor(getResources().getColor(R.color.white));

        }
        if (janTv31.getText().equals(day)) {
            janTv31.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            janTv31.setTextColor(getResources().getColor(R.color.white));

        }
        //}
    }

    private void setSepDatesRound(String day) {
        if (sepTv1.getText().equals(day)) {
            sepTv1.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            sepTv1.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv2.getText().equals(day)) {
            sepTv2.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            sepTv2.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv3.getText().equals(day)) {
            sepTv3.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            sepTv3.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv4.getText().equals(day)) {
            sepTv4.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            sepTv4.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv5.getText().equals(day)) {
            sepTv5.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            sepTv5.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv6.getText().equals(day)) {
            sepTv6.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            sepTv6.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv7.getText().equals(day)) {
            sepTv7.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            sepTv7.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv8.getText().equals(day)) {
            sepTv8.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            sepTv8.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv9.getText().equals(day)) {
            sepTv9.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            sepTv9.setTextColor(getResources().getColor(R.color.white));


        }
        if (sepTv10.getText().equals(day)) {
            sepTv10.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            sepTv10.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv11.getText().equals(day)) {
            sepTv11.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            sepTv11.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv12.getText().equals(day)) {
            sepTv12.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            sepTv12.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv13.getText().equals(day)) {
            sepTv13.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            sepTv13.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv14.getText().equals(day)) {
            sepTv14.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            sepTv14.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv15.getText().equals(day)) {
            sepTv15.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            sepTv15.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv16.getText().equals(day)) {
            sepTv16.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            sepTv16.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv17.getText().equals(day)) {
            sepTv17.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            sepTv17.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv18.getText().equals(day)) {
            sepTv18.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            sepTv18.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv19.getText().equals(day)) {
            sepTv19.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            sepTv19.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv20.getText().equals(day)) {
            sepTv20.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            sepTv20.setTextColor(getResources().getColor(R.color.white));

        }

        if (sepTv21.getText().equals(day)) {
            sepTv21.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            sepTv21.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv22.getText().equals(day)) {
            sepTv22.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            sepTv22.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv23.getText().equals(day)) {
            sepTv23.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            sepTv23.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv24.getText().equals(day)) {
            sepTv24.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            sepTv24.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv25.getText().equals(day)) {
            sepTv25.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            sepTv25.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv26.getText().equals(day)) {
            sepTv26.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            sepTv26.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv27.getText().equals(day)) {
            sepTv27.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            sepTv27.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv28.getText().equals(day)) {
            sepTv28.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            sepTv28.setTextColor(getResources().getColor(R.color.white));
        }
        if (sepTv29.getText().equals(day)) {
            sepTv29.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            sepTv29.setTextColor(getResources().getColor(R.color.white));

        }
        if (sepTv30.getText().equals(day)) {
            sepTv30.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            sepTv30.setTextColor(getResources().getColor(R.color.white));

        }

    }

    private void setAugDatesRound(String day) {
        if (augTv1.getText().equals(day)) {
            augTv1.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            augTv1.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv2.getText().equals(day)) {
            augTv2.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            augTv2.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv3.getText().equals(day)) {
            augTv3.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            augTv3.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv4.getText().equals(day)) {
            augTv4.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            augTv4.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv5.getText().equals(day)) {
            augTv5.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            augTv5.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv6.getText().equals(day)) {
            augTv6.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            augTv6.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv7.getText().equals(day)) {
            augTv7.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            augTv7.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv8.getText().equals(day)) {
            augTv8.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            augTv8.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv9.getText().equals(day)) {
            augTv9.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            augTv9.setTextColor(getResources().getColor(R.color.white));


        }
        if (augTv10.getText().equals(day)) {
            augTv10.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            augTv10.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv11.getText().equals(day)) {
            augTv11.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            augTv11.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv12.getText().equals(day)) {
            augTv12.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            augTv12.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv13.getText().equals(day)) {
            augTv13.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            augTv13.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv14.getText().equals(day)) {
            augTv14.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            augTv14.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv15.getText().equals(day)) {
            augTv15.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            augTv15.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv16.getText().equals(day)) {
            augTv16.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            augTv16.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv17.getText().equals(day)) {
            augTv17.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            augTv17.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv18.getText().equals(day)) {
            augTv18.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            augTv18.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv19.getText().equals(day)) {
            augTv19.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            augTv19.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv20.getText().equals(day)) {
            augTv20.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            augTv20.setTextColor(getResources().getColor(R.color.white));

        }

        if (augTv21.getText().equals(day)) {
            augTv21.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            augTv21.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv22.getText().equals(day)) {
            augTv22.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            augTv22.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv23.getText().equals(day)) {
            augTv23.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            augTv23.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv24.getText().equals(day)) {
            augTv24.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            augTv24.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv25.getText().equals(day)) {
            augTv25.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            augTv25.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv26.getText().equals(day)) {
            augTv26.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            augTv26.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv27.getText().equals(day)) {
            augTv27.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            augTv27.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv28.getText().equals(day)) {
            augTv28.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            augTv28.setTextColor(getResources().getColor(R.color.white));
        }
        if (augTv29.getText().equals(day)) {
            augTv29.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            augTv29.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv30.getText().equals(day)) {
            augTv30.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            augTv30.setTextColor(getResources().getColor(R.color.white));

        }
        if (augTv31.getText().equals(day)) {
            augTv31.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            augTv31.setTextColor(getResources().getColor(R.color.white));

        }
    }

    private void setJulDatesRound(String day) {
        if (julTv1.getText().equals(day)) {
            julTv1.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            julTv1.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv2.getText().equals(day)) {
            julTv2.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            julTv2.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv3.getText().equals(day)) {
            julTv3.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            julTv3.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv4.getText().equals(day)) {
            julTv4.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            julTv4.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv5.getText().equals(day)) {
            julTv5.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            julTv5.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv6.getText().equals(day)) {
            julTv6.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            julTv6.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv7.getText().equals(day)) {
            julTv7.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            julTv7.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv8.getText().equals(day)) {
            julTv8.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            julTv8.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv9.getText().equals(day)) {
            julTv9.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            julTv9.setTextColor(getResources().getColor(R.color.white));


        }
        if (julTv10.getText().equals(day)) {
            julTv10.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            julTv10.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv11.getText().equals(day)) {
            julTv11.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            julTv11.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv12.getText().equals(day)) {
            julTv12.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            julTv12.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv13.getText().equals(day)) {
            julTv13.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            julTv13.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv14.getText().equals(day)) {
            julTv14.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            julTv14.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv15.getText().equals(day)) {
            julTv15.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            julTv15.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv16.getText().equals(day)) {
            julTv16.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            julTv16.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv17.getText().equals(day)) {
            julTv17.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            julTv17.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv18.getText().equals(day)) {
            julTv18.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            julTv18.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv19.getText().equals(day)) {
            julTv19.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            julTv19.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv20.getText().equals(day)) {
            julTv20.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            julTv20.setTextColor(getResources().getColor(R.color.white));

        }

        if (julTv21.getText().equals(day)) {
            julTv21.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            julTv21.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv22.getText().equals(day)) {
            julTv22.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            julTv22.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv23.getText().equals(day)) {
            julTv23.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            julTv23.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv24.getText().equals(day)) {
            julTv24.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            julTv24.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv25.getText().equals(day)) {
            julTv25.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            julTv25.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv26.getText().equals(day)) {
            julTv26.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            julTv26.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv27.getText().equals(day)) {
            julTv27.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            julTv27.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv28.getText().equals(day)) {
            julTv28.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            julTv28.setTextColor(getResources().getColor(R.color.white));
        }
        if (julTv29.getText().equals(day)) {
            julTv29.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            julTv29.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv30.getText().equals(day)) {
            julTv30.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            julTv30.setTextColor(getResources().getColor(R.color.white));

        }
        if (julTv31.getText().equals(day)) {
            julTv31.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            julTv31.setTextColor(getResources().getColor(R.color.white));

        }
    }

    private void setJunDatesRound(String day) {
        if (junTv1.getText().equals(day)) {
            junTv1.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            junTv1.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv2.getText().equals(day)) {
            junTv2.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            junTv2.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv3.getText().equals(day)) {
            junTv3.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            junTv3.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv4.getText().equals(day)) {
            junTv4.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            junTv4.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv5.getText().equals(day)) {
            junTv5.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            junTv5.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv6.getText().equals(day)) {
            junTv6.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            junTv6.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv7.getText().equals(day)) {
            junTv7.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            junTv7.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv8.getText().equals(day)) {
            junTv8.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            junTv8.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv9.getText().equals(day)) {
            junTv9.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            junTv9.setTextColor(getResources().getColor(R.color.white));


        }
        if (junTv10.getText().equals(day)) {
            junTv10.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            junTv10.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv11.getText().equals(day)) {
            junTv11.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            junTv11.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv12.getText().equals(day)) {
            junTv12.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            junTv12.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv13.getText().equals(day)) {
            junTv13.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            junTv13.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv14.getText().equals(day)) {
            junTv14.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            junTv14.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv15.getText().equals(day)) {
            junTv15.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            junTv15.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv16.getText().equals(day)) {
            junTv16.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            junTv16.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv17.getText().equals(day)) {
            junTv17.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            junTv17.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv18.getText().equals(day)) {
            junTv18.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            junTv18.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv19.getText().equals(day)) {
            junTv19.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            junTv19.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv20.getText().equals(day)) {
            junTv20.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            junTv20.setTextColor(getResources().getColor(R.color.white));

        }

        if (junTv21.getText().equals(day)) {
            junTv21.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            junTv21.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv22.getText().equals(day)) {
            junTv22.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            junTv22.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv23.getText().equals(day)) {
            junTv23.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            junTv23.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv24.getText().equals(day)) {
            junTv24.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            junTv24.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv25.getText().equals(day)) {
            junTv25.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            junTv25.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv26.getText().equals(day)) {
            junTv26.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            junTv26.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv27.getText().equals(day)) {
            junTv27.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            junTv27.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv28.getText().equals(day)) {
            junTv28.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            junTv28.setTextColor(getResources().getColor(R.color.white));
        }
        if (junTv29.getText().equals(day)) {
            junTv29.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            junTv29.setTextColor(getResources().getColor(R.color.white));

        }
        if (junTv30.getText().equals(day)) {
            junTv30.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            junTv30.setTextColor(getResources().getColor(R.color.white));

        }
    }

    private void setMayDatesRound(String day) {
        if (mayTv1.getText().equals(day)) {
            mayTv1.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            mayTv1.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv2.getText().equals(day)) {
            mayTv2.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            mayTv2.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv3.getText().equals(day)) {
            mayTv3.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            mayTv3.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv4.getText().equals(day)) {
            mayTv4.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            mayTv4.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv5.getText().equals(day)) {
            mayTv5.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            mayTv5.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv6.getText().equals(day)) {
            mayTv6.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            mayTv6.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv7.getText().equals(day)) {
            mayTv7.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            mayTv7.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv8.getText().equals(day)) {
            mayTv8.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            mayTv8.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv9.getText().equals(day)) {
            mayTv9.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            mayTv9.setTextColor(getResources().getColor(R.color.white));


        }
        if (mayTv10.getText().equals(day)) {
            mayTv10.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            mayTv10.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv11.getText().equals(day)) {
            mayTv11.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            mayTv11.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv12.getText().equals(day)) {
            mayTv12.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            mayTv12.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv13.getText().equals(day)) {
            mayTv13.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            mayTv13.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv14.getText().equals(day)) {
            mayTv14.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            mayTv14.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv15.getText().equals(day)) {
            mayTv15.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            mayTv15.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv16.getText().equals(day)) {
            mayTv16.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            mayTv16.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv17.getText().equals(day)) {
            mayTv17.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            mayTv17.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv18.getText().equals(day)) {
            mayTv18.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            mayTv18.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv19.getText().equals(day)) {
            mayTv19.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            mayTv19.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv20.getText().equals(day)) {
            mayTv20.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            mayTv20.setTextColor(getResources().getColor(R.color.white));

        }

        if (mayTv21.getText().equals(day)) {
            mayTv21.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            mayTv21.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv22.getText().equals(day)) {
            mayTv22.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            mayTv22.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv23.getText().equals(day)) {
            mayTv23.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            mayTv23.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv24.getText().equals(day)) {
            mayTv24.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            mayTv24.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv25.getText().equals(day)) {
            mayTv25.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            mayTv25.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv26.getText().equals(day)) {
            mayTv26.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            mayTv26.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv27.getText().equals(day)) {
            mayTv27.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            mayTv27.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv28.getText().equals(day)) {
            mayTv28.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            mayTv28.setTextColor(getResources().getColor(R.color.white));
        }
        if (mayTv29.getText().equals(day)) {
            mayTv29.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            mayTv29.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv30.getText().equals(day)) {
            mayTv30.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            mayTv30.setTextColor(getResources().getColor(R.color.white));

        }
        if (mayTv31.getText().equals(day)) {
            mayTv31.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            mayTv31.setTextColor(getResources().getColor(R.color.white));

        }
    }

    private void setAprDatesRound(String day) {
        if (aprTv1.getText().equals(day)) {
            aprTv1.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            aprTv1.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv2.getText().equals(day)) {
            aprTv2.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            aprTv2.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv3.getText().equals(day)) {
            aprTv3.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            aprTv3.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv4.getText().equals(day)) {
            aprTv4.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            aprTv4.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv5.getText().equals(day)) {
            aprTv5.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            aprTv5.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv6.getText().equals(day)) {
            aprTv6.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            aprTv6.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv7.getText().equals(day)) {
            aprTv7.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            aprTv7.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv8.getText().equals(day)) {
            aprTv8.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            aprTv8.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv9.getText().equals(day)) {
            aprTv9.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            aprTv9.setTextColor(getResources().getColor(R.color.white));


        }
        if (aprTv10.getText().equals(day)) {
            aprTv10.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            aprTv10.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv11.getText().equals(day)) {
            aprTv11.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            aprTv11.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv12.getText().equals(day)) {
            aprTv12.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            aprTv12.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv13.getText().equals(day)) {
            aprTv13.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            aprTv13.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv14.getText().equals(day)) {
            aprTv14.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            aprTv14.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv15.getText().equals(day)) {
            aprTv15.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            aprTv15.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv16.getText().equals(day)) {
            aprTv16.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            aprTv16.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv17.getText().equals(day)) {
            aprTv17.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            aprTv17.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv18.getText().equals(day)) {
            aprTv18.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            aprTv18.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv19.getText().equals(day)) {
            aprTv19.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            aprTv19.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv20.getText().equals(day)) {
            aprTv20.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            aprTv20.setTextColor(getResources().getColor(R.color.white));

        }

        if (aprTv21.getText().equals(day)) {
            aprTv21.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            aprTv21.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv22.getText().equals(day)) {
            aprTv22.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            aprTv22.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv23.getText().equals(day)) {
            aprTv23.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            aprTv23.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv24.getText().equals(day)) {
            aprTv24.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            aprTv24.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv25.getText().equals(day)) {
            aprTv25.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            aprTv25.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv26.getText().equals(day)) {
            aprTv26.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            aprTv26.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv27.getText().equals(day)) {
            aprTv27.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            aprTv27.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv28.getText().equals(day)) {
            aprTv28.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            aprTv28.setTextColor(getResources().getColor(R.color.white));
        }
        if (aprTv29.getText().equals(day)) {
            aprTv29.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            aprTv29.setTextColor(getResources().getColor(R.color.white));

        }
        if (aprTv30.getText().equals(day)) {
            aprTv30.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            aprTv30.setTextColor(getResources().getColor(R.color.white));

        }
    }

    private void setMarDatesRound(String day) {
        //    if (isAdded()) {
        if (marTv1.getText().equals(day)) {
            marTv1.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            marTv1.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv2.getText().equals(day)) {
            marTv2.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            marTv2.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv3.getText().equals(day)) {
            marTv3.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            marTv3.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv4.getText().equals(day)) {
            marTv4.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            marTv4.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv5.getText().equals(day)) {
            marTv5.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            marTv5.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv6.getText().equals(day)) {
            marTv6.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            marTv6.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv7.getText().equals(day)) {
            marTv7.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            marTv7.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv8.getText().equals(day)) {
            marTv8.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            marTv8.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv9.getText().equals(day)) {
            marTv9.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            marTv9.setTextColor(getResources().getColor(R.color.white));


        }
        if (marTv10.getText().equals(day)) {
            marTv10.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            marTv10.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv11.getText().equals(day)) {
            marTv11.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            marTv11.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv12.getText().equals(day)) {
            marTv12.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            marTv12.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv13.getText().equals(day)) {
            marTv13.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            marTv13.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv14.getText().equals(day)) {
            marTv14.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            marTv14.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv15.getText().equals(day)) {
            marTv15.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            marTv15.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv16.getText().equals(day)) {
            marTv16.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            marTv16.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv17.getText().equals(day)) {
            marTv17.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            marTv17.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv18.getText().equals(day)) {
            marTv18.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            marTv18.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv19.getText().equals(day)) {
            marTv19.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            marTv19.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv20.getText().equals(day)) {
            marTv20.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            marTv20.setTextColor(getResources().getColor(R.color.white));

        }

        if (marTv21.getText().equals(day)) {
            marTv21.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            marTv21.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv22.getText().equals(day)) {
            marTv22.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            marTv22.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv23.getText().equals(day)) {
            marTv23.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            marTv23.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv24.getText().equals(day)) {
            marTv24.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            marTv24.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv25.getText().equals(day)) {
            marTv25.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            marTv25.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv26.getText().equals(day)) {
            marTv26.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            marTv26.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv27.getText().equals(day)) {
            marTv27.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            marTv27.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv28.getText().equals(day)) {
            marTv28.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            marTv28.setTextColor(getResources().getColor(R.color.white));
        }
        if (marTv29.getText().equals(day)) {
            marTv29.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            marTv29.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv30.getText().equals(day)) {
            marTv30.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            marTv30.setTextColor(getResources().getColor(R.color.white));

        }
        if (marTv31.getText().equals(day)) {
            marTv31.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            marTv31.setTextColor(getResources().getColor(R.color.white));

        }

        //  }
    }

    private void setFebDatesRound(String day) {
        // if (isAdded()) {
        if (febTv1.getText().equals(day)) {
            febTv1.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            febTv1.setTextColor(getResources().getColor(R.color.white));

        }
        if (febTv2.getText().equals(day)) {
            febTv2.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            febTv2.setTextColor(getResources().getColor(R.color.white));

        }
        if (febTv3.getText().equals(day)) {
            febTv3.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            febTv3.setTextColor(getResources().getColor(R.color.white));

        }
        if (febTv4.getText().equals(day)) {
            febTv4.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            febTv4.setTextColor(getResources().getColor(R.color.white));

        }
        if (febTv5.getText().equals(day)) {
            febTv5.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            febTv5.setTextColor(getResources().getColor(R.color.white));

        }
        if (febTv6.getText().equals(day)) {
            febTv6.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            febTv6.setTextColor(getResources().getColor(R.color.white));

        }
        if (febTv7.getText().equals(day)) {
            febTv7.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            febTv7.setTextColor(getResources().getColor(R.color.white));

        }
        if (febTv8.getText().equals(day)) {
            febTv8.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            febTv8.setTextColor(getResources().getColor(R.color.white));

        }
        if (febTv9.getText().equals(day)) {
            febTv9.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            febTv9.setTextColor(getResources().getColor(R.color.white));

        }
        if (febTv10.getText().equals(day)) {
            febTv10.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            febTv10.setTextColor(getResources().getColor(R.color.white));

        }
        if (febTv11.getText().equals(day)) {
            febTv11.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            febTv11.setTextColor(getResources().getColor(R.color.white));

        }
        if (febTv12.getText().equals(day)) {
            febTv12.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            febTv12.setTextColor(getResources().getColor(R.color.white));

        }
        if (febTv13.getText().equals(day)) {
            febTv13.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            febTv13.setTextColor(getResources().getColor(R.color.white));

        }
        if (febTv14.getText().equals(day)) {
            febTv14.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            febTv14.setTextColor(getResources().getColor(R.color.white));

        }
        if (febTv15.getText().equals(day)) {
            febTv15.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            febTv15.setTextColor(getResources().getColor(R.color.white));

        }
        if (febTv16.getText().equals(day)) {
            febTv16.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            febTv16.setTextColor(getResources().getColor(R.color.white));

        }
        if (febTv17.getText().equals(day)) {
            febTv17.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            febTv17.setTextColor(getResources().getColor(R.color.white));

        }
        if (febTv18.getText().equals(day)) {
            febTv18.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            febTv18.setTextColor(getResources().getColor(R.color.white));

        }
        if (febTv19.getText().equals(day)) {
            febTv19.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            febTv19.setTextColor(getResources().getColor(R.color.white));

        }
        if (febTv20.getText().equals(day)) {
            febTv20.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            febTv20.setTextColor(getResources().getColor(R.color.white));

        }
        if (febTv21.getText().equals(day)) {
            febTv21.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            febTv21.setTextColor(getResources().getColor(R.color.white));

        }
        if (febTv22.getText().equals(day)) {
            febTv22.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            febTv22.setTextColor(getResources().getColor(R.color.white));

        }
        if (febTv23.getText().equals(day)) {
            febTv23.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            febTv23.setTextColor(getResources().getColor(R.color.white));

        }
        if (febTv24.getText().equals(day)) {
            febTv24.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            febTv24.setTextColor(getResources().getColor(R.color.white));

        }
        if (febTv25.getText().equals(day)) {
            febTv25.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            febTv25.setTextColor(getResources().getColor(R.color.white));

        }
        if (febTv26.getText().equals(day)) {
            febTv26.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            febTv26.setTextColor(getResources().getColor(R.color.white));

        }
        if (febTv27.getText().equals(day)) {
            febTv27.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            febTv27.setTextColor(getResources().getColor(R.color.white));

        }
        if (febTv28.getText().equals(day)) {
            febTv28.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            febTv28.setTextColor(getResources().getColor(R.color.white));
        }
        //}

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




    @SuppressLint("NewApi")
    public void JanuaryDatesClick(View view) {
        //  if (isAdded()) {
        TextView t = (TextView) view;
        availabilty =  t.getText() + " Jan";
        if (t.getBackground() == null) {
            // String key = t.getText().toString();
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("date", availabilty);
            db.child(currrentUser.getUid()).child("availability").child(availabilty).updateChildren(hashMap);

            t.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            t.setTextColor(getResources().getColor(R.color.white));
                /*if (t.getBackground() != null) {
                    t.setBackgroundResource(0);
                    t.setTextColor(getResources().getColor(R.color.black));
                } else {
                    t.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
                    t.setTextColor(getResources().getColor(R.color.white));
                }*/
        } else {
            //String key = t.getText().toString();
            db.child(currrentUser.getUid()).child("availability").child(availabilty).removeValue();
            t.setBackgroundResource(0);
            t.setTextColor(getResources().getColor(R.color.black));
        }
        //}
    }

    @SuppressLint("NewApi")
    public void FebruaryDatesClick(View view) {
        //  if (isAdded()) {
        TextView t = (TextView) view;
        availabilty =  t.getText() + " Feb";
        if (t.getBackground() == null) {
            // String key = t.getText().toString();
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("date", availabilty);
            db.child(currrentUser.getUid()).child("availability").child(availabilty).updateChildren(hashMap);

            t.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            t.setTextColor(getResources().getColor(R.color.white));
                /*if (t.getBackground() != null) {
                    t.setBackgroundResource(0);
                    t.setTextColor(getResources().getColor(R.color.black));
                } else {
                    t.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
                    t.setTextColor(getResources().getColor(R.color.white));
                }*/
        } else {
            //String key = t.getText().toString();
            db.child(currrentUser.getUid()).child("availability").child(availabilty).removeValue();
            t.setBackgroundResource(0);
            t.setTextColor(getResources().getColor(R.color.black));
        }
        //}
    }
    @SuppressLint("NewApi")
    public void MarchDatesClick(View view) {
        //  if (isAdded()) {
        TextView t = (TextView) view;
        availabilty =  t.getText() + " Mar";
        if (t.getBackground() == null) {
            // String key = t.getText().toString();
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("date", availabilty);
            db.child(currrentUser.getUid()).child("availability").child(availabilty).updateChildren(hashMap);

            t.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            t.setTextColor(getResources().getColor(R.color.white));
                /*if (t.getBackground() != null) {
                    t.setBackgroundResource(0);
                    t.setTextColor(getResources().getColor(R.color.black));
                } else {
                    t.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
                    t.setTextColor(getResources().getColor(R.color.white));
                }*/
        } else {
            //String key = t.getText().toString();
            db.child(currrentUser.getUid()).child("availability").child(availabilty).removeValue();
            t.setBackgroundResource(0);
            t.setTextColor(getResources().getColor(R.color.black));
        }
        //}
    }
    @SuppressLint("NewApi")
    public void AprilDatesClick(View view) {
        //  if (isAdded()) {
        TextView t = (TextView) view;
        availabilty =  t.getText() + " Apr";
        if (t.getBackground() == null) {
            // String key = t.getText().toString();
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("date", availabilty);
            db.child(currrentUser.getUid()).child("availability").child(availabilty).updateChildren(hashMap);

            t.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            t.setTextColor(getResources().getColor(R.color.white));
                /*if (t.getBackground() != null) {
                    t.setBackgroundResource(0);
                    t.setTextColor(getResources().getColor(R.color.black));
                } else {
                    t.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
                    t.setTextColor(getResources().getColor(R.color.white));
                }*/
        } else {
            //String key = t.getText().toString();
            db.child(currrentUser.getUid()).child("availability").child(availabilty).removeValue();
            t.setBackgroundResource(0);
            t.setTextColor(getResources().getColor(R.color.black));
        }
        //}
    }
    @SuppressLint("NewApi")
    public void MayDatesClick(View view) {
        //  if (isAdded()) {
        TextView t = (TextView) view;
        availabilty =  t.getText() + " May";
        if (t.getBackground() == null) {
            // String key = t.getText().toString();
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("date", availabilty);
            db.child(currrentUser.getUid()).child("availability").child(availabilty).updateChildren(hashMap);

            t.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            t.setTextColor(getResources().getColor(R.color.white));
                /*if (t.getBackground() != null) {
                    t.setBackgroundResource(0);
                    t.setTextColor(getResources().getColor(R.color.black));
                } else {
                    t.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
                    t.setTextColor(getResources().getColor(R.color.white));
                }*/
        } else {
            //String key = t.getText().toString();
            db.child(currrentUser.getUid()).child("availability").child(availabilty).removeValue();
            t.setBackgroundResource(0);
            t.setTextColor(getResources().getColor(R.color.black));
        }
        //}
    }
    @SuppressLint("NewApi")
    public void JuneDatesClick(View view) {
        //  if (isAdded()) {
        TextView t = (TextView) view;
        availabilty =   t.getText() + " Jun";
        if (t.getBackground() == null) {
            // String key = t.getText().toString();
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("date", availabilty);
            db.child(currrentUser.getUid()).child("availability").child(availabilty).updateChildren(hashMap);

            t.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            t.setTextColor(getResources().getColor(R.color.white));
                /*if (t.getBackground() != null) {
                    t.setBackgroundResource(0);
                    t.setTextColor(getResources().getColor(R.color.black));
                } else {
                    t.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
                    t.setTextColor(getResources().getColor(R.color.white));
                }*/
        } else {
            //String key = t.getText().toString();
            db.child(currrentUser.getUid()).child("availability").child(availabilty).removeValue();
            t.setBackgroundResource(0);
            t.setTextColor(getResources().getColor(R.color.black));
        }
        //}
    }
    @SuppressLint("NewApi")
    public void JulyDatesClick(View view) {
        //  if (isAdded()) {
        TextView t = (TextView) view;
        availabilty =  t.getText() + " Jul";
        if (t.getBackground() == null) {
            // String key = t.getText().toString();
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("date", availabilty);
            db.child(currrentUser.getUid()).child("availability").child(availabilty).updateChildren(hashMap);

            t.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            t.setTextColor(getResources().getColor(R.color.white));
                /*if (t.getBackground() != null) {
                    t.setBackgroundResource(0);
                    t.setTextColor(getResources().getColor(R.color.black));
                } else {
                    t.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
                    t.setTextColor(getResources().getColor(R.color.white));
                }*/
        } else {
            //String key = t.getText().toString();
            db.child(currrentUser.getUid()).child("availability").child(availabilty).removeValue();
            t.setBackgroundResource(0);
            t.setTextColor(getResources().getColor(R.color.black));
        }
        //}
    }
    @SuppressLint("NewApi")
    public void AugustDatesClick(View view) {
        //  if (isAdded()) {
        TextView t = (TextView) view;
        availabilty =  t.getText() + " Aug";
        if (t.getBackground() == null) {
            // String key = t.getText().toString();
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("date", availabilty);
            db.child(currrentUser.getUid()).child("availability").child(availabilty).updateChildren(hashMap);

            t.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            t.setTextColor(getResources().getColor(R.color.white));
                /*if (t.getBackground() != null) {
                    t.setBackgroundResource(0);
                    t.setTextColor(getResources().getColor(R.color.black));
                } else {
                    t.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
                    t.setTextColor(getResources().getColor(R.color.white));
                }*/
        } else {
            //String key = t.getText().toString();
            db.child(currrentUser.getUid()).child("availability").child(availabilty).removeValue();
            t.setBackgroundResource(0);
            t.setTextColor(getResources().getColor(R.color.black));
        }
        //}
    }
    @SuppressLint("NewApi")
    public void SeptemberDatesClick(View view) {
        //  if (isAdded()) {
        TextView t = (TextView) view;
        availabilty =  t.getText() + " Sep" ;
        if (t.getBackground() == null) {
            // String key = t.getText().toString();
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("date", availabilty);
            db.child(currrentUser.getUid()).child("availability").child(availabilty).updateChildren(hashMap);

            t.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            t.setTextColor(getResources().getColor(R.color.white));
                /*if (t.getBackground() != null) {
                    t.setBackgroundResource(0);
                    t.setTextColor(getResources().getColor(R.color.black));
                } else {
                    t.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
                    t.setTextColor(getResources().getColor(R.color.white));
                }*/
        } else {
            //String key = t.getText().toString();
            db.child(currrentUser.getUid()).child("availability").child(availabilty).removeValue();
            t.setBackgroundResource(0);
            t.setTextColor(getResources().getColor(R.color.black));
        }
        //}
    }



    @SuppressLint("NewApi")
    public void OctoberDatesClick(View view) {
        //  if (isAdded()) {
        TextView t = (TextView) view;
        availabilty =  t.getText() + " Oct";
        if (t.getBackground() == null) {
            // String key = t.getText().toString();
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("date", availabilty);
            db.child(currrentUser.getUid()).child("availability").child(availabilty).updateChildren(hashMap);

            t.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            t.setTextColor(getResources().getColor(R.color.white));
                /*if (t.getBackground() != null) {
                    t.setBackgroundResource(0);
                    t.setTextColor(getResources().getColor(R.color.black));
                } else {
                    t.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
                    t.setTextColor(getResources().getColor(R.color.white));
                }*/
        } else {
            //String key = t.getText().toString();
            db.child(currrentUser.getUid()).child("availability").child(availabilty).removeValue();
            t.setBackgroundResource(0);
            t.setTextColor(getResources().getColor(R.color.black));
        }
        //}
    }

    public void NovemberDatesClick(View view) {
        //if (isAdded()) {
        TextView t = (TextView) view;
        availabilty =  t.getText() + "Nov ";
        if (t.getBackground() == null) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("date", availabilty);
            db.child(currrentUser.getUid()).child("availability").child(availabilty).updateChildren(hashMap);
                /*if (t.getBackground() != null) {
                    t.setBackgroundResource(0);
                    t.setTextColor(getResources().getColor(R.color.black));
                } else {
                    t.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
                    t.setTextColor(getResources().getColor(R.color.white));
                    //    }
                }*/

            t.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            t.setTextColor(getResources().getColor(R.color.white));
        }else {
            db.child(currrentUser.getUid()).child("availability").child(availabilty).removeValue();
            t.setBackgroundResource(0);
            t.setTextColor(getResources().getColor(R.color.black));
            //  }
        }
    }
    public void DecemberDatesClick(View view) {
        //if (isAdded()) {
        TextView t = (TextView) view;
        availabilty =  t.getText() + " Dec";

        if (t.getBackground() == null) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("date", availabilty);
            db.child(currrentUser.getUid()).child("availability").child(availabilty).updateChildren(hashMap);
                /*if (t.getBackground() != null) {
                    t.setBackgroundResource(0);
                    t.setTextColor(getResources().getColor(R.color.black));
                } else {
                    t.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
                    t.setTextColor(getResources().getColor(R.color.white));
                    //    }
                }*/

            t.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            t.setTextColor(getResources().getColor(R.color.white));

        }else {
            db.child(currrentUser.getUid()).child("availability").child(availabilty).removeValue();
            t.setBackgroundResource(0);
            t.setTextColor(getResources().getColor(R.color.black));
            //  }
        }
    }



    private void initJanTextViews() {
        janTv1 = findViewById(R.id.jan_tv_1);
        janTv2 = findViewById(R.id.jan_tv_2);
        janTv3 = findViewById(R.id.jan_tv_3);
        janTv4 = findViewById(R.id.jan_tv_4);
        janTv5 = findViewById(R.id.jan_tv_5);
        janTv6 = findViewById(R.id.jan_tv_6);
        janTv7 = findViewById(R.id.jan_tv_7);
        janTv8 = findViewById(R.id.jan_tv_8);
        janTv9 = findViewById(R.id.jan_tv_9);
        janTv10 = findViewById(R.id.jan_tv_10);
        janTv11 = findViewById(R.id.jan_tv_11);
        janTv12 = findViewById(R.id.jan_tv_12);
        janTv13 = findViewById(R.id.jan_tv_13);
        janTv14 = findViewById(R.id.jan_tv_14);
        janTv15 = findViewById(R.id.jan_tv_15);
        janTv16 = findViewById(R.id.jan_tv_16);
        janTv17 = findViewById(R.id.jan_tv_17);
        janTv18 = findViewById(R.id.jan_tv_18);
        janTv19 = findViewById(R.id.jan_tv_19);
        janTv20 = findViewById(R.id.jan_tv_20);
        janTv21 = findViewById(R.id.jan_tv_21);
        janTv22 = findViewById(R.id.jan_tv_22);
        janTv23 = findViewById(R.id.jan_tv_23);
        janTv24 = findViewById(R.id.jan_tv_24);
        janTv25 = findViewById(R.id.jan_tv_25);
        janTv26 = findViewById(R.id.jan_tv_26);
        janTv27 = findViewById(R.id.jan_tv_27);
        janTv28 = findViewById(R.id.jan_tv_28);
        janTv29 = findViewById(R.id.jan_tv_29);
        janTv30 = findViewById(R.id.jan_tv_30);
        janTv31 = findViewById(R.id.jan_tv_31);
    }

    private void initFebTextViews() {
        febTv1 = findViewById(R.id.feb_tv_1);
        febTv2 = findViewById(R.id.feb_tv_2);
        febTv3 = findViewById(R.id.feb_tv_3);
        febTv4 = findViewById(R.id.feb_tv_4);
        febTv5 = findViewById(R.id.feb_tv_5);
        febTv6 = findViewById(R.id.feb_tv_6);
        febTv7 = findViewById(R.id.feb_tv_7);
        febTv8 = findViewById(R.id.feb_tv_8);
        febTv9 = findViewById(R.id.feb_tv_9);
        febTv10 = findViewById(R.id.feb_tv_10);
        febTv11 = findViewById(R.id.feb_tv_11);
        febTv12 = findViewById(R.id.feb_tv_12);
        febTv13 = findViewById(R.id.feb_tv_13);
        febTv14 = findViewById(R.id.feb_tv_14);
        febTv15 = findViewById(R.id.feb_tv_15);
        febTv16 = findViewById(R.id.feb_tv_16);
        febTv17 = findViewById(R.id.feb_tv_17);
        febTv18 = findViewById(R.id.feb_tv_18);
        febTv19 = findViewById(R.id.feb_tv_19);
        febTv20 = findViewById(R.id.feb_tv_20);
        febTv21 = findViewById(R.id.feb_tv_21);
        febTv22 = findViewById(R.id.feb_tv_22);
        febTv23 = findViewById(R.id.feb_tv_23);
        febTv24 = findViewById(R.id.feb_tv_24);
        febTv25 = findViewById(R.id.feb_tv_25);
        febTv26 = findViewById(R.id.feb_tv_26);
        febTv27 = findViewById(R.id.feb_tv_27);
        febTv28 = findViewById(R.id.feb_tv_28);
    }

    private void initMarTextViews() {
        marTv1 = findViewById(R.id.mar_tv_1);
        marTv2 = findViewById(R.id.mar_tv_2);
        marTv3 = findViewById(R.id.mar_tv_3);
        marTv4 = findViewById(R.id.mar_tv_4);
        marTv5 = findViewById(R.id.mar_tv_5);
        marTv6 = findViewById(R.id.mar_tv_6);
        marTv7 = findViewById(R.id.mar_tv_7);
        marTv8 = findViewById(R.id.mar_tv_8);
        marTv9 = findViewById(R.id.mar_tv_9);
        marTv10 = findViewById(R.id.mar_tv_10);
        marTv11 = findViewById(R.id.mar_tv_11);
        marTv12 = findViewById(R.id.mar_tv_12);
        marTv13 = findViewById(R.id.mar_tv_13);
        marTv14 = findViewById(R.id.mar_tv_14);
        marTv15 = findViewById(R.id.mar_tv_15);
        marTv16 = findViewById(R.id.mar_tv_16);
        marTv17 = findViewById(R.id.mar_tv_17);
        marTv18 = findViewById(R.id.mar_tv_18);
        marTv19 = findViewById(R.id.mar_tv_19);
        marTv20 = findViewById(R.id.mar_tv_20);
        marTv21 = findViewById(R.id.mar_tv_21);
        marTv22 = findViewById(R.id.mar_tv_22);
        marTv23 = findViewById(R.id.mar_tv_23);
        marTv24 = findViewById(R.id.mar_tv_24);
        marTv25 = findViewById(R.id.mar_tv_25);
        marTv26 = findViewById(R.id.mar_tv_26);
        marTv27 = findViewById(R.id.mar_tv_27);
        marTv28 = findViewById(R.id.mar_tv_28);
        marTv29 = findViewById(R.id.mar_tv_29);
        marTv30 = findViewById(R.id.mar_tv_30);
        marTv31 = findViewById(R.id.mar_tv_31);
    }

    private void initAprTextViews() {
        aprTv1 = findViewById(R.id.apr_tv_1);
        aprTv2 = findViewById(R.id.apr_tv_2);
        aprTv3= findViewById(R.id.apr_tv_3);
        aprTv4 = findViewById(R.id.apr_tv_4);
        aprTv5 = findViewById(R.id.apr_tv_5);
        aprTv6 = findViewById(R.id.apr_tv_6);
        aprTv7 = findViewById(R.id.apr_tv_7);
        aprTv8 = findViewById(R.id.apr_tv_8);
        aprTv9 = findViewById(R.id.apr_tv_9);
        aprTv10 = findViewById(R.id.apr_tv_10);
        aprTv11 = findViewById(R.id.apr_tv_11);
        aprTv12 = findViewById(R.id.apr_tv_12);
        aprTv13 = findViewById(R.id.apr_tv_13);
        aprTv14 = findViewById(R.id.apr_tv_14);
        aprTv15 = findViewById(R.id.apr_tv_15);
        aprTv16 = findViewById(R.id.apr_tv_16);
        aprTv17 = findViewById(R.id.apr_tv_17);
        aprTv18 = findViewById(R.id.apr_tv_18);
        aprTv19 = findViewById(R.id.apr_tv_19);
        aprTv20 = findViewById(R.id.apr_tv_20);
        aprTv21 = findViewById(R.id.apr_tv_21);
        aprTv22 = findViewById(R.id.apr_tv_22);
        aprTv23 = findViewById(R.id.apr_tv_23);
        aprTv24 = findViewById(R.id.apr_tv_24);
        aprTv25 = findViewById(R.id.apr_tv_25);
        aprTv26 = findViewById(R.id.apr_tv_26);
        aprTv27 = findViewById(R.id.apr_tv_27);
        aprTv28 = findViewById(R.id.apr_tv_28);
        aprTv29 = findViewById(R.id.apr_tv_29);
        aprTv30 = findViewById(R.id.apr_tv_30);
    }

    private void initMayTextViews() {
        mayTv1 = findViewById(R.id.may_tv_1);
        mayTv2 = findViewById(R.id.may_tv_2);
        mayTv3 = findViewById(R.id.may_tv_3);
        mayTv4 = findViewById(R.id.may_tv_4);
        mayTv5 = findViewById(R.id.may_tv_5);
        mayTv6 = findViewById(R.id.may_tv_6);
        mayTv7 = findViewById(R.id.may_tv_7);
        mayTv8 = findViewById(R.id.may_tv_8);
        mayTv9 = findViewById(R.id.may_tv_9);
        mayTv10 = findViewById(R.id.may_tv_10);
        mayTv11 = findViewById(R.id.may_tv_11);
        mayTv12 = findViewById(R.id.may_tv_12);
        mayTv13 = findViewById(R.id.may_tv_13);
        mayTv14 = findViewById(R.id.may_tv_14);
        mayTv15 = findViewById(R.id.may_tv_15);
        mayTv16 = findViewById(R.id.may_tv_16);
        mayTv17 = findViewById(R.id.may_tv_17);
        mayTv18 = findViewById(R.id.may_tv_18);
        mayTv19 = findViewById(R.id.may_tv_19);
        mayTv20 = findViewById(R.id.may_tv_20);
        mayTv21 = findViewById(R.id.may_tv_21);
        mayTv22 = findViewById(R.id.may_tv_22);
        mayTv23 = findViewById(R.id.may_tv_23);
        mayTv24 = findViewById(R.id.may_tv_24);
        mayTv25 = findViewById(R.id.may_tv_25);
        mayTv26 = findViewById(R.id.may_tv_26);
        mayTv27 = findViewById(R.id.may_tv_27);
        mayTv28 = findViewById(R.id.may_tv_28);
        mayTv29 = findViewById(R.id.may_tv_29);
        mayTv30 = findViewById(R.id.may_tv_30);
        mayTv31 = findViewById(R.id.may_tv_31);
    }

    private void initJunTextViews() {
        junTv1 = findViewById(R.id.jun_tv_1);
        junTv2 = findViewById(R.id.jun_tv_2);
        junTv3 = findViewById(R.id.jun_tv_3);
        junTv4 = findViewById(R.id.jun_tv_4);
        junTv5 = findViewById(R.id.jun_tv_5);
        junTv6 = findViewById(R.id.jun_tv_6);
        junTv7= findViewById(R.id.jun_tv_7);
        junTv8 = findViewById(R.id.jun_tv_8);
        junTv9 = findViewById(R.id.jun_tv_9);
        junTv10 = findViewById(R.id.jun_tv_10);
        junTv11 = findViewById(R.id.jun_tv_11);
        junTv12 = findViewById(R.id.jun_tv_12);
        junTv13 = findViewById(R.id.jun_tv_13);
        junTv14 = findViewById(R.id.jun_tv_14);
        junTv15 = findViewById(R.id.jun_tv_15);
        junTv16 = findViewById(R.id.jun_tv_16);
        junTv17 = findViewById(R.id.jun_tv_17);
        junTv18 = findViewById(R.id.jun_tv_18);
        junTv19 = findViewById(R.id.jun_tv_19);
        junTv20 = findViewById(R.id.jun_tv_20);
        junTv21 = findViewById(R.id.jun_tv_21);
        junTv22 = findViewById(R.id.jun_tv_22);
        junTv23 = findViewById(R.id.jun_tv_23);
        junTv24 = findViewById(R.id.jun_tv_24);
        junTv25 = findViewById(R.id.jun_tv_25);
        junTv26 = findViewById(R.id.jun_tv_26);
        junTv27 = findViewById(R.id.jun_tv_27);
        junTv28 = findViewById(R.id.jun_tv_28);
        junTv29 = findViewById(R.id.jun_tv_29);
        junTv30 = findViewById(R.id.jun_tv_30);
    }

    private void initJulTextViews() {
        julTv1 = findViewById(R.id.jul_tv_1);
        julTv2 = findViewById(R.id.jul_tv_2);
        julTv3 = findViewById(R.id.jul_tv_3);
        julTv4 = findViewById(R.id.jul_tv_4);
        julTv5 = findViewById(R.id.jul_tv_5);
        julTv6 = findViewById(R.id.jul_tv_6);
        julTv7 = findViewById(R.id.jul_tv_7);
        julTv8 = findViewById(R.id.jul_tv_8);
        julTv9 = findViewById(R.id.jul_tv_9);
        julTv10 = findViewById(R.id.jul_tv_10);
        julTv11 = findViewById(R.id.jul_tv_11);
        julTv12 = findViewById(R.id.jul_tv_12);
        julTv13 = findViewById(R.id.jul_tv_13);
        julTv14 = findViewById(R.id.jul_tv_14);
        julTv15 = findViewById(R.id.jul_tv_15);
        julTv16 = findViewById(R.id.jul_tv_16);
        julTv17 = findViewById(R.id.jul_tv_17);
        julTv18 = findViewById(R.id.jul_tv_18);
        julTv19 = findViewById(R.id.jul_tv_19);
        julTv20 = findViewById(R.id.jul_tv_20);
        julTv21 = findViewById(R.id.jul_tv_21);
        julTv22 = findViewById(R.id.jul_tv_22);
        julTv23 = findViewById(R.id.jul_tv_23);
        julTv24 = findViewById(R.id.jul_tv_24);
        julTv25 = findViewById(R.id.jul_tv_25);
        julTv26 = findViewById(R.id.jul_tv_26);
        julTv27 = findViewById(R.id.jul_tv_27);
        julTv28 = findViewById(R.id.jul_tv_28);
        julTv29 = findViewById(R.id.jul_tv_29);
        julTv30 = findViewById(R.id.jul_tv_30);
        julTv31 = findViewById(R.id.jul_tv_31);
    }

    private void initAugTextViews() {
        augTv1 = findViewById(R.id.aug_tv_1);
        augTv2 = findViewById(R.id.aug_tv_2);
        augTv3 = findViewById(R.id.aug_tv_3);
        augTv4 = findViewById(R.id.aug_tv_4);
        augTv5 = findViewById(R.id.aug_tv_5);
        augTv6 = findViewById(R.id.aug_tv_6);
        augTv7 = findViewById(R.id.aug_tv_7);
        augTv8 = findViewById(R.id.aug_tv_8);
        augTv9 = findViewById(R.id.aug_tv_9);
        augTv10 = findViewById(R.id.aug_tv_10);
        augTv11 = findViewById(R.id.aug_tv_11);
        augTv12 = findViewById(R.id.aug_tv_12);
        augTv13 = findViewById(R.id.aug_tv_13);
        augTv14 = findViewById(R.id.aug_tv_14);
        augTv15 = findViewById(R.id.aug_tv_15);
        augTv16 = findViewById(R.id.aug_tv_16);
        augTv17 = findViewById(R.id.aug_tv_17);
        augTv18 = findViewById(R.id.aug_tv_18);
        augTv19 = findViewById(R.id.aug_tv_19);
        augTv20 = findViewById(R.id.aug_tv_20);
        augTv21 = findViewById(R.id.aug_tv_21);
        augTv22 = findViewById(R.id.aug_tv_22);
        augTv23 = findViewById(R.id.aug_tv_23);
        augTv24 = findViewById(R.id.aug_tv_24);
        augTv25 = findViewById(R.id.aug_tv_25);
        augTv26 = findViewById(R.id.aug_tv_26);
        augTv27 = findViewById(R.id.aug_tv_27);
        augTv28 = findViewById(R.id.aug_tv_28);
        augTv29 = findViewById(R.id.aug_tv_29);
        augTv30 = findViewById(R.id.aug_tv_30);
        augTv31 = findViewById(R.id.aug_tv_31);
    }

    private void initSepTextViews() {
        sepTv1 = findViewById(R.id.sept_tv_1);
        sepTv2 = findViewById(R.id.sept_tv_2);
        sepTv3 = findViewById(R.id.sept_tv_3);
        sepTv4 = findViewById(R.id.sept_tv_4);
        sepTv5 = findViewById(R.id.sept_tv_5);
        sepTv6 = findViewById(R.id.sept_tv_6);
        sepTv7 = findViewById(R.id.sept_tv_7);
        sepTv8 = findViewById(R.id.sept_tv_8);
        sepTv9 = findViewById(R.id.sept_tv_9);
        sepTv10 = findViewById(R.id.sept_tv_10);
        sepTv11 = findViewById(R.id.sept_tv_11);
        sepTv12 = findViewById(R.id.sept_tv_12);
        sepTv13 = findViewById(R.id.sept_tv_13);
        sepTv14 = findViewById(R.id.sept_tv_14);
        sepTv15 = findViewById(R.id.sept_tv_15);
        sepTv16 = findViewById(R.id.sept_tv_16);
        sepTv17 = findViewById(R.id.sept_tv_17);
        sepTv18 = findViewById(R.id.sept_tv_18);
        sepTv19 = findViewById(R.id.sept_tv_19);
        sepTv20 = findViewById(R.id.sept_tv_20);
        sepTv21 = findViewById(R.id.sept_tv_21);
        sepTv22 = findViewById(R.id.sept_tv_22);
        sepTv23 = findViewById(R.id.sept_tv_23);
        sepTv24 = findViewById(R.id.sept_tv_24);
        sepTv25 = findViewById(R.id.sept_tv_25);
        sepTv26 = findViewById(R.id.sept_tv_26);
        sepTv27 = findViewById(R.id.sept_tv_27);
        sepTv28 = findViewById(R.id.sept_tv_28);
        sepTv29 = findViewById(R.id.sept_tv_29);
        sepTv30 = findViewById(R.id.sept_tv_30);
    }

    private void initOctTextViews() {
        octTv1 = findViewById(R.id.oct_tv_1);
        octTv2 = findViewById(R.id.oct_tv_2);
        octTv3 = findViewById(R.id.oct_tv_3);
        octTv4 = findViewById(R.id.oct_tv_4);
        octTv5 = findViewById(R.id.oct_tv_5);
        octTv6 = findViewById(R.id.oct_tv_6);
        octTv7 = findViewById(R.id.oct_tv_7);
        octTv8 = findViewById(R.id.oct_tv_8);
        octTv9 = findViewById(R.id.oct_tv_9);
        octTv10 = findViewById(R.id.oct_tv_10);
        octTv11 = findViewById(R.id.oct_tv_11);
        octTv12 = findViewById(R.id.oct_tv_12);
        octTv13 = findViewById(R.id.oct_tv_13);
        octTv14 = findViewById(R.id.oct_tv_14);
        octTv15 = findViewById(R.id.oct_tv_15);
        octTv16 = findViewById(R.id.oct_tv_16);
        octTv17 = findViewById(R.id.oct_tv_17);
        octTv18 = findViewById(R.id.oct_tv_18);
        octTv19 = findViewById(R.id.oct_tv_19);
        octTv20 = findViewById(R.id.oct_tv_20);
        octTv21 = findViewById(R.id.oct_tv_21);
        octTv22 = findViewById(R.id.oct_tv_22);
        octTv23 = findViewById(R.id.oct_tv_23);
        octTv24 = findViewById(R.id.oct_tv_24);
        octTv25 = findViewById(R.id.oct_tv_25);
        octTv26 = findViewById(R.id.oct_tv_26);
        octTv27 = findViewById(R.id.oct_tv_27);
        octTv28 = findViewById(R.id.oct_tv_28);
        octTv29 = findViewById(R.id.oct_tv_29);
        octTv30 = findViewById(R.id.oct_tv_30);
        octTv31 = findViewById(R.id.oct_tv_31);
    }

    private void initNovTextViews() {
        novTv1 = findViewById(R.id.nov_tv_1);
        novTv2 = findViewById(R.id.nov_tv_2);
        novTv3 = findViewById(R.id.nov_tv_3);
        novTv4 = findViewById(R.id.nov_tv_4);
        novTv5 = findViewById(R.id.nov_tv_5);
        novTv6 = findViewById(R.id.nov_tv_6);
        novTv7 = findViewById(R.id.nov_tv_7);
        novTv8 = findViewById(R.id.nov_tv_8);
        novTv9 = findViewById(R.id.nov_tv_9);
        novTv10 = findViewById(R.id.nov_tv_10);
        novTv11 = findViewById(R.id.nov_tv_11);
        novTv12 = findViewById(R.id.nov_tv_12);
        novTv13 = findViewById(R.id.nov_tv_13);
        novTv14 = findViewById(R.id.nov_tv_14);
        novTv15 = findViewById(R.id.nov_tv_15);
        novTv16 = findViewById(R.id.nov_tv_16);
        novTv17 = findViewById(R.id.nov_tv_17);
        novTv18 = findViewById(R.id.nov_tv_18);
        novTv19 = findViewById(R.id.nov_tv_19);
        novTv20 = findViewById(R.id.nov_tv_20);
        novTv21 = findViewById(R.id.nov_tv_21);
        novTv22 = findViewById(R.id.nov_tv_22);
        novTv23 = findViewById(R.id.nov_tv_23);
        novTv24 = findViewById(R.id.nov_tv_24);
        novTv25 = findViewById(R.id.nov_tv_25);
        novTv26 = findViewById(R.id.nov_tv_26);
        novTv27 = findViewById(R.id.nov_tv_27);
        novTv28 = findViewById(R.id.nov_tv_28);
        novTv29 = findViewById(R.id.nov_tv_29);
        novTv30 = findViewById(R.id.nov_tv_30);
    }

    private void initDecTextViews() {
        decTv1 = findViewById(R.id.dec_tv_1);
        decTv2 = findViewById(R.id.dec_tv_2);
        decTv3 = findViewById(R.id.dec_tv_3);
        decTv4 = findViewById(R.id.dec_tv_4);
        decTv5 = findViewById(R.id.dec_tv_5);
        decTv6 = findViewById(R.id.dec_tv_6);
        decTv7 = findViewById(R.id.dec_tv_7);
        decTv8 = findViewById(R.id.dec_tv_8);
        decTv9 = findViewById(R.id.dec_tv_9);
        decTv10 = findViewById(R.id.dec_tv_10);
        decTv11 = findViewById(R.id.dec_tv_11);
        decTv12 = findViewById(R.id.dec_tv_12);
        decTv13 = findViewById(R.id.dec_tv_13);
        decTv14 = findViewById(R.id.dec_tv_14);
        decTv15 = findViewById(R.id.dec_tv_15);
        decTv16 = findViewById(R.id.dec_tv_16);
        decTv17 = findViewById(R.id.dec_tv_17);
        decTv18 = findViewById(R.id.dec_tv_18);
        decTv19 = findViewById(R.id.dec_tv_19);
        decTv20 = findViewById(R.id.dec_tv_20);
        decTv21 = findViewById(R.id.dec_tv_21);
        decTv22 = findViewById(R.id.dec_tv_22);
        decTv23 = findViewById(R.id.dec_tv_23);
        decTv24 = findViewById(R.id.dec_tv_24);
        decTv25 = findViewById(R.id.dec_tv_25);
        decTv26 = findViewById(R.id.dec_tv_26);
        decTv27 = findViewById(R.id.dec_tv_27);
        decTv28 = findViewById(R.id.dec_tv_28);
        decTv29 = findViewById(R.id.dec_tv_29);
        decTv30 = findViewById(R.id.dec_tv_30);
        decTv31 = findViewById(R.id.dec_tv_31);
    }

}