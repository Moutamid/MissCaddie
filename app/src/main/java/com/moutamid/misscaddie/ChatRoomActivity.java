package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.misscaddie.adapters.ChatRoomAdapter;
import com.moutamid.misscaddie.models.Chat;
import com.moutamid.misscaddie.models.Conversation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ChatRoomActivity extends AppCompatActivity {

    private DatabaseReference mChatReference,mConversationReference;
    private ChatRoomAdapter adapters;
    public String userUid,mode,id,name;
    FirebaseAuth mAuth;
    FirebaseUser user;
    private int unreadCount = 0;
    private View top1,top2;
    private LinearLayout bottom;
    private RecyclerView messgesGolfer;
    private EditText messageTxt;
    private ImageView sendBtn,backBtn;
    private TextView nameTxt;
    private List<Chat> chatList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        id = getIntent().getStringExtra("uId");
        mode = getIntent().getStringExtra("mode");
        name = getIntent().getStringExtra("name");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userUid = user.getUid();
        top1 = findViewById(R.id.top1);
        top2 = findViewById(R.id.top2);
        bottom = findViewById(R.id.bottom);
        messageTxt = findViewById(R.id.message);
        messgesGolfer = findViewById(R.id.messges_golfer);
        sendBtn = findViewById(R.id.send);
        backBtn = findViewById(R.id.back_btn);
        nameTxt = findViewById(R.id.nameTxt);
        mChatReference = FirebaseDatabase.getInstance().getReference().child("chats");
        mConversationReference = FirebaseDatabase.getInstance().getReference().child("conversation");
        nameTxt.setText(name);
        if (mode.equals("caddie")){
            changeStatusBarColor(this,R.color.yellow);
            top1.setBackground(getDrawable(R.drawable.circle_yellow1));
            top2.setBackground(getDrawable(R.drawable.circle_yellow1));
            bottom.setBackground(getDrawable(R.drawable.message_back_yellow));
        }else {
            changeStatusBarColor(this,R.color.green);

            top1.setBackground(getDrawable(R.drawable.circle_green1));
            top2.setBackground(getDrawable(R.drawable.circle_green1));
            bottom.setBackground(getDrawable(R.drawable.message_back_green));
        }
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mode.equals("caddie")){
                    startActivity(new Intent(ChatRoomActivity.this,CaddieDashboardActivity.class));
                    Animatoo.animateSlideLeft(ChatRoomActivity.this);
                    finish();
                }else {
                    startActivity(new Intent(ChatRoomActivity.this,Dashboard_Golfer.class));
                    finish();
                    Animatoo.animateSlideLeft(ChatRoomActivity.this);
                }
            }
        });
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contoh = messageTxt.getText().toString();
                if (!TextUtils.isEmpty(contoh)) {
                    long timestamp = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
                    sentChat("text",contoh,timestamp);
                }

            }
        });
        getChatData();
    }

    private void sentChat(String type, String contoh, long timestamp) {
        Chat chatReciever = new Chat(type,mode,contoh, user.getUid(), id, timestamp, unreadCount);

        Conversation conversationSender = new Conversation(type, user.getUid(), id, contoh,
                timestamp, 0);
        Conversation conversationReceiver = new Conversation(type, id, user.getUid(), contoh,
                timestamp, unreadCount);

        DatabaseReference senderReference = mConversationReference.child(user.getUid()).child(id);
        senderReference.setValue(conversationSender);
        DatabaseReference receiverReference = mConversationReference.child(id).child(user.getUid());
        receiverReference.setValue(conversationReceiver);


        DatabaseReference senderReference1 = mChatReference.child(user.getUid()).child(id);
        senderReference1.child(String.valueOf(timestamp)).setValue(chatReciever);
        DatabaseReference receiverReference1 = mChatReference.child(id).child(user.getUid());
        receiverReference1.child(String.valueOf(timestamp)).setValue(chatReciever);

        messageTxt.setText("");
    }


    public void changeStatusBarColor(Activity activity, int id) {

        // Changing the color of status bar
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(activity.getResources().getColor(id));
        }

        // CHANGE STATUS BAR TO TRANSPARENT
        //window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
    private void getChatData() {
        mChatReference.child(userUid).child(id).orderByChild("timestamp")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            chatList.clear();
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                Chat chat = ds.getValue(Chat.class);
                                chatList.add(chat);
                            }

                            adapters = new ChatRoomAdapter(ChatRoomActivity.this, chatList,mode);
                            messgesGolfer.smoothScrollToPosition(chatList.size() - 1);
                            messgesGolfer.setAdapter(adapters);
                            adapters.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }


}