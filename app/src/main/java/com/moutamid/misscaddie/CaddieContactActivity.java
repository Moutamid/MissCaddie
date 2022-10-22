package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.moutamid.misscaddie.Notifications.Client;
import com.moutamid.misscaddie.Notifications.Data;
import com.moutamid.misscaddie.Notifications.MyResponse;
import com.moutamid.misscaddie.Notifications.Sender;
import com.moutamid.misscaddie.Notifications.Token;
import com.moutamid.misscaddie.listners.APIService;
import com.moutamid.misscaddie.models.Chat;
import com.moutamid.misscaddie.models.Conversation;
import com.moutamid.misscaddie.models.RequestsModel;
import com.moutamid.misscaddie.models.ServiceListModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CaddieContactActivity extends AppCompatActivity {
    ImageView backBtn;
    TextView booking_dates,sendBtn;
    EditText et_location,messageTxt;
    private Spinner serviceSpinner;
    private ArrayAdapter<String> arrayAdapter;
    private String uId;
    private DatabaseReference requestsDb,caddie_db,mConversationReference,mChatReference;
    private ArrayList<String> serviceListModels;
    private int unreadCount = 0;
    private String service = "";
    private String place = "";
    private String date = "";
    private String message = "";
    private String price = "";
    private FirebaseAuth mAuth;
    private LocationListener locationListener;
    private double latitude = 0.0;
    private double longitude = 0.0;
    FirebaseUser user;
    private APIService apiService;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caddie_contact);
        backBtn = findViewById(R.id.back_btn);
        booking_dates = findViewById(R.id.booking_dates);
        et_location = findViewById(R.id.location);
        messageTxt = findViewById(R.id.message);
        serviceSpinner = findViewById(R.id.spinner_booking);
        sendBtn = findViewById(R.id.sendRequest);
        uId = getIntent().getStringExtra("userId");
        apiService = Client.getRetrofit("https://fcm.googleapis.com/").create(APIService.class);
        requestsDb = FirebaseDatabase.getInstance().getReference().child("Requests");
        mChatReference = FirebaseDatabase.getInstance().getReference().child("chats");
        mConversationReference = FirebaseDatabase.getInstance().getReference().child("conversation");
        caddie_db = FirebaseDatabase.getInstance().getReference().child("Caddie");
        serviceListModels = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    //    changeStatusBarColor(this,R.color.yellow);
        MaterialDatePicker.Builder<Long> materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("SELECT A DATE");

        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

        booking_dates.setOnClickListener(v -> {
            materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
        });

        materialDatePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        String day = materialDatePicker.getHeaderText();
                        date = day.substring(0, 6);
                        booking_dates.setText("Selected Date is (" + date + ")");
                    }
                });

        backBtn.setOnClickListener(v -> {
            onBackPressed();
            Animatoo.animateSwipeLeft(CaddieContactActivity.this);
        });
        getServices();
        if (permissions()){
            getlocation();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},1001);
        }
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message = messageTxt.getText().toString();
                place = et_location.getText().toString();
                if (!service.equals("") && !date.equals("") && !place.equals("") && !message.equals("")){
                    sendRequest();
                    sendMessage(message,"golfer");
                    startActivity(new Intent(CaddieContactActivity.this,CaddiesRequestsActivity.class));
                    finish();
                    Animatoo.animateZoom(CaddieContactActivity.this);
                }
            }
        });
        //permissionAccess();
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

    private void sendRequest() {
        String key = requestsDb.push().getKey();
        RequestsModel requestsModel = new RequestsModel(key,user.getUid(),price,"Pending",date,
                place,message,service,uId);
        requestsDb.child(key).setValue(requestsModel);

        sendNotification(uId);

    }

    private void sendNotification(String uId) {
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(uId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Token token = snapshot.getValue(Token.class);
                    Data data = new Data("Caddie",user.getUid(), R.mipmap.ic_launcher, "You have new Request..",
                            "New Request", uId);

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

    private void sendMessage(String message, String mode) {
        long timestamp = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        mConversationReference.child(uId).child(user.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Conversation model = snapshot.getValue(Conversation.class);
                            //if (model.getChatWithId().equals(user.getUid()) && model.getUserUid().equals(idFromContact)){
                            unreadCount = model.getUnreadChatCount() + 1;
                            sentChat("text",message,timestamp,unreadCount);
                            //}
                        }else {
                            unreadCount = unreadCount + 1;
                            sentChat("text",message,timestamp,unreadCount);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    private void sentChat(String type, String contoh, long timestamp, int unreadCount) {
        Chat chatReciever = new Chat(type,"golfer",contoh, user.getUid(), uId, timestamp, unreadCount);

        Conversation conversationSender = new Conversation(type, user.getUid(), uId, contoh,
                timestamp, 0);
        Conversation conversationReceiver = new Conversation(type, uId, user.getUid(), contoh,
                timestamp, unreadCount);

        DatabaseReference senderReference = mConversationReference.child(user.getUid()).child(uId);
        senderReference.setValue(conversationSender);
        DatabaseReference receiverReference = mConversationReference.child(uId).child(user.getUid());
        receiverReference.setValue(conversationReceiver);


        DatabaseReference senderReference1 = mChatReference.child(user.getUid()).child(uId);
        senderReference1.child(String.valueOf(timestamp)).setValue(chatReciever);
        DatabaseReference receiverReference1 = mChatReference.child(uId).child(user.getUid());
        receiverReference1.child(String.valueOf(timestamp)).setValue(chatReciever);

     //   sendNotification(userId, username, contoh);

    }

    private void getServices() {
        caddie_db.child(uId)
                .child("services")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            serviceListModels.clear();
                            for (DataSnapshot ds : snapshot.getChildren()){
                                ServiceListModel model = ds.getValue(ServiceListModel.class);
                                serviceListModels.add(model.getTitle() + " " + model.getPrice());
                            }
                            arrayAdapter = new ArrayAdapter<String>(CaddieContactActivity.this,
                                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                                    serviceListModels);
                            serviceSpinner.setAdapter(arrayAdapter);
                            serviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    service = adapterView.getItemAtPosition(i).toString();
                                    int last = service.lastIndexOf(" ");
                                    String lastname = service.substring(last,service.length());
                                    price = lastname;
                                    //Toast.makeText(CaddieContactActivity.this,"" + lastname,Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @SuppressLint("MissingPermission")
    private void getlocation() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        //manager = new SharedPreferencesManager(this);
        // Define a listener that responds to location updates
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Log.d("latitude changed", "" + latitude);
                Log.d("longitude changed", "" + longitude);
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 101);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                place = addresses.get(0).getAddressLine(0);
                et_location.setText(place);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, locationListener);
            boolean isNetworkEnabled = locationManager.isProviderEnabled
                    (LocationManager.NETWORK_PROVIDER);
            String locationProvider = LocationManager.NETWORK_PROVIDER;

            if (isNetworkEnabled) {
                if (locationManager != null) {
                    latitude = locationManager.getLastKnownLocation(locationProvider).getLatitude();
                    longitude = locationManager.getLastKnownLocation(locationProvider).getLongitude();
                    Log.d("latitude", "" + latitude);
                    Log.d("longitude", "" + longitude);
                }
            } else {
                Toast.makeText(this, "Please turn on your Internet and GPS", Toast.LENGTH_LONG).show();
            }
        }
        //LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, CaddieContactActivity.this);
    }

    private boolean permissions() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void permissionAccess() {
        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                    }
                }).check();
    }

}