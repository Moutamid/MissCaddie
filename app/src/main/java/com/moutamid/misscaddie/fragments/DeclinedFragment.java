package com.moutamid.misscaddie.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moutamid.misscaddie.adapters.DAPAdapter;
import com.moutamid.misscaddie.R;
import com.moutamid.misscaddie.models.RequestsModel;
import com.moutamid.misscaddie.models.ServiceListModel;

import java.util.ArrayList;
import java.util.List;


public class DeclinedFragment extends Fragment {
    RecyclerView requestRC;

    public DeclinedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_declined, container, false);

        requestRC = view.findViewById(R.id.declinedRC);

        DAPAdapter adapter = new DAPAdapter(RequestItems(), view.getContext());

        requestRC.setAdapter(adapter);
        requestRC.setLayoutManager(new LinearLayoutManager(view.getContext()));
        requestRC.setHasFixedSize(false);

        return view;
    }

    private List<RequestsModel> RequestItems()
    {
        List<RequestsModel> itemList = new ArrayList<>();

       /* RequestsModel item2 = new RequestsModel(null, "M. Moutamid", "65", "Declined", "15 Oct - 27 Nov", "Dean St, Brooklyn, NY, USA", tableMessage2() );
        itemList.add(item2);
        RequestsModel item4 = new RequestsModel(null, "M. Mohamed", "85", "Declined", "15 Oct - 27 Nov", "Dean St, Brooklyn, NY, USA", tableMessage2() );
        itemList.add(item4);*/

        return itemList;
    }

    private List<ServiceListModel> tableMessage()
    {
        List<ServiceListModel> ChildItemList = new ArrayList<>();

        ChildItemList.add(new ServiceListModel("Ride Along", "80"));
        ChildItemList.add(new ServiceListModel("Caddie Party", "30"));
        ChildItemList.add(new ServiceListModel("Service 3", "450"));
        ChildItemList.add(new ServiceListModel("Service 5", "10"));

        return ChildItemList;
    }

    private List<ServiceListModel> tableMessage2()
    {
        List<ServiceListModel> ChildItemList = new ArrayList<>();

        ChildItemList.add(new ServiceListModel("Ride Along", "80"));
        ChildItemList.add(new ServiceListModel("Caddie Party", "30"));

        return ChildItemList;
    }

}