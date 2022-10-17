package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
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


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CaddieHomeFragment(navigationView)).commit();

        navigationView.setSelectedItemId(R.id.home_menu);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_menu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CaddieHomeFragment(navigationView)).commit();
                        break;
                    case R.id.list_menu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CaddieListFragment()).commit();
                        break;
                    case R.id.calender_menu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CaddieCalenderFragment()).commit();
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