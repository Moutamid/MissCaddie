package com.moutamid.misscaddie.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.misscaddie.CaddiesRequestsActivity;
import com.moutamid.misscaddie.adapters.DAPAdapter;
import com.moutamid.misscaddie.R;
import com.moutamid.misscaddie.adapters.RequestesAdapter;
import com.moutamid.misscaddie.models.RequestsModel;
import com.moutamid.misscaddie.models.ServiceListModel;

import java.util.ArrayList;
import java.util.List;


public class DeclinedFragment extends Fragment {
    RecyclerView requestRC;
    List<RequestsModel> itemList = new ArrayList<>();
    private DatabaseReference db;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    public DeclinedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_declined, container, false);

        requestRC = view.findViewById(R.id.declinedRC);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference().child("Requests");

        requestRC.setLayoutManager(new LinearLayoutManager(view.getContext()));
        requestRC.setHasFixedSize(false);
        getRequests();

        return view;
    }

    private void getRequests() {
        Query query = db.orderByChild("status_title").equalTo("Decline");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    itemList.clear();
                    for (DataSnapshot ds : snapshot.getChildren()){
                        RequestsModel model = ds.getValue(RequestsModel.class);
                        if(model.getCaddieId().equals(user.getUid())) {
                            itemList.add(model);
                        }
                    }

                    DAPAdapter adapter = new DAPAdapter(itemList, getActivity());

                    requestRC.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private List<RequestsModel> RequestItems()
    {


       /* RequestsModel item2 = new RequestsModel(null, "M. Moutamid", "65", "Declined", "15 Oct - 27 Nov",
       "Dean St, Brooklyn, NY, USA", tableMessage2() );
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