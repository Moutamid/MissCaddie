package com.moutamid.misscaddie.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.moutamid.misscaddie.CardPaymentActivity;
import com.moutamid.misscaddie.Dashboard_Golfer;
import com.moutamid.misscaddie.GolferFilterActivity;
import com.moutamid.misscaddie.R;
import com.moutamid.misscaddie.SharedPreferencesManager;
import com.moutamid.misscaddie.adapters.Adapter_Golfer;
import com.moutamid.misscaddie.models.Model_Caddie;
import com.moutamid.misscaddie.models.RequestsModel;

import java.util.ArrayList;

public class GolferHomeFragment extends Fragment {

    private RecyclerView golfer_recycler;
    private ArrayList<Model_Caddie> modelGolferArrayList;
    private Adapter_Golfer adapterGolfer;
    private DatabaseReference db;
    FirebaseAuth mAuth;
    private String state,status,start_date,last_date;
    FirebaseUser currrentUser;
    private boolean filter = false;
    private TextView filterBtn;
    private SharedPreferencesManager manager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_golfer_home, container, false);
        mAuth = FirebaseAuth.getInstance();
        currrentUser = mAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference().child("Caddie");
        modelGolferArrayList = new ArrayList<>();
        golfer_recycler = view.findViewById(R.id.recyclerView_golfer);
        filterBtn = view.findViewById(R.id.filter);
        if (getArguments() != null) {
          //  state = getArguments().getString("state", "");
            //status = getArguments().getString("status", "");
            //date = getArguments().getString("date", "");
            filter = getArguments().getBoolean("filter", false);
        }

        manager = new SharedPreferencesManager(getActivity());
        state = manager.retrieveString("state","");
        start_date = manager.retrieveString("sdate","");
        last_date = manager.retrieveString("ldate","");
        status = manager.retrieveString("status","");
        if (filter) {
            filterDate();
            filterBtn.setText("Reset Filter");
        }
        else {
            load_detail();
            filterBtn.setText("Filter Your Search");
        }
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filter){
                    filter = false;
                    load_detail();
                    filterBtn.setText("Filter Your Search");
                }else {
                    startActivity(new Intent(getActivity(), GolferFilterActivity.class));
                    getActivity().finish();
                    Animatoo.animateZoom(getActivity());
                }
            }
        });

        return view;
    }

    private void filterDate() {
        DatabaseReference mRequestReference = FirebaseDatabase.getInstance().getReference().child("Requests");
        //    Query query = mRequestReference.orderByChild("status_title").equalTo("Accepted");
        mRequestReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot ds : snapshot.getChildren()){
                        RequestsModel requestsModel = ds.getValue(RequestsModel.class);
                        String date = requestsModel.getDate();
                        int modeldate = Integer.parseInt(date.substring(date.length() - 2).trim());
                        int stdate = Integer.parseInt(start_date.substring(0,2).trim());
                        int ladate = Integer.parseInt(last_date.substring(0,2).trim());

                         //Toast.makeText(getActivity(),""+stdate + " , " +  ladate + " , " + modeldate, Toast.LENGTH_SHORT).show();

                        if (modeldate > stdate || modeldate < ladate){
                            Query query = db.orderByChild("id").equalTo(requestsModel.getCaddieId());
                            query.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot1) {
                                    if (snapshot.exists()){
                                        modelGolferArrayList.clear();
                                        for (DataSnapshot dataSnapshot : snapshot1.getChildren()){
                                            Model_Caddie caddie = dataSnapshot.getValue(Model_Caddie.class);
                                            if (caddie.getState().equals(state) ||
                                                    caddie.getStatus().equals(status)){
                                                modelGolferArrayList.add(caddie);
                                            }else {
                                                modelGolferArrayList.add(caddie);
                                            }
                                        }

                                        adapterGolfer = new Adapter_Golfer(getActivity(),
                                                modelGolferArrayList);
                                        golfer_recycler.setAdapter(adapterGolfer);
                                        adapterGolfer.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void load_detail() {
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    modelGolferArrayList.clear();
                    for (DataSnapshot ds : snapshot.getChildren()){
                        Model_Caddie model_golfer = ds.getValue(Model_Caddie.class);
                        modelGolferArrayList.add(model_golfer);

                    }

                    adapterGolfer = new Adapter_Golfer(getActivity(), modelGolferArrayList);
                    golfer_recycler.setAdapter(adapterGolfer);
                    adapterGolfer.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
