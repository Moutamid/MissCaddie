package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class MessagesActivity extends Fragment {
    RecyclerView messges_golfer;
    ArrayList<Conversation> conversations = new ArrayList<>();
    private DatabaseReference mConversationReference;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private GoflerChatListAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_messages, container, false);

        messges_golfer = view.findViewById(R.id.messges_golfer);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mConversationReference = FirebaseDatabase.getInstance().getReference().child("conversation")
                .child(user.getUid());
        messges_golfer.setLayoutManager(new LinearLayoutManager(getActivity()));
        messges_golfer.setHasFixedSize(false);

        getMessages();
        return view;
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
                    mAdapter = new GoflerChatListAdapter(getActivity(), conversations);
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