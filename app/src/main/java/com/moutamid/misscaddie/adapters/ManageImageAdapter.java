package com.moutamid.misscaddie.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moutamid.misscaddie.listners.ManageImageListner;
import com.moutamid.misscaddie.models.ManageImageModel;
import com.moutamid.misscaddie.R;

import java.util.ArrayList;

public class ManageImageAdapter extends RecyclerView.Adapter<ManageImageAdapter.ImageViewHolder> {
    Context context;
    ArrayList<ManageImageModel> imageModelArrayList;
    ManageImageListner clickListner;
    TextView heading;

    public ManageImageAdapter(Context context, ArrayList<ManageImageModel> imageModelArrayList, ManageImageListner clickListner, TextView heading) {
        this.context = context;
        this.imageModelArrayList = imageModelArrayList;
        this.clickListner = clickListner;
        this.heading = heading;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.manage_image_card, parent, false);
        return new ManageImageAdapter.ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        ManageImageModel model = imageModelArrayList.get(position);
        if (model.isState()){
            holder.overlay.setVisibility(View.GONE);
        }
        holder.image.setImageURI(model.getImage());
        holder.icon.setBackgroundResource(model.drawable);

        holder.itemView.setOnClickListener(v -> {
            if (model.isState()){
                clickListner.onClick(imageModelArrayList.get(holder.getAdapterPosition()));
            } else {
                imageModelArrayList.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();
                if (imageModelArrayList.size() == 1){
                    heading.setText("Manage" );
                } else {
                    heading.setText("Manage (" + (imageModelArrayList.size() - 1) + ")" );
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageModelArrayList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        ImageView image, icon;
        View overlay;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            icon = itemView.findViewById(R.id.icon);
            overlay = itemView.findViewById(R.id.overlay);
        }
    }
}
