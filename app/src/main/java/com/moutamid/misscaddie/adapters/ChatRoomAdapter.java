package com.moutamid.misscaddie.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.misscaddie.R;
import com.moutamid.misscaddie.models.Chat;
import com.moutamid.misscaddie.models.Model_Caddie;
import com.moutamid.misscaddie.models.Model_Golfer;

import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatRoomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TYPE_TIME = 0;
    private final int TYPE_INCOMING = 1;
    private final int TYPE_OUTGOING = 2;
    List<Chat> chatList;
    private Context mContext;
    String fname = "";
    int[] types = {TYPE_TIME,TYPE_INCOMING,TYPE_OUTGOING};
    private String mode;


    public ChatRoomAdapter(Context context, List<Chat> chats, String mode) {
        this.mContext = context;
        this.chatList = chats;
        this.mode = mode;
    }

    @Override
    public int getItemViewType(int position) {
        Chat chat = chatList.get(position);

       /* if (position == 0){
            return TYPE_TIME;
        }*/
        return tes(chat);
      }

    private int tes(Chat chat) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser().getUid().equalsIgnoreCase(chat.getSenderUid())) {
            return TYPE_OUTGOING;
        }
        return TYPE_INCOMING;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if(viewType == TYPE_INCOMING){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_recive_layout, parent, false);
            return new IncomingViewHolder(view);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_send_layout, parent, false);
            return new OutgoingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_INCOMING) {
            IncomingViewHolder holder1 = (IncomingViewHolder) holder;
            configureViewHolderIncoming(holder1, position);
        }  else{
            OutgoingViewHolder holder2 = (OutgoingViewHolder) holder;
            configureViewholderOutgoing(holder2, position);
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    private void configureViewHolderIncoming(final IncomingViewHolder holder, int position) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        Chat chat = (Chat) chatList.get(position);
        if (chat != null) {

            getProfilePic(chat.getSenderUid(),chat.getMode(), holder.imageView);
          //  if (chat.getReplyId() == 0) {

                if (chat.getType().equals("text")) {
                    holder.message.setVisibility(View.VISIBLE);
                 //   holder.iuploadImg.setVisibility(View.GONE);
                    holder.message.setText(chat.getMessage());
                }
            calculateTimeAgo(chat.getTimestamp(),holder.time);
        }

    }

    private void getProfilePic(String receiverUid, String mode, CircleImageView imageView) {

      //  Toast.makeText(mContext,receiverUid,Toast.LENGTH_LONG).show();
        if (mode.equals("caddie")) {
            DatabaseReference mUserReference = FirebaseDatabase.getInstance().getReference()
                    .child("Caddie").child(receiverUid);
            mUserReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot ds) {
                    if (ds.exists()) {
                        Model_Caddie model = ds.getValue(Model_Caddie.class);
                        /*if (model.getImage().equals("")) {
                            Glide.with(mContext)
                                    .load(R.drawable.img1)
                                    .into(imageView);
                        } else {
                            Glide.with(mContext)
                                    .load(model.getImage())
                                    .into(imageView);
                        }*/

                        Glide.with(mContext)
                                .load(model.getImage())
                                .placeholder(R.drawable.logo_green)
                                .into(imageView);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else {
            DatabaseReference mUserReference = FirebaseDatabase.getInstance().getReference()
                    .child("Golfer").child(receiverUid);
            mUserReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot ds) {
                    if (ds.exists()) {
                        Model_Golfer model = ds.getValue(Model_Golfer.class);
                        /*if (model.getImage().equals("")) {
                            Glide.with(mContext)
                                    .load(R.drawable.img1)
                                    .into(imageView);
                        } else {
                            Glide.with(mContext)
                                    .load(model.getImage())
                                    .into(imageView);
                        }*/

                        Glide.with(mContext)
                                .load(model.getImage())
                                .placeholder(R.drawable.logo_yellow)
                                .into(imageView);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void configureViewholderOutgoing(final OutgoingViewHolder holder, int position) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userUid = mAuth.getCurrentUser().getUid();
        Chat chat = (Chat) chatList.get(position);
        if (chat != null) {
            //if (chat.getReplyId() == 0) {

               // holder.reply_layout.setVisibility(View.GONE);
                if (chat.getType().equals("text")) {

                    holder.message.setVisibility(View.VISIBLE);
                    holder.message.setText(chat.getMessage());
                    //checkOutgoingReply(chat,holder);
                }
                calculateTimeAgo(chat.getTimestamp(),holder.time);
        }
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
    public class IncomingViewHolder extends RecyclerView.ViewHolder {
        private TextView message;
        private TextView time;
        private CircleImageView imageView;

        public IncomingViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.personImage);
            message = itemView.findViewById(R.id.recive_message);
            time = itemView.findViewById(R.id.dateDay);

        }

    }


    public class OutgoingViewHolder extends RecyclerView.ViewHolder {
        private TextView message;
        private TextView time;

        public OutgoingViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.send_message);
            time = itemView.findViewById(R.id.dateDay);
        }
    }


    public void removeAt(int position){
        chatList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,chatList.size());
    }

    public void clearChat(){
        int size = chatList.size();
        chatList.clear();
        notifyItemRangeRemoved(0,size);
    }

}
