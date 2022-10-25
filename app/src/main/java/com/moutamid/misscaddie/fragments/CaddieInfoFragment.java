package com.moutamid.misscaddie.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.misscaddie.CaddieContactActivity;
import com.moutamid.misscaddie.CaddieProfileActivity;
import com.moutamid.misscaddie.R;
import com.moutamid.misscaddie.databinding.FragmentCaddieInfoBinding;
import com.moutamid.misscaddie.models.Model_Caddie;
import com.moutamid.misscaddie.models.ServiceListModel;

public class CaddieInfoFragment extends Fragment {

    private FragmentCaddieInfoBinding b;
    private String userId;
    private DatabaseReference db;

    public CaddieInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        b = FragmentCaddieInfoBinding.inflate(getLayoutInflater());
        userId = getArguments().getString("uId");
        db = FirebaseDatabase.getInstance().getReference().child("Caddie");
        getCaddieInfo();
        b.contactCaddie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CaddieContactActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
                Animatoo.animateSwipeLeft(getActivity());
            }
        });
        return b.getRoot();
    }

    private void getCaddieInfo() {
        db.child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Model_Caddie model = snapshot.getValue(Model_Caddie.class);
                            b.height.setText(model.getLength() + " cm");
                            b.location.setText(model.getPlace());
                            if (snapshot.child("about").exists()) {
                                b.aboutMessage.setText(model.getAbout());
                            }
                            if (model.getStatus().equals("willing")){
                                b.WillingIcon.setImageResource(R.drawable.ic_charm_tick1);
                            }else {
                                b.WillingIcon.setImageResource(R.drawable.ic_charm_cross);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}