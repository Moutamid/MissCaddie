package com.moutamid.misscaddie.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.moutamid.misscaddie.R;
import com.moutamid.misscaddie.adapters.CaddieProfileVPadapter;

import java.util.Calendar;
import java.util.Date;

public class CaddieListFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    TextView welcomeText, datetext;

    public CaddieListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_caddie_list, container, false);

        tabLayout = view.findViewById(R.id.tabsLayout);
        viewPager = view.findViewById(R.id.ProfileViewpager);
        welcomeText = view.findViewById(R.id.text_heading);
        datetext = view.findViewById(R.id.date);
        greetingMessage();

        CaddieProfileVPadapter caddieProfileVPadapter = new CaddieProfileVPadapter(getActivity().getSupportFragmentManager());
        caddieProfileVPadapter.addFragment(new PendingFragment(), "Pending");
        caddieProfileVPadapter.addFragment(new AcceptedFragment(), "Accepted");
        caddieProfileVPadapter.addFragment(new DeclinedFragment(), "Declined");

        viewPager.setAdapter(caddieProfileVPadapter);
        tabLayout.setupWithViewPager(viewPager);

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