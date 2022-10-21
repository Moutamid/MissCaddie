package com.moutamid.misscaddie.fragments;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
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
import com.moutamid.misscaddie.adapters.DAPAdapter;
import com.moutamid.misscaddie.adapters.GoflerChatListAdapter;
import com.moutamid.misscaddie.models.Conversation;
import com.moutamid.misscaddie.models.Model_Golfer;
import com.moutamid.misscaddie.models.RequestsModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CaddieHomeFragment extends Fragment {
    TextView welcomeText, datetext, viewCalender;
    BottomNavigationView navigationView;
    ArrayList<Conversation> conversations = new ArrayList<>();
    private DatabaseReference mConversationReference,requestsDb,db;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private CaddieChatListAdapter mAdapter;
    private RecyclerView recyclerView;
    private MaterialCardView cardView1,cardView2,cardView3,cardView4,cardView5,cardView6,cardView7;
    private TextView date1,date2,date3,date4,date5,date6,date7,todayHead;
    private ArrayList<String> availableList = new ArrayList<>();

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
        todayHead = view.findViewById(R.id.head);
        viewCalender = view.findViewById(R.id.view_cal);
        recyclerView = view.findViewById(R.id.recyclerView);
        cardView1 = view.findViewById(R.id.item_card1);
        cardView2 = view.findViewById(R.id.item_card2);
        cardView3 = view.findViewById(R.id.item_card3);
        cardView4 = view.findViewById(R.id.item_card4);
        cardView5 = view.findViewById(R.id.item_card5);
        cardView6 = view.findViewById(R.id.item_card6);
        cardView7 = view.findViewById(R.id.item_card7);
        date1 = view.findViewById(R.id.calender_date1);
        date2 = view.findViewById(R.id.calender_date2);
        date3 = view.findViewById(R.id.calender_date3);
        date4 = view.findViewById(R.id.calender_date4);
        date5 = view.findViewById(R.id.calender_date5);
        date6 = view.findViewById(R.id.calender_date6);
        date7 = view.findViewById(R.id.calender_date7);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(false);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mConversationReference = FirebaseDatabase.getInstance().getReference().child("conversation")
                .child(user.getUid());
        requestsDb = FirebaseDatabase.getInstance().getReference().child("Requests");
        db = FirebaseDatabase.getInstance().getReference().child("Caddie")
                .child(user.getUid()).child("availability");
        viewCalender.setOnClickListener(v -> {
            navigationView.setSelectedItemId(R.id.calender_menu);
            FragmentManager.findFragment(view).getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new CaddieCalenderFragment()).commit();
        });
        getWeeksDate();
        greetingMessage();
        getMessages();
        checkWeekUpdates();
        return view;
    }

    private void checkWeekUpdates() {
        Query query = requestsDb.orderByChild("status_title").equalTo("Accepted");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    availableList.clear();
                    for (DataSnapshot ds : snapshot.getChildren()){
                        RequestsModel model = ds.getValue(RequestsModel.class);
                        if (model.getCaddieId().equals(user.getUid())){
                            db.addValueEventListener(new ValueEventListener() {
                                @RequiresApi(api = Build.VERSION_CODES.M)
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot1) {
                                    if (snapshot1.exists()){
                                        for (DataSnapshot dataSnapshot : snapshot1.getChildren()){
                                            String date = dataSnapshot.child("date").getValue().toString();
                                            //Toast.makeText(getActivity(),date,Toast.LENGTH_LONG).show();
                                            if (model.getDate().equals(date)){
                                                availableList.add(model.getDate());
                                                getHighlightWeeksDate();
                                                String day = model.getDate().substring(0, 2);
                                                Calendar c = Calendar.getInstance();
                                                SimpleDateFormat format = new SimpleDateFormat("dd");
                                                String d = format.format(c.getTime());
                                                if (day.equals(d)){
                                                    getUserData(model.getUserId(),model.getAddress());
                                                }else {
                                                    todayHead.setText("Today: No Booking");
                                                }
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getUserData(String userId, String address) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Golfer")
                .child(userId);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Model_Golfer model_golfer = snapshot.getValue(Model_Golfer.class);
                    todayHead.setText("Today: Booking with "+ model_golfer.getName() + " in " + address);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    private void getHighlightWeeksDate() {
     /*   SimpleDateFormat format = new SimpleDateFormat("dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        String[] days = new String[7];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < availableList.size(); j++){
                days[i] = format.format(calendar.getTime());
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                String available = availableList.get(j);
                String day=available.substring(0,2);
                if (days[i].equals(day)){

                }
            }
        }*/
        for (int j = 0; j < availableList.size(); j++) {
            if (isAdded()) {
                String available = availableList.get(j);
                String day = available.substring(0, 2);

                if (date1.getText().equals(day)) {
                    cardView1.setCardBackgroundColor(getActivity().getColor(R.color.yellow));
                    date1.setTextColor(getResources().getColor(R.color.white));
                }
                if (date2.getText().equals(day)) {
                    cardView2.setCardBackgroundColor(getActivity().getColor(R.color.yellow));
                    date2.setTextColor(getResources().getColor(R.color.white));
                }
                if (date3.getText().equals(day)) {
                    cardView3.setCardBackgroundColor(getActivity().getColor(R.color.yellow));
                    date3.setTextColor(getResources().getColor(R.color.white));
                }
                if (date4.getText().equals(day)) {
                    cardView4.setCardBackgroundColor(getActivity().getColor(R.color.yellow));
                    date4.setTextColor(getResources().getColor(R.color.white));
                }
                if (date5.getText().equals(day)) {
                    cardView5.setCardBackgroundColor(getActivity().getColor(R.color.yellow));
                    date5.setTextColor(getResources().getColor(R.color.white));
                }
                if (date6.getText().equals(day)) {
                    cardView6.setCardBackgroundColor(getActivity().getColor(R.color.yellow));
                    date6.setTextColor(getResources().getColor(R.color.white));
                }
                if (date7.getText().equals(day)) {
                    cardView7.setCardBackgroundColor(getActivity().getColor(R.color.yellow));
                    date7.setTextColor(getResources().getColor(R.color.white));
                }
            }
        }
    }

    private void getWeeksDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        String[] days = new String[7];
        for (int i = 0; i < 7; i++) {
            days[i] = format.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        date1.setText(days[0]);
        date2.setText(days[1]);
        date3.setText(days[2]);
        date4.setText(days[3]);
        date5.setText(days[4]);
        date6.setText(days[5]);
        date7.setText(days[6]);
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