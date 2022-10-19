package com.moutamid.misscaddie.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.misscaddie.MessagesActivity;
import com.moutamid.misscaddie.R;
import com.moutamid.misscaddie.adapters.CaddieChatListAdapter;
import com.moutamid.misscaddie.adapters.GoflerChatListAdapter;
import com.moutamid.misscaddie.models.Conversation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CaddieHomeFragment extends Fragment {
    TextView welcomeText, datetext, viewCalender;
    BottomNavigationView navigationView;
    ArrayList<Conversation> conversations = new ArrayList<>();
    private DatabaseReference mConversationReference;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private CaddieChatListAdapter mAdapter;
    private RecyclerView recyclerView;

    public CaddieHomeFragment() {
        // Required empty public constructor
    }

    public CaddieHomeFragment(BottomNavigationView navigationView) {
        this.navigationView = navigationView;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_caddie_home, container, false);
        welcomeText = view.findViewById(R.id.text_heading);
        datetext = view.findViewById(R.id.date);
        viewCalender = view.findViewById(R.id.view_cal);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(false);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mConversationReference = FirebaseDatabase.getInstance().getReference().child("conversation")
                .child(user.getUid());

        viewCalender.setOnClickListener(v -> {
            navigationView.setSelectedItemId(R.id.calender_menu);
            FragmentManager.findFragment(view).getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new CaddieCalenderFragment()).commit();
        });

        greetingMessage();
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
                    mAdapter = new CaddieChatListAdapter(getActivity(), conversations);
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void greetingMessage() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        DateFormat df = new DateFormat();
        String d = df.format("dd MMM, yyyy", new Date()).toString();

        datetext.setText(d);

        if(timeOfDay >= 0 && timeOfDay < 12){
            welcomeText.setText("Good Morning");
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            welcomeText.setText("Good Afternoon");
        }else if(timeOfDay >= 16 && timeOfDay < 21){
            welcomeText.setText("Good Evening");
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            welcomeText.setText("Good Night");
        }
    }
}