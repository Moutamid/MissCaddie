package com.moutamid.misscaddie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.ServiceVH> {
    Context context;
    ArrayList<ServiceListModel> list;

    public ServiceListAdapter(Context context, ArrayList<ServiceListModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ServiceVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.servicelistcard, parent, false);
        return new ServiceVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceVH holder, int position) {
        ServiceListModel model = list.get(position);
        holder.title.setText(model.getTitle());
        holder.price.setText(model.getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ServiceVH extends RecyclerView.ViewHolder{
        TextView title, price;

        public ServiceVH(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.serviceTitle);
            price = itemView.findViewById(R.id.servicePrice);

        }
    }
}
