package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.misscaddie.adapters.GoflerChatListAdapter;
import com.moutamid.misscaddie.models.Conversation;
import com.moutamid.misscaddie.models.IncomingMessageModel;
import com.moutamid.misscaddie.models.Model_Caddie;

import java.util.ArrayList;

public class MessagesActivity extends AppCompatActivity {
    ImageView back_btn, profile_btn;
    TextView myRequest;
    RecyclerView messges_golfer;
    ArrayList<Conversation> conversations = new ArrayList<>();
    private DatabaseReference mConversationReference;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private GoflerChatListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        profile_btn = findViewById(R.id.profiles);
        myRequest = findViewById(R.id.myRequest);
        back_btn = findViewById(R.id.back_btn);
        messges_golfer = findViewById(R.id.messges_golfer);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mConversationReference = FirebaseDatabase.getInstance().getReference().child("conversation")
                .child(user.getUid());
        messges_golfer.setLayoutManager(new LinearLayoutManager(this));
        messges_golfer.setHasFixedSize(false);

        profile_btn.setOnClickListener(v -> {
            Intent intent = new Intent(MessagesActivity.this , GolferProfileEditActivity.class);
            startActivity(intent);
            Animatoo.animateSlideLeft(MessagesActivity.this);
        });

        myRequest.setOnClickListener(v -> {
            Intent intent = new Intent(MessagesActivity.this , CaddiesRequestsActivity.class);
            startActivity(intent);
            Animatoo.animateSlideLeft(MessagesActivity.this);
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MessagesActivity.this,Dashboard_Golfer.class));
                finish();
                Animatoo.animateSlideLeft(MessagesActivity.this);
            }
        });

        getMessages();
    }

    private void getMessages() {
        Query myQuery = mConversationReference.orderByChild("timestamp").limitToFirst(10);
        myQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    conversations.clear();
                    //  mStartChatLayout.setVisibility(View.GONE);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Conversation conversation = snapshot.getValue(Conversation.class);
                        // if (!conversation.isArchive()) {
                        conversations.add(conversation);
                        //}
                    }
                    mAdapter = new GoflerChatListAdapter(MessagesActivity.this, conversations);
                    messges_golfer.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}