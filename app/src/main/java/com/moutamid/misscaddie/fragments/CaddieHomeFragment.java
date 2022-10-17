package com.moutamid.misscaddie.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moutamid.misscaddie.R;

import java.util.Calendar;
import java.util.Date;

public class CaddieHomeFragment extends Fragment {
    TextView welcomeText, datetext, viewCalender;
    BottomNavigationView navigationView;

    public CaddieHomeFragment() {
        // Required empty public constructor
    }

    public CaddieHomeFragment(BottomNavigationView navigationView) {
        this.navigationView = navigationView;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_caddie_home, container, false);
        welcomeText = view.findViewById(R.id.text_heading);
        datetext = view.findViewById(R.id.date);
        viewCalender = view.findViewById(R.id.view_cal);

        viewCalender.setOnClickListener(v -> {
            navigationView.setSelectedItemId(R.id.calender_menu);
            FragmentManager.findFragment(view).getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new CaddieCalenderFragment()).commit();
        });

        greetingMessage();
        return view;
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