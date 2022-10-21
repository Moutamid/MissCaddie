package com.moutamid.misscaddie.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moutamid.misscaddie.models.GolferReviewModel;
import com.moutamid.misscaddie.R;
import com.moutamid.misscaddie.adapters.GolferReviewAdapter;

import java.util.ArrayList;

public class CaddieReviewsFragment extends Fragment {
    private RecyclerView reviewRecyclerView;
    private ArrayList<GolferReviewModel> modelGolferArrayList;
    private GolferReviewAdapter adapterGolfer;

    private String[] golfer_name = {"David Rose", "M. Moutamid", "Hannah Charlie", "Sam Karen"};
    private String[] golfer_date = {"September 25, 2022", "August 15, 2022", "July 10, 2022", "June 21, 2022"};
    private String[] golfer_message = {
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy.",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy.",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy.",
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy."
    };
    private int[] images1_golfer = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img2};

    public CaddieReviewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_caddie_reviews, container, false);

        reviewRecyclerView = view.findViewById(R.id.reviews_recycler);
        reviewRecyclerView.setHasFixedSize(false);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        modelGolferArrayList = new ArrayList<>();

      //  loadData(view);

        return view;
    }

    private void loadData(View view) {
        for (int i = 0; i < golfer_name.length; i++) {
            GolferReviewModel modelAndroid = new GolferReviewModel(
                    golfer_name[i],
                    golfer_date[i],
                    golfer_message[i],
                    images1_golfer[i]
            );
            modelGolferArrayList.add(modelAndroid);
        }
        adapterGolfer = new GolferReviewAdapter(view.getContext(), modelGolferArrayList);
        reviewRecyclerView.setAdapter(adapterGolfer);
    }
}