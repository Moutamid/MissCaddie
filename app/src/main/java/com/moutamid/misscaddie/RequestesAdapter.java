package com.moutamid.misscaddie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RequestesAdapter extends RecyclerView.Adapter<RequestesAdapter.VH> {
    RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    List<RequestsModel> itemList;
    Context context;

    public RequestesAdapter(List<RequestsModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.requestes_card, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        RequestsModel model = itemList.get(position);
        holder.name.setText(model.getName());
        holder.price.setText("(" + model.getPrice() + ")");
        holder.address.setText(model.getAddress());
        holder.date.setText(model.getDate());
        holder.status_title.setText("(" + model.getStatus_title() + ")");
        if (model.getStatus_title().equals("Accepted")){
            holder.status_title.setTextColor(context.getResources().getColor(R.color.green));
            holder.butn.setBackgroundResource(R.drawable.ic_charm_tick1);
        } else if (model.getStatus_title().equals("Declined")){
            holder.status_title.setTextColor(context.getResources().getColor(R.color.red));
            holder.butn.setBackgroundResource(R.drawable.ic_charm_cross);
        } else {
            holder.status_title.setTextColor(context.getResources().getColor(R.color.blue));
            holder.butn.setBackgroundResource(0);
        }

        //Glide.with(context).load(model.getImage()).into(holder.image);

        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.tableRC.getContext(), LinearLayoutManager.VERTICAL,false);
        layoutManager.setInitialPrefetchItemCount(model.tableRows.size());

        TableRowAdapter tableRowAdapter = new TableRowAdapter(model.tableRows);
        holder.tableRC.setLayoutManager(layoutManager);
        holder.tableRC.setAdapter(tableRowAdapter);
        holder.tableRC.setRecycledViewPool(viewPool);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class VH extends RecyclerView.ViewHolder{
        CircleImageView image;
        TextView name, price, status_title, date, address;
        RecyclerView tableRC;
        ImageView butn;

        public VH(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img_golfer);
            name = itemView.findViewById(R.id.name_golfer);
            price = itemView.findViewById(R.id.price_golfer);
            status_title = itemView.findViewById(R.id.status_golfer);
            date = itemView.findViewById(R.id.date_golfer);
            address = itemView.findViewById(R.id.address_golfer);
            tableRC = itemView.findViewById(R.id.tableRowRV);
            butn = itemView.findViewById(R.id.checkBtn);
        }
    }
}
