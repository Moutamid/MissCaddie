package com.moutamid.misscaddie;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class PendingFragment extends Fragment {
    RecyclerView requestRC;

    public PendingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending, container, false);

        requestRC = view.findViewById(R.id.pendingRC);

        DAPAdapter adapter = new DAPAdapter(RequestItems(), view.getContext());

        requestRC.setAdapter(adapter);
        requestRC.setLayoutManager(new LinearLayoutManager(view.getContext()));
        requestRC.setHasFixedSize(false);

        return view;
    }

    private List<RequestsModel> RequestItems()
    {
        List<RequestsModel> itemList = new ArrayList<>();

        RequestsModel item = new RequestsModel(null, "Suleman Ijaz", "5", "Pending", "5 Oct - 15 Nov", "Dean St, Brooklyn, NY, USA", tableMessage());
        itemList.add(item);

        RequestsModel item2 = new RequestsModel(null, "M. Moutamid", "65", "Pending", "15 Oct - 27 Nov", "Dean St, Brooklyn, NY, USA", tableMessage2() );
        itemList.add(item2);


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