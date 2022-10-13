package com.moutamid.misscaddie;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ManageImageAdapter extends RecyclerView.Adapter<ManageImageAdapter.ImageViewHolder> {
    Context context;
    ArrayList<ManageImageModel> imageModelArrayList;

    public ManageImageAdapter(Context context, ArrayList<ManageImageModel> imageModelArrayList) {
        this.context = context;
        this.imageModelArrayList = imageModelArrayList;
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
                showDialog();
            } else {
                imageModelArrayList.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();
            }
        });
    }

    private void showDialog() {
        Toast.makeText(context, "Add", Toast.LENGTH_SHORT).show();
        /*Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        context.getApplicationContext().startActivityForResult(Intent.createChooser(intent, ""), 101);*/
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
