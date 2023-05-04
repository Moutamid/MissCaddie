package com.moutamid.misscaddie.fragments;

import static android.widget.LinearLayout.VERTICAL;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.misscaddie.models.GolferReviewModel;
import com.moutamid.misscaddie.R;
import com.moutamid.misscaddie.adapters.GolferReviewAdapter;
import com.moutamid.misscaddie.models.Review;

import java.util.ArrayList;

public class CaddieReviewsFragment extends Fragment {
    private RecyclerView reviewRecyclerView;
    private ArrayList<Review> modelGolferArrayList;
    private GolferReviewAdapter adapterGolfer;
    private DatabaseReference db;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    public CaddieReviewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_caddie_reviews, container, false);

        reviewRecyclerView = view.findViewById(R.id.reviews_recycler);
        //reviewRecyclerView.setHasFixedSize(false);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        reviewRecyclerView.setLayoutManager(manager);

        modelGolferArrayList = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference().child("Review")
                        .child(user.getUid());
        loadData(view);
        return view;
    }

    private void loadData(View view) {
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    modelGolferArrayList.clear();
                    for (DataSnapshot ds : snapshot.getChildren()){
                        Review model = ds.getValue(Review.class);
                        modelGolferArrayList.add(model);
                    }
                    adapterGolfer = new GolferReviewAdapter(view.getContext(), modelGolferArrayList);
                    reviewRecyclerView.setAdapter(adapterGolfer);
                    adapterGolfer.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}