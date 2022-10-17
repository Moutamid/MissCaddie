package com.moutamid.misscaddie.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.moutamid.misscaddie.R;

import java.util.Calendar;
import java.util.Date;

public class CaddieCalenderFragment extends Fragment {
    TextView welcomeText, datetext;
    View view;

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
        return view;
    }

    public void OctoberDatesClick(View v) {
        TextView t = (TextView) v;
        Toast.makeText(view.getContext(), "October " + t.getText() + ", 2022", Toast.LENGTH_SHORT).show();
        if (t.getBackground() != null){
            t.setBackgroundResource(0);
            t.setTextColor(getResources().getColor(R.color.black));
        } else {
            t.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            t.setTextColor(getResources().getColor(R.color.white));
        }
    }

    public void NovemberDatesClick(View v) {
        TextView t = (TextView) v;
        Toast.makeText(view.getContext(), "November " + t.getText() + ", 2022", Toast.LENGTH_SHORT).show();
        if (t.getBackground() != null){
            t.setBackgroundResource(0);
            t.setTextColor(getResources().getColor(R.color.black));
        } else {
            t.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            t.setTextColor(getResources().getColor(R.color.white));
        }
    }

    public void DecemberDatesClick(View v) {
        TextView t = (TextView) v;
        Toast.makeText(view.getContext(), "December " + t.getText() + ", 2022", Toast.LENGTH_SHORT).show();
        if (t.getBackground() != null){
            t.setBackgroundResource(0);
            t.setTextColor(getResources().getColor(R.color.black));
        } else {
            t.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
            t.setTextColor(getResources().getColor(R.color.white));
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