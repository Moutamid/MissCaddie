package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.moutamid.misscaddie.Notifications.Token;
import com.moutamid.misscaddie.fragments.GolferHomeFragment;

import java.util.ArrayList;

public class Dashboard_Golfer extends AppCompatActivity {

    BottomNavigationView navigationView;
    FrameLayout fragmentLayouts;
  //  private String state,status,date;
    private boolean filter = false;
    private DatabaseReference db;
    private SharedPreferencesManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_golfer);
        navigationView = findViewById(R.id.bottomNavigation);
        fragmentLayouts = findViewById(R.id.fragment_container);
        navigationView.setItemIconTintList(null);
    //    state = getIntent().getStringExtra("state");
      //  status = getIntent().getStringExtra("status");
        //date = getIntent().getStringExtra("date");
        filter = getIntent().getBooleanExtra("filter",false);
        manager = new SharedPreferencesManager(Dashboard_Golfer.this);
        db = FirebaseDatabase.getInstance().getReference().child("PayPal");
        if (filter){
            GolferHomeFragment fragment = new GolferHomeFragment();
            Bundle b = new Bundle();
            //b.putString("state",state);
            //b.putString("status",status);
           // b.putString("date",date);
            b.putBoolean("filter",filter);
            fragment.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    fragment).commit();
        }else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new GolferHomeFragment()).commit();
        }

        navigationView.setSelectedItemId(R.id.home_menu);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_menu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new GolferHomeFragment()).commit();
                        break;
                    case R.id.message_menu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new MessagesActivity()).commit();
                        break;
                    case R.id.request_menu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new CaddiesRequestsActivity()).commit();
                        break;
                    case R.id.profile_menu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                                , new GolferProfileEditActivity()).commit();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(!task.isSuccessful()){
                    System.out.println("Fetching FCM registration token failed ");
                    return;
                }

                String token = task.getResult();
                updatetoken(token);
                // Toast.makeText(DashBoard.this,token,Toast.LENGTH_LONG).show();
            }
        });
        Constants.checkApp(Dashboard_Golfer.this);
        getStripeDetails();

    }

    private void getStripeDetails() {
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String clientId = snapshot.child("clientId").getValue().toString();
                    String clientSecret = snapshot.child("clientSecret").getValue().toString();
                    manager.storeString("clientId",clientId);
                    manager.storeString("clientSecret",clientSecret);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updatetoken(String token) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Tokens");
        Token uToken = new Token(token);
        db.child(firebaseUser.getUid()).setValue(uToken);
    }


}