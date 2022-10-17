package com.moutamid.misscaddie.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moutamid.misscaddie.CaddieEditProfileActivity;
import com.moutamid.misscaddie.CaddieProServicesActivity;
import com.moutamid.misscaddie.R;

import java.util.Calendar;
import java.util.Date;

public class CaddieProfileFragment extends Fragment {
    CardView profileBtn, serviceBtn, privacybtn, termsbtn;
    TextView welcomeText, datetext;

    public CaddieProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_caddie_profile, container, false);

        profileBtn = view.findViewById(R.id.profilebtn);
        serviceBtn = view.findViewById(R.id.servicebtn);
        termsbtn = view.findViewById(R.id.termsbtn);
        privacybtn = view.findViewById(R.id.privacybtn);
        welcomeText = view.findViewById(R.id.text_heading);
        datetext = view.findViewById(R.id.date);
        greetingMessage();

        termsbtn.setOnClickListener(v -> {
            Uri webpage = Uri.parse("https://www.google.com");
            Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
            try {
                startActivity(webIntent);
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        privacybtn.setOnClickListener(v -> {
            Uri webpage = Uri.parse("https://www.google.com");
            Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
            try {
                startActivity(webIntent);
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        profileBtn.setOnClickListener(v -> {
            startActivity(new Intent(getActivity().getApplicationContext(), CaddieEditProfileActivity.class));
        });
        serviceBtn.setOnClickListener(v -> {
            startActivity(new Intent(getActivity().getApplicationContext(), CaddieProServicesActivity.class));
        });

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