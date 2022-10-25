package com.moutamid.misscaddie.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moutamid.misscaddie.R;
import com.moutamid.misscaddie.models.Bonus;
import com.moutamid.misscaddie.models.ServiceListModel;

import java.util.ArrayList;

public class AddOnsListAdapter extends RecyclerView.Adapter<AddOnsListAdapter.ServiceVH> {
    Context context;
    ArrayList<Bonus> list;

    public AddOnsListAdapter(Context context, ArrayList<Bonus> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ServiceVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_ons_custom_layout, parent, false);
        return new ServiceVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceVH holder, @SuppressLint("RecyclerView") int position) {
        Bonus model = list.get(position);
        holder.bonus.setText(model.getBonus());
        holder.deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAddOns(model.getId(),position);
            }
        });
    }

    private void deleteAddOns(String id, int position) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currrentUser = mAuth.getCurrentUser();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Caddie");
        db.child(currrentUser.getUid()).child("bonus").child(id).removeValue();
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeRemoved(position,list.size());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ServiceVH extends RecyclerView.ViewHolder{
        TextView bonus;
        ImageView deleteImg;

        public ServiceVH(@NonNull View itemView) {
            super(itemView);

            bonus = itemView.findViewById(R.id.bonus);
            deleteImg = itemView.findViewById(R.id.delete);

        }
    }
}
