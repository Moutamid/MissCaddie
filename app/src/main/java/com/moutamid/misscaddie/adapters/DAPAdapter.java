package com.moutamid.misscaddie.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moutamid.misscaddie.R;
import com.moutamid.misscaddie.models.RequestsModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DAPAdapter extends RecyclerView.Adapter<DAPAdapter.VH> {
    RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    List<RequestsModel> itemList;
    Context context;

    public DAPAdapter(List<RequestsModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dap_card, parent, false);
        return new VH(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        RequestsModel model = itemList.get(position);
        String serviceList = "";
        String bullet = context.getResources().getString(R.string.bullet);

        holder.name.setText(model.getName());
        holder.price.setText("(US$" + model.getPrice() + ")");
        holder.address.setText(model.getAddress());
        holder.date.setText(model.getDate());

        if (model.getStatus_title().equals("Accepted")){
            holder.butn.setBackgroundResource(R.drawable.charm_check);
        } else if (model.getStatus_title().equals("Declined")){
            holder.butn.setBackgroundResource(R.drawable.cross_yellow);
        } else {
            holder.butn.setBackgroundResource(R.drawable.arrow_right_yellow);
        }

        for (int i=0; i < model.getTableRows().size(); i++){
            String service = model.getTableRows().get(i).getTitle() + " ($" + model.getTableRows().get(i).getPrice() + ")";
            if (i==2){
                serviceList = serviceList + bullet + "\t\t" + "more";
                break;
            }
            serviceList = serviceList + bullet + "\t\t" + service +  "\n";
        }

        holder.service_list.setText(serviceList);
        //Glide.with(context).load(model.getImage()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class VH extends RecyclerView.ViewHolder{
        CircleImageView image;
        TextView name, price, date, address;
        TextView service_list;
        ImageView butn;

        public VH(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img_golfer);
            name = itemView.findViewById(R.id.name_golfer);
            price = itemView.findViewById(R.id.price_golfer);
            date = itemView.findViewById(R.id.date_golfer);
            address = itemView.findViewById(R.id.address_golfer);
            service_list = itemView.findViewById(R.id.service_list);
            butn = itemView.findViewById(R.id.checkBtn);
        }
    }
}
