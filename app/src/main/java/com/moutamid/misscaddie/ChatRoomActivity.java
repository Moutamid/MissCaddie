package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.misscaddie.adapters.ChatRoomAdapter;
import com.moutamid.misscaddie.databinding.ActivityChatRoomBinding;
import com.moutamid.misscaddie.models.Chat;
import com.moutamid.misscaddie.models.Conversation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ChatRoomActivity extends AppCompatActivity {

    private ActivityChatRoomBinding b;
    private DatabaseReference mChatReference,mConversationReference;
    private ChatRoomAdapter adapters;
    public String userUid,mode,id,name;
    FirebaseAuth mAuth;
    FirebaseUser user;
    private int unreadCount = 0;
    private List<Chat> chatList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        id = getIntent().getStringExtra("uId");
        mode = getIntent().getStringExtra("mode");
        name = getIntent().getStringExtra("name");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userUid = user.getUid();
        mChatReference = FirebaseDatabase.getInstance().getReference().child("chats");
        mConversationReference = FirebaseDatabase.getInstance().getReference().child("conversation");
        b.nameTxt.setText(name);
        changeStatusBarColor(this,R.color.yellow);
        b.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mode.equals("caddie")){
                    startActivity(new Intent(ChatRoomActivity.this,CaddieDashboardActivity.class));
                    Animatoo.animateSlideLeft(ChatRoomActivity.this);
                    finish();
                }else {
                    startActivity(new Intent(ChatRoomActivity.this,MessagesActivity.class));
                    finish();
                    Animatoo.animateSlideLeft(ChatRoomActivity.this);
                }
            }
        });
        b.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contoh = b.message.getText().toString();
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

        b.message.setText("");
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
                            b.messgesGolfer.smoothScrollToPosition(chatList.size() - 1);
                            b.messgesGolfer.setAdapter(adapters);
                            adapters.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }


}