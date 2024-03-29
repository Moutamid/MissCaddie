package com.moutamid.misscaddie.adapters;

import android.annotation.SuppressLint;
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
import com.moutamid.misscaddie.CaddieBookingDetailsActivity;
import com.moutamid.misscaddie.R;
import com.moutamid.misscaddie.models.Model_Golfer;
import com.moutamid.misscaddie.models.RequestsModel;

import java.io.Serializable;
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
        double price = 0.0;
       // holder.name.setText(model.getName());
        holder.address.setText(model.getAddress());
        holder.time.setText(model.getTime());
        holder.date.setText(model.getDate());

        if (model.getStatus_title().equals("Accepted")){
            holder.butn.setBackgroundResource(R.drawable.charm_check);
        } else if (model.getStatus_title().equals("Declined")){
            holder.butn.setBackgroundResource(R.drawable.cross_yellow);
        } else {
            holder.butn.setBackgroundResource(R.drawable.arrow_right_yellow);
        }

        for (int i=0; i < model.getTableRows().size(); i++){
            String service = model.getTableRows().get(i).getTitle() +
                    " (USD$" + model.getTableRows().get(i).getPrice() + ")";
            price = price + Double.parseDouble(model.getTableRows().get(i).getPrice());
            if (i==2){
                serviceList = serviceList + "\t\t" + "more";
                break;
            }
            serviceList = serviceList + "\t\t" + service +  "\n";
        }
        holder.price.setText("(USD$" + price + ")");
        //serviceList = model.getService();
        holder.service_list.setText(serviceList);
        //Glide.with(context).load(model.getImage()).into(holder.image);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Golfer")
                .child(model.getUserId());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Model_Golfer model_golfer = snapshot.getValue(Model_Golfer.class);
                    holder.name.setText(model_golfer.getName());
                    Glide.with(context).load(model_golfer.getImage()).placeholder(R.drawable.bi_person_fill).into(holder.image);

                    holder.butn.setOnClickListener(v -> {
                        Intent i = new Intent(context, CaddieBookingDetailsActivity.class);
                        i.putExtra("personName", model_golfer.getName());
                        i.putExtra("personImage", model_golfer.getImage());
                        i.putExtra("requestModel",model);
                      //  i.putExtra("serviceList", (Serializable) model.getTableRows());

                        context.startActivity(i);
                    });

                    holder.itemView.setOnClickListener(v -> {
                        Intent i = new Intent(context, CaddieBookingDetailsActivity.class);
                        i.putExtra("personName", model_golfer.getName());
                        i.putExtra("personImage", model_golfer.getImage());
                        i.putExtra("requestModel",model);
                        //  i.putExtra("serviceList", (Serializable) model.getTableRows());

                        context.startActivity(i);
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class VH extends RecyclerView.ViewHolder{
        CircleImageView image;
        TextView name, price, date, address,time;
        TextView service_list;
        ImageView butn;

        public VH(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img_golfer);
            name = itemView.findViewById(R.id.name_golfer);
            price = itemView.findViewById(R.id.price_golfer);
            date = itemView.findViewById(R.id.date_golfer);
            time = itemView.findViewById(R.id.time_golfer);
            address = itemView.findViewById(R.id.address_golfer);
            service_list = itemView.findViewById(R.id.service_list);
            butn = itemView.findViewById(R.id.checkBtn);
        }
    }
}
