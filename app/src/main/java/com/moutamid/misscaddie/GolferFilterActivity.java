package com.moutamid.misscaddie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.util.Pair;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class GolferFilterActivity extends AppCompatActivity {
    View close_btn,save_btn;
    LinearLayout notWillingLayout, WillingLayout;
    TextView willingTv, notWillingTv;
    ImageView iconsNot, iconWill;
    boolean willingState = true, notWillingState = false;
    CardView willingCard, notWillingCard;
    TextView booking_dates,saveBtn;
    private ImageView cancelBtn,filterBtn;
    private String status = "";
    private String start_date = "";
    private String last_date = "";
    private ArrayList<String> strings = new ArrayList<>();
    private String state = "";
    private Spinner spinner;
    private SharedPreferencesManager manager;
    private String[] states = {"Select State (New York)","Alabama","Alaska","Arizona","Arkansas",
            "California","Colorado", "Connecticut", "Delaware",
            "Florida",
            "Georgia",
            "Hawaii",
            "Idaho",
            "Illinois",
            "Indiana",
            "Iowa",
            "Kansas",
            "Kentucky",
            "Louisiana",
            "Maine",
            "Maryland",
            "Massachusetts",
            "Michigan",
            "Minnesota",
            "Mississippi",
            "Missouri",
            "Montana",
            "Nebraska",
            "Nevada",
            "New Hampshire",
            "New Jersey",
            "New Mexico",
            "New York",
            "North Carolina",
            "North Dakota",
            "Ohio",
            "Oklahoma",
            "Oregon",
            "Pennsylvania",
            "Rhode Island",
            "South Carolina",
            "South Dakota",
            "Tennessee",
            "Texas",
            "Utah",
            "Vermont",
            "Virginia",
            "Washington",
            "West Virginia",
            "Wisconsin",
            "Wyoming",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_golfer_filter);

        close_btn = findViewById(R.id.close);
        manager = new SharedPreferencesManager(this);
        state = manager.retrieveString("state","");
        start_date = manager.retrieveString("sdate","");
        last_date = manager.retrieveString("ldate","");
        status = manager.retrieveString("status","");
        notWillingLayout = findViewById(R.id.notWilling);
        WillingLayout = findViewById(R.id.willing);
        iconsNot = findViewById(R.id.notWilling_icon);
        iconWill = findViewById(R.id.willing_icon);
        cancelBtn = findViewById(R.id.cancel);
        filterBtn = findViewById(R.id.filter);
        willingCard = findViewById(R.id.willing_layoutCard);
        notWillingCard = findViewById(R.id.notWilling_layoutCard);
        willingTv = findViewById(R.id.willing_tv);
        notWillingTv = findViewById(R.id.notwilling_tv);
        booking_dates = findViewById(R.id.booking_dates);
        spinner = findViewById(R.id.spinner_golfCourse);
        save_btn = findViewById(R.id.save);
        saveBtn = findViewById(R.id.save_filter);

        close_btn.setOnClickListener(v -> {
            Intent intent = new Intent(GolferFilterActivity.this , Dashboard_Golfer.class);
            startActivity(intent);
            Animatoo.animateZoom(GolferFilterActivity.this);
        });

        getSavedData();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                state = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        MaterialDatePicker.Builder<Pair<Long, Long>> materialDateBuilder = MaterialDatePicker.Builder.dateRangePicker();
        materialDateBuilder.setTitleText("SELECT A DATE");

        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

        booking_dates.setOnClickListener(v -> {
            materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
        });

        materialDatePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        String day = materialDatePicker.getHeaderText();
                        String sdate = day.substring(0, 6).trim();
                        String ldate = day.substring(day.length() - 6).trim();

                        if (sdate.length() == 5){
                            start_date = day.substring(0, 5);
                        }else {
                            start_date = day.substring(0, 6);
                        }

                        if (ldate.length() == 5){
                            last_date = day.substring(day.length() - 5);
                        }else {
                            last_date = day.substring(day.length() - 6);
                        }

                       // Toast.makeText(GolferFilterActivity.this,""+start_date, Toast.LENGTH_SHORT).show();
                        booking_dates.setText("Selected Date is ( " + day + " )");
                    }
                });


        notWillingCard.setOnClickListener(v -> {
                    if (notWillingState) {
                        WillingLayout.setBackground(getDrawable(R.drawable.selected_box));
                        //iconWill.setBackground(getDrawable(R.drawable.ic_charm_tick1));
                        notWillingLayout.setBackground(getDrawable(R.drawable.unselected_box));
                        //iconsNot.setBackground(getDrawable(R.drawable.ic_charm_cross2));
                        willingState = true;
                        willingTv.setTextColor(getResources().getColor(R.color.black));
                        notWillingTv.setTextColor(getResources().getColor(R.color.black_light));
                        notWillingState = false;
                    } else {
                        notWillingLayout.setBackground(getDrawable(R.drawable.selected_box));
                        //iconsNot.setBackground(getDrawable(R.drawable.ic_charm_cross));
                        notWillingState = true;
                        WillingLayout.setBackground(getDrawable(R.drawable.unselected_box));
                        //iconWill.setBackground(getDrawable(R.drawable.ic_charm_tick));
                        willingTv.setTextColor(getResources().getColor(R.color.black_light));
                        notWillingTv.setTextColor(getResources().getColor(R.color.black));
                        willingState = false;
                    }
            status = "not willing";
        });
        willingCard.setOnClickListener(v ->{
            if (willingState){
                WillingLayout.setBackground(getDrawable(R.drawable.unselected_box));
                //iconWill.setBackground(getDrawable(R.drawable.ic_charm_tick));
                willingState = false;
                notWillingLayout.setBackground(getDrawable(R.drawable.selected_box));
                //iconsNot.setBackground(getDrawable(R.drawable.ic_charm_cross));
                notWillingState = true;
                willingTv.setTextColor(getResources().getColor(R.color.black_light));
                notWillingTv.setTextColor(getResources().getColor(R.color.black));
            } else {
                WillingLayout.setBackground(getDrawable(R.drawable.selected_box));
                //iconWill.setBackground(getDrawable(R.drawable.ic_charm_tick1));
                notWillingLayout.setBackground(getDrawable(R.drawable.unselected_box));
                //iconsNot.setBackground(getDrawable(R.drawable.ic_charm_cross2));
                willingState = true;
                willingTv.setTextColor(getResources().getColor(R.color.black));
                notWillingTv.setTextColor(getResources().getColor(R.color.black_light));
                notWillingState = false;
            }
            status = "willing";
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!status.equals("") || !state.equals("") || !start_date.equals("")){
                    Intent intent = new Intent(GolferFilterActivity.this , Dashboard_Golfer.class);
                    //intent.putExtra("state",state);
                    //intent.putExtra("status",status);
                    //intent.putExtra("sdate",date);
                    intent.putExtra("filter",true);
                    startActivity(intent);
                    finish();
                    Animatoo.animateZoom(GolferFilterActivity.this);
                    manager.storeString("state",state);
                    manager.storeString("sdate",start_date);
                    manager.storeString("ldate",last_date);
                    manager.storeString("status",status);
                }else {
                    Toast.makeText(GolferFilterActivity.this, "Please Select all the given fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
      /*  resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GolferFilterActivity.this, "Reset Filter", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    private void getSavedData() {
        for (int i = 0; i < states.length; i++){

            if(states[i].equals(state)){
                spinner.setSelection(i);
            }
        }

        if (start_date.equals("")){
            SimpleDateFormat format = new SimpleDateFormat("dd MMM");
            Calendar calendar = Calendar.getInstance();
            booking_dates.setText("Selected Date is (" + format.format(calendar.getTime()) + ")");
        }else {
            booking_dates.setText("Selected Date is (" + start_date + " - " + last_date + ")");
        }
        if (status.equals("willing")) {
            WillingLayout.setBackground(getDrawable(R.drawable.selected_box));
            //iconWill.setBackground(getDrawable(R.drawable.ic_charm_tick1));
            notWillingLayout.setBackground(getDrawable(R.drawable.unselected_box));
            //iconsNot.setBackground(getDrawable(R.drawable.ic_charm_cross2));
            willingTv.setTextColor(getResources().getColor(R.color.black));
            notWillingTv.setTextColor(getResources().getColor(R.color.black_light));

        } else {
            notWillingLayout.setBackground(getDrawable(R.drawable.selected_box));
            //iconsNot.setBackground(getDrawable(R.drawable.ic_charm_cross));
            WillingLayout.setBackground(getDrawable(R.drawable.unselected_box));
            //iconWill.setBackground(getDrawable(R.drawable.ic_charm_tick));
            willingTv.setTextColor(getResources().getColor(R.color.black_light));
            notWillingTv.setTextColor(getResources().getColor(R.color.black));
        }

    }
}