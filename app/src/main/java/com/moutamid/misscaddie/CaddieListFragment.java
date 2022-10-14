package com.moutamid.misscaddie;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

public class CaddieListFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;

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

        CaddieProfileVPadapter caddieProfileVPadapter = new CaddieProfileVPadapter(getActivity().getSupportFragmentManager());
        caddieProfileVPadapter.addFragment(new CaddieInfoFragment(), "Pending");
        caddieProfileVPadapter.addFragment(new CaddieServicesFragment(), "Accepted");
        caddieProfileVPadapter.addFragment(new CaddieReviewsFragment(), "Declined");

        viewPager.setAdapter(caddieProfileVPadapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}