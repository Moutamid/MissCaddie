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

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

public class GolferFilterActivity extends AppCompatActivity {
    View close_btn,save_btn;
    LinearLayout notWillingLayout, WillingLayout;
    TextView willingTv, notWillingTv;
    ImageView iconsNot, iconWill;
    boolean willingState = true, notWillingState = false;
    CardView willingCard, notWillingCard;
    TextView booking_dates;
    private ImageView cancelBtn,filterBtn;
    private String status = "";
    private String date = "";
    private String state = "";
    private Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_golfer_filter);

        close_btn = findViewById(R.id.close);
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

        close_btn.setOnClickListener(v -> {
            Intent intent = new Intent(GolferFilterActivity.this , Dashboard_Golfer.class);
            startActivity(intent);
            Animatoo.animateZoom(GolferFilterActivity.this);
        });
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
                        date = materialDatePicker.getHeaderText();
                        booking_dates.setText("Selected Date is (" + date + ")");
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
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!status.equals("") && !state.equals("") && !date.equals("")){
                    Intent intent = new Intent(GolferFilterActivity.this , Dashboard_Golfer.class);
                    intent.putExtra("state",state);
                    intent.putExtra("status",status);
                    intent.putExtra("date",date);
                    startActivity(intent);
                    Animatoo.animateZoom(GolferFilterActivity.this);
                }
            }
        });
    }
}