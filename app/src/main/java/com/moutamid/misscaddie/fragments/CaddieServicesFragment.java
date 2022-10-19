package com.moutamid.misscaddie.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.misscaddie.R;
import com.moutamid.misscaddie.databinding.FragmentCaddieServicesBinding;
import com.moutamid.misscaddie.models.ServiceListModel;
import com.moutamid.misscaddie.adapters.ServiceListAdapter;

import java.util.ArrayList;

public class CaddieServicesFragment extends Fragment {
   // RecyclerView service_listRC;
    ServiceListAdapter adapter;
    ArrayList<ServiceListModel> list;
    private FragmentCaddieServicesBinding b;
    private String userId;
    private DatabaseReference db;

    public CaddieServicesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     //   View view = inflater.inflate(R.layout.fragment_caddie_services, container, false);
        b = FragmentCaddieServicesBinding.inflate(getLayoutInflater());
        list = new ArrayList<>();
        userId = getArguments().getString("uId");
        db = FirebaseDatabase.getInstance().getReference().child("Caddie");
        /*ServiceListModel model = new ServiceListModel("Service Num 1", "$80");
        ServiceListModel model2 = new ServiceListModel("Service Num 2", "$180");
        ServiceListModel model3 = new ServiceListModel("Service Num 3", "$20");
        ServiceListModel model4 = new ServiceListModel("Service Num 4", "$50");

        list.add(model);
        list.add(model2);
        list.add(model3);
        list.add(model4);*/
        getServices();

        b.serviceListRC.setHasFixedSize(false);
        b.serviceListRC.setLayoutManager(new LinearLayoutManager(getActivity()));
        return b.getRoot();
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
                            adapter = new ServiceListAdapter(getActivity(), list);
                            b.serviceListRC.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}