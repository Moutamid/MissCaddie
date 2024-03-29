package com.moutamid.misscaddie.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.misscaddie.CaddieProfileActivity;
import com.moutamid.misscaddie.models.Model_Caddie;
import com.moutamid.misscaddie.R;
import com.moutamid.misscaddie.models.Review;
import com.moutamid.misscaddie.models.ServiceListModel;

import java.util.ArrayList;

public class Adapter_Golfer extends RecyclerView.Adapter<Adapter_Golfer.HolderAndroid> {

    private Context context;
    private ArrayList<Model_Caddie> androidArrayList;
    private ArrayList<ServiceListModel> serviceListModels = new ArrayList<>();

    public Adapter_Golfer(Context context, ArrayList<Model_Caddie> androidArrayList) {
        this.context = context;
        this.androidArrayList = androidArrayList;
    }

    @NonNull
    @Override
    public HolderAndroid onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_golfer, parent, false);
        return new HolderAndroid(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderAndroid holder, int position) {
        Model_Caddie modelAndroid = androidArrayList.get(position);

        String name_tv= modelAndroid.getName();
        //String price_tv = modelAndroid.getPrice();
        String length_tv = modelAndroid.getFeet() + "'"+ modelAndroid.getInches() + "''";
        String cat_tv = modelAndroid.getCatagory();
        String place_tv = modelAndroid.getState();
        //String reviews_tv = modelAndroid.getReviews();
        String status_tv = modelAndroid.getStatus();
        getReviewsDetail(holder.reviews,holder.rating,modelAndroid.getId());
     //   int image_1 = modelAndroid.getImage();

        holder.name.setText(name_tv);
       // holder.price.setText(price_tv);
        holder.length.setText(length_tv);
        holder.place.setText(place_tv);
        holder.catagory.setText(cat_tv);
        //holder.price.setText(price_tv);
      //  holder.reviews.setText(reviews_tv);

        if (status_tv.equals("willing")){
            holder.status.setText("Willing to Travel");
            holder.status_icon.setImageResource(R.drawable.ic_charm_tick1);
        }else if (status_tv.equals("not willing")){
            holder.status.setText("Not Willing to Travel");
            holder.status_icon.setImageResource(R.drawable.ic_charm_cross);
        }


        Glide.with(context)
                .load(modelAndroid.getImage())
                .placeholder(R.drawable.bi_person_fill)
                        .into(holder.image);
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Caddie");
        Query query = db.child(modelAndroid.getId()).child("services")
                .orderByChild("price").limitToFirst(1);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot ds : snapshot.getChildren()){
                        ServiceListModel model = ds.getValue(ServiceListModel.class);
                        holder.price.setText("USD$ "+ model.getPrice());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //holder.image.setImageResource(image_1);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context.getApplicationContext(), CaddieProfileActivity.class);
            intent.putExtra("uId",modelAndroid.getId());
            context.startActivity(intent);
        });
    }

    float count = 0;
    int reviewChild = 0;
    private void getReviewsDetail(TextView reviews, RatingBar rating, String id) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser  = mAuth.getCurrentUser();
        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference().child("Review")
                .child(currentUser.getUid());
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    reviewChild = (int) snapshot.getChildrenCount();
                    for (DataSnapshot ds : snapshot.getChildren()){
                        Review model = ds.getValue(Review.class);
                        if (model.getCaddieId().equals(id)){
                            count += model.getRating();
                            reviews.setText(reviewChild + " Reviews");

                            float total = (float) (count/reviewChild);
                            rating.setRating(total);
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return androidArrayList.size();
    }

    class HolderAndroid extends RecyclerView.ViewHolder {

        TextView name , price , length , catagory , place , reviews , status;
        ImageView image,status_icon;
        RatingBar rating;

        HolderAndroid(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.img_golfer);
            name = itemView.findViewById(R.id.name_golfer);
            length = itemView.findViewById(R.id.length_golfer);
            catagory = itemView.findViewById(R.id.catagory_golfer);
            price = itemView.findViewById(R.id.price_golfer);
            place = itemView.findViewById(R.id.place_golfer);
            reviews = itemView.findViewById(R.id.reviews_golfer);
            rating = itemView.findViewById(R.id.rating);
            status = itemView.findViewById(R.id.status_golfer);
            status_icon = itemView.findViewById(R.id.status_icon);
        }
    }
}
