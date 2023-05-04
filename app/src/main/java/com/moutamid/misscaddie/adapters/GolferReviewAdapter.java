package com.moutamid.misscaddie.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.misscaddie.models.GolferReviewModel;
import com.moutamid.misscaddie.R;
import com.moutamid.misscaddie.models.Model_Golfer;
import com.moutamid.misscaddie.models.Review;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class GolferReviewAdapter extends RecyclerView.Adapter<GolferReviewAdapter.ReviewViewHolder> {
    Context context;
    ArrayList<Review> modelArrayList;

    public GolferReviewAdapter(Context context, ArrayList<Review> modelArrayList) {
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
        Review model = modelArrayList.get(position);
        holder.date.setText(model.getDate());
        holder.message.setText(model.getFeedback());
        //Glide.with(context).load(model.getImage()).into(holder.circleImageView);
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Golfer")
                .child(model.getGolferId());
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Model_Golfer model_golfer = snapshot.getValue(Model_Golfer.class);
                    holder.name.setText(model_golfer.getName());
                    Glide.with(context).load(model_golfer.getImage()).into(holder.circleImageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
