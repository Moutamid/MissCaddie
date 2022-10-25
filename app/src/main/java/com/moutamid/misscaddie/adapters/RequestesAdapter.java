package com.moutamid.misscaddie.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.misscaddie.CaddieBookingDetailsActivity;
import com.moutamid.misscaddie.R;
import com.moutamid.misscaddie.models.Model_Golfer;
import com.moutamid.misscaddie.models.RequestsModel;

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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        RequestsModel model = itemList.get(position);
        String serviceList = "";

      //  holder.name.setText(model.getName());
        holder.price.setText("(USD$" + model.getTableRows().get(0).getPrice() + ")");
        holder.address.setText(model.getAddress());
        holder.date.setText(model.getDate());
        holder.time.setText(model.getTime());
        holder.status_title.setText("(" + model.getStatus_title() + ")");

        if (model.getStatus_title().equals("Accepted")){
            holder.status_title.setTextColor(context.getResources().getColor(R.color.green));
            holder.butn.setBackgroundResource(R.drawable.ic_charm_tick1);
        } else if (model.getStatus_title().equals("Declined")){
            holder.status_title.setTextColor(context.getResources().getColor(R.color.red));
            holder.butn.setBackgroundResource(R.drawable.ic_charm_cross);
        } else {
            holder.status_title.setTextColor(context.getResources().getColor(R.color.black_light));
            holder.butn.setBackgroundResource(0);
        }

        for (int i=0; i < model.getTableRows().size(); i++){
            String service = model.getTableRows().get(i).getTitle() + " (USD$" + model.getTableRows().get(i).getPrice() + ")";
            if (i==2){
                serviceList = serviceList + "\t\t" + "more";
                break;
            }
            serviceList = serviceList + "\t\t" + service +  "\n";
        }
        //serviceList = model.getService();
        holder.service_list.setText(serviceList);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Caddie")
                .child(model.getCaddieId());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Model_Golfer model_golfer = snapshot.getValue(Model_Golfer.class);
                    holder.name.setText(model_golfer.getName());
                    Glide.with(context).load(model_golfer.getImage()).placeholder(R.drawable.bi_person_fill).into(holder.image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Glide.with(context).load(model.getImage()).into(holder.image);


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class VH extends RecyclerView.ViewHolder{
        CircleImageView image;
        TextView name, price, status_title, date, address,time;
        TextView service_list;
        ImageView butn;

        public VH(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img_golfer);
            name = itemView.findViewById(R.id.name_golfer);
            price = itemView.findViewById(R.id.price_golfer);
            status_title = itemView.findViewById(R.id.status_golfer);
            date = itemView.findViewById(R.id.date_golfer);
            time = itemView.findViewById(R.id.time_golfer);
            address = itemView.findViewById(R.id.address_golfer);
            service_list = itemView.findViewById(R.id.service_list);
            butn = itemView.findViewById(R.id.checkBtn);
        }
    }
}
