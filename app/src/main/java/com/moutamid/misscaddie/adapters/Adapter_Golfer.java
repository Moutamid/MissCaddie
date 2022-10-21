package com.moutamid.misscaddie.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.misscaddie.CaddieProfileActivity;
import com.moutamid.misscaddie.models.Model_Caddie;
import com.moutamid.misscaddie.models.Model_Golfer;
import com.moutamid.misscaddie.R;
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
        String length_tv = modelAndroid.getLength();
        String cat_tv = modelAndroid.getCatagory();
        String place_tv = modelAndroid.getState();
        String reviews_tv = modelAndroid.getReviews();
        String status_tv = modelAndroid.getStatus();

     //   int image_1 = modelAndroid.getImage();

        holder.name.setText(name_tv);
       // holder.price.setText(price_tv);
        holder.length.setText(length_tv + " cm");
        holder.place.setText(place_tv);
        holder.catagory.setText(cat_tv);
        //holder.price.setText(price_tv);
        holder.reviews.setText(reviews_tv);
        if (status_tv.equals("willing")){
            holder.status.setText("Willing to Travel");
            holder.status_icon.setImageResource(R.drawable.ic_charm_tick1);
        }else {
            holder.status.setText("Not Willing to Travel");
            holder.status_icon.setImageResource(R.drawable.ic_charm_cross);
        }


        Glide.with(context)
                .load(modelAndroid.getImage())
                .placeholder(R.drawable.img3)
                        .into(holder.image);
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Caddie");
        db.child(modelAndroid.getId()).child("services").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot ds : snapshot.getChildren()){
                        ServiceListModel model = ds.getValue(ServiceListModel.class);
                        serviceListModels.add(model);
                    }
                    holder.price.setText("US$ "+ serviceListModels.get(0).getPrice());
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

    @Override
    public int getItemCount() {
        return androidArrayList.size();
    }

    class HolderAndroid extends RecyclerView.ViewHolder {

        TextView name , price , length , catagory , place , reviews , status;
        ImageView image,status_icon;

        HolderAndroid(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.img_golfer);
            name = itemView.findViewById(R.id.name_golfer);
            length = itemView.findViewById(R.id.length_golfer);
            catagory = itemView.findViewById(R.id.catagory_golfer);
            price = itemView.findViewById(R.id.price_golfer);
            place = itemView.findViewById(R.id.place_golfer);
            reviews = itemView.findViewById(R.id.reviews_golfer);
            status = itemView.findViewById(R.id.status_golfer);
            status_icon = itemView.findViewById(R.id.status_icon);
        }
    }
}
