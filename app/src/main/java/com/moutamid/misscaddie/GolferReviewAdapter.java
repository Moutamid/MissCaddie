package com.moutamid.misscaddie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class GolferReviewAdapter extends RecyclerView.Adapter<GolferReviewAdapter.ReviewViewHolder> {
    Context context;
    ArrayList<GolferReviewModel> modelArrayList;

    public GolferReviewAdapter(Context context, ArrayList<GolferReviewModel> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reviews_card, parent, false);
        return new GolferReviewAdapter.ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        GolferReviewModel model = modelArrayList.get(position);
        holder.name.setText(model.getName());
        holder.date.setText(model.getDate());
        holder.message.setText(model.getMessage());
        Glide.with(context).load(model.getImage()).into(holder.circleImageView);
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }


    public class ReviewViewHolder extends RecyclerView.ViewHolder{
        CircleImageView circleImageView;
        TextView name, date, message;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.reviewer_img);
            name = itemView.findViewById(R.id.name_reviewer);
            date = itemView.findViewById(R.id.date);
            message = itemView.findViewById(R.id.message);
        }
    }
}
