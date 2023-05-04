package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RatingBar;
import android.widget.TextView;

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
import com.moutamid.misscaddie.models.Model_Caddie;
import com.moutamid.misscaddie.models.RequestsModel;
import com.moutamid.misscaddie.models.Review;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Dashboard_Golfer extends AppCompatActivity {

    BottomNavigationView navigationView;
    FrameLayout fragmentLayouts;
  //  private String state,status,date;
    private boolean filter = false;
    private DatabaseReference db,db1,dbs;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
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
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        manager = new SharedPreferencesManager(Dashboard_Golfer.this);
        db = FirebaseDatabase.getInstance().getReference().child("PayPal");
        db1 = FirebaseDatabase.getInstance().getReference().child("Review");
        dbs = FirebaseDatabase.getInstance().getReference().child("Requests");
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
        checkReviews();
  }

    private void checkReviews() {
        Query query = dbs.orderByChild("userId").equalTo(user.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot ds :  snapshot.getChildren()){
                        RequestsModel model = ds.getValue(RequestsModel.class);
                        if (model.getStatus_title().equals("Accepted")){
                            String day = model.getDate().substring(0, 2);
                            Calendar c = Calendar.getInstance();
                            c.add(Calendar.DAY_OF_YEAR, 1);
                            SimpleDateFormat format = new SimpleDateFormat("dd");
                            String d = format.format(c.getTime());
                            if (!day.equals(d) && !model.isReview()){
                                showReviewDialog(model.getCaddieId(),model.getId());
                            }
                        }
                       // Toast.makeText(Dashboard_Golfer.this, day + " and " + d, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    String feedback = "";
    @SuppressLint("MissingInflatedId")
    private void showReviewDialog(String caddieId, String id) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Dashboard_Golfer.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.review_dialog_layout, null);
        RatingBar ratingBar = dialogView.findViewById(R.id.rating);
        EditText mesageTxt = dialogView.findViewById(R.id.labelMessage);
        dialogBuilder.setView(dialogView);
        TextView title = (TextView) dialogView.findViewById(R.id.title);
        TextView content = (TextView) dialogView.findViewById(R.id.content);
        TextView yesBtn = (TextView) dialogView.findViewById(R.id.yes);
        TextView noBtn = (TextView) dialogView.findViewById(R.id.no);
        DatabaseReference dbs = FirebaseDatabase.getInstance().getReference().child("Caddie")
                .child(caddieId);
        dbs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Model_Caddie model_caddie = snapshot.getValue(Model_Caddie.class);
                    title.setText("Do your like \""+ model_caddie.getName() +"\" service");
                    content.setText("Please take a moment and rate "+ model_caddie.getName() +" service.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        AlertDialog alertDialog = dialogBuilder.create();
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat format = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    format = new SimpleDateFormat("MMMM dd, YYYY");
                }
                String d = format.format(c.getTime());
                feedback =mesageTxt.getText().toString();
                String key = db1.push().getKey();
                Review review = new Review(key,caddieId,user.getUid(),ratingBar.getRating(),feedback,d);
                db1.child(user.getUid()).child(key).setValue(review);
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("review",true);
                dbs.child(id).updateChildren(hashMap);
                alertDialog.dismiss();
            }
        });
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void updateRequest() {

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