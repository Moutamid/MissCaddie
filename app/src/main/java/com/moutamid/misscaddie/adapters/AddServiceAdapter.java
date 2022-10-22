package com.moutamid.misscaddie.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.moutamid.misscaddie.R;
import com.moutamid.misscaddie.models.ServiceListModel;

import java.util.ArrayList;

public class AddServiceAdapter extends RecyclerView.Adapter<AddServiceAdapter.ServiceVH> {

    Context context;
    ArrayList<ServiceListModel> list;

    public AddServiceAdapter(Context context, ArrayList<ServiceListModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ServiceVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.add_service, parent, false);
        return new AddServiceAdapter.ServiceVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceVH holder, int position) {
        ServiceListModel model = list.get(position);
        holder.serviceTitle.setText(model.getTitle());
        holder.price.setText(model.getPrice());

        holder.deleteBtn.setOnClickListener(v -> {
            removeItem(model.getId());
            list.remove(position);
            notifyItemRemoved(position);
            notifyDataSetChanged();
        });
    }

    private void removeItem(String id) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        FirebaseDatabase.getInstance().getReference().child("Caddie")
                .child(currentUser.getUid())
                .child("services").child(id).removeValue();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ServiceVH extends RecyclerView.ViewHolder{
        ImageView deleteBtn;
        EditText serviceTitle, price;

        public ServiceVH(@NonNull View itemView) {
            super(itemView);
            deleteBtn = itemView.findViewById(R.id.deleteService);
            serviceTitle = itemView.findViewById(R.id.et_service);
            price = itemView.findViewById(R.id.et_price);
        }
    }
}
