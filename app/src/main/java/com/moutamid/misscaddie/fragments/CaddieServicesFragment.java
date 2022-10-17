package com.moutamid.misscaddie.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moutamid.misscaddie.R;
import com.moutamid.misscaddie.models.ServiceListModel;
import com.moutamid.misscaddie.adapters.ServiceListAdapter;

import java.util.ArrayList;

public class CaddieServicesFragment extends Fragment {
    RecyclerView service_listRC;
    ServiceListAdapter adapter;
    ArrayList<ServiceListModel> list;

    public CaddieServicesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_caddie_services, container, false);

        service_listRC = view.findViewById(R.id.service_listRC);
        list = new ArrayList<>();

        ServiceListModel model = new ServiceListModel("Service Num 1", "$80");
        ServiceListModel model2 = new ServiceListModel("Service Num 2", "$180");
        ServiceListModel model3 = new ServiceListModel("Service Num 3", "$20");
        ServiceListModel model4 = new ServiceListModel("Service Num 4", "$50");

        list.add(model);
        list.add(model2);
        list.add(model3);
        list.add(model4);

        adapter = new ServiceListAdapter(view.getContext(), list);

        service_listRC.setHasFixedSize(false);
        service_listRC.setLayoutManager(new LinearLayoutManager(view.getContext()));
        service_listRC.setAdapter(adapter);
        return view;
    }
}