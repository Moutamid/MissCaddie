package com.moutamid.misscaddie;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CaddieProfileFragment extends Fragment {
    CardView profileBtn, serviceBtn;

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

        profileBtn.setOnClickListener(v -> {
            startActivity(new Intent(getActivity().getApplicationContext(), CaddieEditProfileActivity.class));
        });
        serviceBtn.setOnClickListener(v -> {
            startActivity(new Intent(getActivity().getApplicationContext(), CaddieProServicesActivity.class));
        });

        return view;
    }
}