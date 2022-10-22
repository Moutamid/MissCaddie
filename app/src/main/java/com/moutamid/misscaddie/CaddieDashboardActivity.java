package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.moutamid.misscaddie.Notifications.Token;
import com.moutamid.misscaddie.fragments.CaddieCalenderFragment;
import com.moutamid.misscaddie.fragments.CaddieHomeFragment;
import com.moutamid.misscaddie.fragments.CaddieListFragment;
import com.moutamid.misscaddie.fragments.CaddieProfileFragment;

public class CaddieDashboardActivity extends AppCompatActivity {

    BottomNavigationView navigationView;
    FrameLayout fragmentLayouts;
    CaddieCalenderFragment calenderFragment;

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