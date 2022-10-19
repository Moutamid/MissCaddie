package com.moutamid.misscaddie.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.misscaddie.ChatRoomActivity;
import com.moutamid.misscaddie.R;
import com.moutamid.misscaddie.models.Conversation;
import com.moutamid.misscaddie.models.Model_Caddie;
import com.moutamid.misscaddie.models.Model_Golfer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

public class CaddieChatListAdapter extends RecyclerView.Adapter<CaddieChatListAdapter.IncomingMessageVH> {

    Context context;
    ArrayList<Conversation> list;

    public CaddieChatListAdapter(Context context, ArrayList<Conversation> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public IncomingMessageVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.incoming_message_layout, parent, false);
        return new IncomingMessageVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomingMessageVH holder, int position) {
        Conversation model = list.get(position);
        holder.last_message.setText(model.getLastMessage());
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Golfer").child(model.getChatWithId());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Model_Golfer model_caddie = snapshot.getValue(Model_Golfer.class);
                    holder.name.setText(model_caddie.getName());
                    Glide.with(context)
                            .load(model_caddie.getImage())
                            .placeholder(R.drawable.img3)
                            .into(holder.imageView);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, ChatRoomActivity.class);
                            intent.putExtra("uId",model.getChatWithId());
                            intent.putExtra("name",model_caddie.getName());
                            intent.putExtra("mode","caddie");
                            context.startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        calculateTimeAgo(model.getTimestamp(),holder.date);

    }

    private void calculateTimeAgo(long timestamp, TextView chatTime) {
        Date timeD = new Date(timestamp * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = sdf.format(timeD);
        try {
            long time = sdf.parse(date).getTime();
            long now = System.currentTimeMillis();
            CharSequence ago =
                    DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
            chatTime.setText(ago);
        } catch (ParseException e) {
            e.printStackTrace();
        }

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
