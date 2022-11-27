package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.misscaddie.Dashboard_Golfer;
import com.moutamid.misscaddie.R;
import com.moutamid.misscaddie.adapters.CaddieProfileVPadapter;
import com.moutamid.misscaddie.fragments.AcceptedFragment;
import com.moutamid.misscaddie.fragments.DeclinedFragment;
import com.moutamid.misscaddie.fragments.MyRequestsFragment;
import com.moutamid.misscaddie.fragments.PaymentRequestFragment;
import com.moutamid.misscaddie.fragments.PendingFragment;
import com.moutamid.misscaddie.models.RequestsModel;
import com.moutamid.misscaddie.models.ServiceListModel;
import com.moutamid.misscaddie.adapters.RequestesAdapter;

import java.util.ArrayList;
import java.util.List;

public class CaddiesRequestsActivity extends Fragment {

    private Bundle savedState = null;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_caddies_requests, container, false);

        tabLayout = view.findViewById(R.id.tabsLayout);
        viewPager = view.findViewById(R.id.ProfileViewpager);
        if(savedInstanceState != null && savedState == null) {
            savedState = savedInstanceState.getBundle("state");
        }
        if(savedState != null) {
            refresh();
        }
        savedState = null;
        return view;
    }

    private void refresh() {
        if (isAdded()) {
            CaddieProfileVPadapter caddieProfileVPadapter = new CaddieProfileVPadapter(getChildFragmentManager());
            caddieProfileVPadapter.addFragment(new MyRequestsFragment(), "My Requests");
            caddieProfileVPadapter.addFragment(new PaymentRequestFragment(), "Payment Requests");

            viewPager.setAdapter(caddieProfileVPadapter);
            tabLayout.setupWithViewPager(viewPager);
        }
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

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    private Bundle saveState() { /* called either from onDestroyView() or onSaveInstanceState() */
        Bundle state = new Bundle();
        state.putCharSequence("state", "fragment");
        return state;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle("state", (savedState != null) ? savedState : saveState());
    }

}