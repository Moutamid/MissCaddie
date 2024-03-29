package com.moutamid.misscaddie.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.misscaddie.CaddieContactActivity;
import com.moutamid.misscaddie.R;
import com.moutamid.misscaddie.models.Bonus;
import com.moutamid.misscaddie.models.ServiceListModel;
import com.moutamid.misscaddie.adapters.ServiceListAdapter;

import java.util.ArrayList;
import java.util.List;

public class CaddieServicesFragment extends Fragment {
    RecyclerView service_listRC;
    ServiceListAdapter adapter;
    ArrayList<ServiceListModel> list;
    private String userId;
    private ListView listView;
    private TextView contactCaddie;
    private DatabaseReference db;
    private List<String> bonusList = new ArrayList<>();

    public CaddieServicesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_caddie_services, container, false);
        list = new ArrayList<>();
        userId = getArguments().getString("uId");
        db = FirebaseDatabase.getInstance().getReference().child("Caddie");
        service_listRC = view.findViewById(R.id.service_listRC);
        contactCaddie = view.findViewById(R.id.contact_caddie);
        listView = view.findViewById(R.id.listView);
        /*ServiceListModel model = new ServiceListModel("Service Num 1", "$80");
        ServiceListModel model2 = new ServiceListModel("Service Num 2", "$180");
        ServiceListModel model3 = new ServiceListModel("Service Num 3", "$20");
        ServiceListModel model4 = new ServiceListModel("Service Num 4", "$50");

        list.add(model);
        list.add(model2);
        list.add(model3);
        list.add(model4);*/
        getServices();
        contactCaddie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CaddieContactActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
                Animatoo.animateSwipeLeft(getActivity());
            }
        });
        service_listRC.setHasFixedSize(false);
        service_listRC.setLayoutManager(new LinearLayoutManager(getActivity()));
        db.child(userId).child("bonus").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    bonusList.clear();
                    for (DataSnapshot ds : snapshot.getChildren()){
                        Bonus model = ds.getValue(Bonus.class);
                        bonusList.add(model.getBonus());
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(requireActivity(),
                            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,bonusList);
                    listView.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    private void getServices() {
        db.child(userId).child("services")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            for (DataSnapshot ds : snapshot.getChildren()){
                                ServiceListModel model = ds.getValue(ServiceListModel.class);
                                list.add(model);
                            }
                            adapter = new ServiceListAdapter(getActivity(), list,false);
                            service_listRC.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}