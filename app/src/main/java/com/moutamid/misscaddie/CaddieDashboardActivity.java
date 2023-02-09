package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.moutamid.misscaddie.Notifications.Token;
import com.moutamid.misscaddie.fragments.CaddieCalenderFragment;
import com.moutamid.misscaddie.fragments.CaddieHomeFragment;
import com.moutamid.misscaddie.fragments.CaddieListFragment;
import com.moutamid.misscaddie.fragments.CaddieProfileFragment;
import com.moutamid.misscaddie.models.Model_Caddie;

public class CaddieDashboardActivity extends AppCompatActivity {

    BottomNavigationView navigationView;
    FrameLayout fragmentLayouts;
    CaddieCalenderFragment calenderFragment;
    private FirebaseAuth mAuth;
    private FirebaseUser currrentUser;
    private DatabaseReference db,db1;
    private SharedPreferencesManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caddie_dashboard);
        navigationView = findViewById(R.id.bottomNavigation);
        fragmentLayouts = findViewById(R.id.fragment_container);
        navigationView.setItemIconTintList(null);

        calenderFragment  = new CaddieCalenderFragment();
        changeStatusBarColor(this,R.color.yellow);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CaddieHomeFragment(navigationView)).commit();

        manager = new SharedPreferencesManager(CaddieDashboardActivity.this);

        mAuth = FirebaseAuth.getInstance();
        currrentUser = mAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference().child("Caddie");
        db1 = FirebaseDatabase.getInstance().getReference().child("Stripe");
        navigationView.setSelectedItemId(R.id.home_menu);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_menu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new CaddieHomeFragment(navigationView)).commit();
                        break;
                    case R.id.list_menu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CaddieListFragment()).commit();
                        break;
                    case R.id.calender_menu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                calenderFragment).commit();
                        break;
                    case R.id.profile_menu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CaddieProfileFragment()).commit();
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

        checkIfExists();
        //getStripeDetails();

    }

    private void getStripeDetails() {
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String apiKey = snapshot.child("api_key").getValue().toString();
                    String pubKey = snapshot.child("publisher_key").getValue().toString();
                    manager.storeString("apiKey",apiKey);
                    manager.storeString("publisherKey",pubKey);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void checkIfExists() {
        db.child(currrentUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Model_Caddie model_caddie = snapshot.getValue(Model_Caddie.class);
                            if (model_caddie.getCatagory().equals("") || model_caddie.getPhone().equals("")
                                    || model_caddie.getFeet() == 0 || model_caddie.getInches() == 0 || model_caddie.getStatus().equals("")
                            || model_caddie.getState().equals("")){

                                Intent intent = new Intent(CaddieDashboardActivity.this , CaddieDeatilsActivity.class);
                                startActivity(intent);
                                finish();
                                Animatoo.animateZoom(CaddieDashboardActivity.this);
                            }
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

    /**
     * Errors TODO
     * */

    public void JanuaryDatesClick(View v) {
        calenderFragment.JanuaryDatesClick(v);
    }

    public void FebruaryDatesClick(View v) {
        calenderFragment.FebruaryDatesClick(v);
    }

    public void MarchDatesClick(View v) {
        calenderFragment.MarchDatesClick(v);
    }

    public void AprilDatesClick(View v) {
        calenderFragment.AprilDatesClick(v);
    }

    public void MayDatesClick(View v) {
        calenderFragment.MayDatesClick(v);
    }

    public void JuneDatesClick(View v) {
        calenderFragment.JuneDatesClick(v);
    }
    public void JulyDatesClick(View v) {
        calenderFragment.JulyDatesClick(v);
    }

    public void AugustDatesClick(View v) {
        calenderFragment.AugustDatesClick(v);
    }

    public void SeptemberDatesClick(View v) {
        calenderFragment.SeptemberDatesClick(v);
    }
    public void OctoberDatesClick(View v) {
        calenderFragment.OctoberDatesClick(v);
    }

    public void NovemberDatesClick(View v) {
        calenderFragment.NovemberDatesClick(v);
    }

    public void DecemberDatesClick(View v) {
        calenderFragment.DecemberDatesClick(v);
    }

}