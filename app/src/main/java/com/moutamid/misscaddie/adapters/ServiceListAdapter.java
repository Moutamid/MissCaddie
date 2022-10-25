package com.moutamid.misscaddie.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moutamid.misscaddie.R;
import com.moutamid.misscaddie.listners.ItemClickListener;
import com.moutamid.misscaddie.models.ServiceListModel;

import java.util.ArrayList;

public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.ServiceVH> {
    Context context;
    ArrayList<ServiceListModel> list;
    private boolean callBack;
    private ItemClickListener itemClickListener;

    public ServiceListAdapter(Context context, ArrayList<ServiceListModel> list,boolean callBack) {
        this.context = context;
        this.list = list;
        this.callBack = callBack;
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
        holder.price.setText("USD$" +model.getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ServiceVH extends RecyclerView.ViewHolder{
        TextView title, price;
        LinearLayout linearLayout;

        public ServiceVH(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.serviceTitle);
            price = itemView.findViewById(R.id.servicePrice);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener!=null){
                        itemClickListener.onItemClick(getAdapterPosition(),itemView);
                    }
                }
            });
        }
    }
    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;

    }
}
