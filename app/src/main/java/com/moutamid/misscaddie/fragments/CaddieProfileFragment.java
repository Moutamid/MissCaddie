package com.moutamid.misscaddie.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.moutamid.misscaddie.CaddieEditProfileActivity;
import com.moutamid.misscaddie.CaddieProServicesActivity;
import com.moutamid.misscaddie.MainActivity;
import com.moutamid.misscaddie.R;
import com.moutamid.misscaddie.SharedPreferencesManager;

import java.util.Calendar;
import java.util.Date;

public class CaddieProfileFragment extends Fragment {
    CardView profileBtn, serviceBtn, privacybtn, termsbtn,logoutBtn;
    TextView welcomeText, datetext;
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleSignInClient;
    private SharedPreferencesManager manager;

    public CaddieProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_caddie_profile, container, false);

        profileBtn = view.findViewById(R.id.profilebtn);
        serviceBtn = view.findViewById(R.id.servicebtn);
        termsbtn = view.findViewById(R.id.termsbtn);
        privacybtn = view.findViewById(R.id.privacybtn);
        welcomeText = view.findViewById(R.id.text_heading);
        datetext = view.findViewById(R.id.date);
        logoutBtn = view.findViewById(R.id.logoutbtn);
        greetingMessage();
        manager = new SharedPreferencesManager(getActivity());
        mAuth = FirebaseAuth.getInstance();
        termsbtn.setOnClickListener(v -> {
            Uri webpage = Uri.parse("https://www.google.com");
            Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
            try {
                startActivity(webIntent);
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        privacybtn.setOnClickListener(v -> {
            Uri webpage = Uri.parse("https://www.google.com");
            Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
            try {
                startActivity(webIntent);
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        profileBtn.setOnClickListener(v -> {
            startActivity(new Intent(getActivity().getApplicationContext(), CaddieEditProfileActivity.class));
        });
        serviceBtn.setOnClickListener(v -> {
            startActivity(new Intent(getActivity().getApplicationContext(), CaddieProServicesActivity.class));
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager.storeString("module","");
                mAuth.signOut();
                Auth.GoogleSignInApi.signOut(mGoogleSignInClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {

                    }
                });
                mGoogleSignInClient.disconnect();
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });

        return view;
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