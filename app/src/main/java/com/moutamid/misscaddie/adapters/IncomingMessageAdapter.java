package com.moutamid.misscaddie.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.moutamid.misscaddie.R;
import com.moutamid.misscaddie.models.IncomingMessageModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class IncomingMessageAdapter extends RecyclerView.Adapter<IncomingMessageAdapter.IncomingMessageVH> {

    Context context;
    ArrayList<IncomingMessageModel> list;

    public IncomingMessageAdapter(Context context, ArrayList<IncomingMessageModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public IncomingMessageVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.incoming_message_layout, parent, false);
        return new IncomingMessageAdapter.IncomingMessageVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomingMessageVH holder, int position) {
        IncomingMessageModel model = list.get(position);
        holder.name.setText(model.getName());
        holder.last_message.setText(model.getLast_message());
        holder.date.setText(model.getDate());
        Glide.with(context).load(model.getImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class IncomingMessageVH extends RecyclerView.ViewHolder{
        TextView name, last_message, date;
        CircleImageView imageView;

        public IncomingMessageVH(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.personName);
            last_message = itemView.findViewById(R.id.lastMessage);
            date = itemView.findViewById(R.id.date);
            imageView = itemView.findViewById(R.id.profile_img);
        }
    }
}
