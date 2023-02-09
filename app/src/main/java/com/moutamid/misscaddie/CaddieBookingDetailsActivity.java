package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.misscaddie.Notifications.Client;
import com.moutamid.misscaddie.Notifications.Data;
import com.moutamid.misscaddie.Notifications.MyResponse;
import com.moutamid.misscaddie.Notifications.Sender;
import com.moutamid.misscaddie.Notifications.Token;
import com.moutamid.misscaddie.listners.APIService;
import com.moutamid.misscaddie.models.RequestsModel;
import com.moutamid.misscaddie.models.ServiceListModel;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CaddieBookingDetailsActivity extends AppCompatActivity {

    private RequestsModel model;
    private String name,image;
    private DatabaseReference requestsDb;
    private FirebaseAuth mAuth;
    private APIService apiService;
    private FirebaseUser user;
    private String serviceList = "";
    private TextView nameTxt,locationtxt,totalPrice,dateTxt,timeTxt,messageTxt,acceptBtn,declineBtn,services;
    private ImageView backBtn;
    private CircleImageView profileImg;
    private double price = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caddie_booking_details);
        nameTxt = findViewById(R.id.name);
        locationtxt = findViewById(R.id.place);
        totalPrice = findViewById(R.id.totalPrice);
        dateTxt = findViewById(R.id.date);
        timeTxt = findViewById(R.id.time);
        messageTxt = findViewById(R.id.message);
        acceptBtn = findViewById(R.id.acceptBtn);
        declineBtn = findViewById(R.id.declineBtn);
        services = findViewById(R.id.service_list);
        backBtn = findViewById(R.id.back_btn);
        profileImg = findViewById(R.id.profile_img);
        model = getIntent().getParcelableExtra("requestModel");
        name = getIntent().getStringExtra("personName");
        image = getIntent().getStringExtra("personImage");
        apiService = Client.getRetrofit("https://fcm.googleapis.com/").create(APIService.class);
        backBtn.setOnClickListener(v -> {
            startActivity(new Intent(CaddieBookingDetailsActivity.this,CaddieDashboardActivity.class));
            finish();
            Animatoo.animateSwipeLeft(CaddieBookingDetailsActivity.this);
        });
        requestsDb = FirebaseDatabase.getInstance().getReference().child("Requests");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        nameTxt.setText(name);
        Glide.with(CaddieBookingDetailsActivity.this)
                .load(image)
                .placeholder(R.drawable.bi_person_fill)
                .into(profileImg);
        
        dateTxt.setText(model.getDate());
        timeTxt.setText(model.getTime());
        getServices();

        /*for (int i=0; i < model.getTableRows().size(); i++){
            String service = model.getTableRows().get(i).getTitle() +
                    " (USD$" + model.getTableRows().get(i).getPrice() + ")";
            price = price + Integer.parseInt(model.getTableRows().get(i).getPrice());
            if (i==2){
                serviceList = serviceList + "\t\t" + "more";
                break;
            }
            serviceList = serviceList + "\t\t" + service +  "\n";
        }*/
        locationtxt.setText(model.getAddress());
        messageTxt.setText(model.getMessage());
        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptBooking();
            }
        });
        declineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                declineBooking();
            }
        });
        changeStatusBarColor(this,R.color.yellow);
    }

    private void getServices() {
        requestsDb.child(model.getId()).child("tableRows")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot ds : snapshot.getChildren()){
                        ServiceListModel serviceListModel  = ds.getValue(ServiceListModel.class);
                        String service = serviceListModel.getTitle() +
                                " (USD$" + serviceListModel.getPrice() + ")";
                        price = price + Double.parseDouble(serviceListModel.getPrice());
                        serviceList = serviceList + "\t\t" + service +  "\n";
                    }

                    services.setText(serviceList);
                    totalPrice.setText("Total : US$ " + price);
                 //   Toast.makeText(CaddieBookingDetailsActivity.this, "Exists", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

    private void declineBooking() {
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("status_title","Decline");
        requestsDb.child(model.getId()).updateChildren(hashMap);

        startActivity(new Intent(CaddieBookingDetailsActivity.this,CaddieDashboardActivity.class));
        finish();
        Animatoo.animateZoom(CaddieBookingDetailsActivity.this);
        sendNotification(model.getUserId(),"Your request has been declined!");
    }

    private void acceptBooking() {
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("status_title","payment_request");
        //hashMap.put("payment",false);
        requestsDb.child(model.getId()).updateChildren(hashMap);

        startActivity(new Intent(CaddieBookingDetailsActivity.this,CaddieDashboardActivity.class));
        finish();
        Animatoo.animateZoom(CaddieBookingDetailsActivity.this);
        sendNotification(model.getUserId(),"Your request has been accepted!");
    }

    private void sendNotification(String uId, String message) {
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(uId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Token token = snapshot.getValue(Token.class);
                    Data data = new Data("Golfer",user.getUid(), R.mipmap.ic_launcher, message,
                            "Request Update", uId);

                    Sender sender = new Sender(data, token.getToken());

                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if(response.code() == 200){
                                        if (response.body().success != 1){
                                            System.out.println("Failed to send notification!");
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {

                                }
                            });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}