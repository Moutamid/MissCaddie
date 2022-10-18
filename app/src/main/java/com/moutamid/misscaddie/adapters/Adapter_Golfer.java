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
import com.moutamid.misscaddie.CaddieProfileActivity;
import com.moutamid.misscaddie.models.Model_Golfer;
import com.moutamid.misscaddie.R;

import java.util.ArrayList;

public class Adapter_Golfer extends RecyclerView.Adapter<Adapter_Golfer.HolderAndroid> {

    private Context context;
    private ArrayList<Model_Golfer> androidArrayList;

    public Adapter_Golfer(Context context, ArrayList<Model_Golfer> androidArrayList) {
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
        Model_Golfer modelAndroid = androidArrayList.get(position);

        String name_tv= modelAndroid.getName();
        String price_tv = modelAndroid.getPrice();
        String length_tv = modelAndroid.getLength();
        String cat_tv = modelAndroid.getCatagory();
        String place_tv = modelAndroid.getPlace();
        String reviews_tv = modelAndroid.getReviews();
        String status_tv = modelAndroid.getStatus();

     //   int image_1 = modelAndroid.getImage();

        holder.name.setText(name_tv);
        holder.price.setText(price_tv);
        holder.length.setText(length_tv);
        holder.catagory.setText(cat_tv);
        holder.price.setText(price_tv);
        holder.reviews.setText(reviews_tv);
        holder.status.setText(status_tv);

        Glide.with(context)
                .load(modelAndroid.getImage())
                        .into(holder.image);

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
        ImageView image;

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

        }
    }
}
