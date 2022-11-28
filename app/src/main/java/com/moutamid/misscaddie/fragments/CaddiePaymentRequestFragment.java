package com.moutamid.misscaddie.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.misscaddie.R;
import com.moutamid.misscaddie.adapters.CaddiePaymentRequestesAdapter;
import com.moutamid.misscaddie.adapters.PaymentRequestesAdapter;
import com.moutamid.misscaddie.models.RequestsModel;

import java.util.ArrayList;

public class CaddiePaymentRequestFragment extends Fragment {

    RecyclerView requestRC;
    private DatabaseReference db;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ArrayList<RequestsModel> itemList;
    private Bundle savedState = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment_request, container, false);
        requestRC = view.findViewById(R.id.recyclerView_golfer);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference().child("Requests");
        itemList = new ArrayList<>();
        requestRC.setLayoutManager(new LinearLayoutManager(getActivity()));
        requestRC.setHasFixedSize(false);
        if (isAdded()){
            if(savedInstanceState != null && savedState == null) {
                savedState = savedInstanceState.getBundle("state");
            }
            if(savedState != null) {
                getRequests();
            }
            savedState = null;
        }
        return view;
    }

    private void getRequests() {
        Query query = db.orderByChild("status_title").equalTo("payment_request");
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

                    CaddiePaymentRequestesAdapter adapter = new CaddiePaymentRequestesAdapter(itemList, getActivity());
                    requestRC.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        getRequests();
    }

    private Bundle saveState() { /* called either from onDestroyView() or onSaveInstanceState() */
        Bundle state = new Bundle();
        state.putCharSequence("state", "fragment");
        return state;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle("state", (savedState != null) ? savedState : saveState());
    }

}
