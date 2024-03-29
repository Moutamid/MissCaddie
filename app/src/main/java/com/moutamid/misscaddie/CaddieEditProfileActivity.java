package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.moutamid.misscaddie.adapters.AddOnsListAdapter;
import com.moutamid.misscaddie.adapters.ImageSliderCaddieAdapter;
import com.moutamid.misscaddie.adapters.ImageSliderGolferAdapter;
import com.moutamid.misscaddie.adapters.ManageImageAdapter;
import com.moutamid.misscaddie.models.Bonus;
import com.moutamid.misscaddie.models.ManageImageModel;
import com.moutamid.misscaddie.models.Model_Caddie;
import com.moutamid.misscaddie.models.ServiceListModel;
import com.moutamid.misscaddie.models.SliderItem;
import com.smarteist.autoimageslider.SliderView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class CaddieEditProfileActivity extends AppCompatActivity {
    TextView manage;
    ArrayList<SliderItem> SlideimageList;
    ImageView backBtn, addImg;
    SliderView sliderView;
    View overlay;
    FirebaseAuth mAuth;
    FirebaseUser currrentUser;
    private DatabaseReference db;
    private String name,price,place,state;
    private String image = "";
    private ProgressDialog dialog;
    private Uri uri;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap = null;
    private StorageReference mStorage;

    private EditText locationTxt,priceTxt,nameTxt,aboutTxt;

    private TextView caddieName,placeCaddie,priceGolfer,updateBtn;
    private CircleImageView profileImg;
    private Spinner spinnerStates;
    private ArrayAdapter<String> arrayAdapter;
    private static final int STORAGE_PERMISSION_CODE = 101;
    private String description = "";
    private String[] states = {"Select State (New York)","Alabama","Alaska","Arizona","Arkansas",
    "California","Colorado", "Connecticut", "Delaware",
        "Florida",
        "Georgia",
        "Hawaii",
        "Idaho",
        "Illinois",
        "Indiana",
        "Iowa",
        "Kansas",
        "Kentucky",
        "Louisiana",
        "Maine",
        "Maryland",
        "Massachusetts",
        "Michigan",
        "Minnesota",
        "Mississippi",
        "Missouri",
        "Montana",
        "Nebraska",
        "Nevada",
            "New Hampshire",
            "New Jersey",
    "New Mexico",
    "New York",
    "North Carolina",
    "North Dakota",
        "Ohio",
        "Oklahoma",
        "Oregon",
        "Pennsylvania",
    "Rhode Island",
    "South Carolina",
    "South Dakota",
        "Tennessee",
        "Texas",
        "Utah",
        "Vermont",
        "Virginia",
        "Washington",
            "West Virginia",
        "Wisconsin",
        "Wyoming",
};

    @SuppressLint({"ResourceType", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caddie_edit_profile);

        manage = findViewById(R.id.manage);
        backBtn = findViewById(R.id.back_btn);
        sliderView = findViewById(R.id.image_slider);
        overlay = findViewById(R.id.overlayProfile);
        profileImg = findViewById(R.id.profile_img);
        addImg = findViewById(R.id.addImg);
        nameTxt = findViewById(R.id.et_name);
        caddieName = findViewById(R.id.caddie_name);
        placeCaddie = findViewById(R.id.place_caddie);
        locationTxt = findViewById(R.id.et_location);
        priceTxt = findViewById(R.id.et_price);
        priceGolfer = findViewById(R.id.price_golfer);
        aboutTxt = findViewById(R.id.et_about);
        spinnerStates = findViewById(R.id.spinner_states);
        updateBtn = findViewById(R.id.update);
        mAuth = FirebaseAuth.getInstance();
        currrentUser = mAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference().child("Caddie");
        mStorage = FirebaseStorage.getInstance().getReference();
        profileImg.setOnClickListener(v -> checkPermission());
        addImg.setOnClickListener(v -> uploadImage());


        SlideimageList = new ArrayList<>();
        getManageImages();

      /*  String img1 = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg";
        String img2 = "https://media.istockphoto.com/photos/mountain-landscape-picture-id517188688?k=20&m=517188688&s=612x612&w=0&h=i38qBm2P-6V4vZVEaMy_TaTEaoCMkYhvLCysE7yJQ5Q=";
        String img3 = "https://media.istockphoto.com/photos/wild-grass-in-the-mountains-at-sunset-picture-id1322277517?b=1&k=20&m=1322277517&s=170667a&w=0&h=BSN_5NMGYJY2qPwI3_vOcEXVSX_hmGBOmXebMBxTLX0=";

        SlideimageList.add(new SliderItem(img1));
        SlideimageList.add(new SliderItem(img2));
        SlideimageList.add(new SliderItem(img3));*/

        manage.setOnClickListener(v -> {
            startActivity(new Intent(this, CaddieManageImagesActivity.class));
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(CaddieEditProfileActivity.this, CaddieDashboardActivity.class));
                finish();
            }
        });
        arrayAdapter = new ArrayAdapter<String>
                (CaddieEditProfileActivity.this,
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,states);
        spinnerStates.setAdapter(arrayAdapter);
        spinnerStates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                state = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameTxt.getText().toString();
                price = priceTxt.getText().toString();
                place = locationTxt.getText().toString();
                description = aboutTxt.getText().toString();
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("name",name);
                hashMap.put("place",place);
                hashMap.put("state",state);
                hashMap.put("image",image);
                hashMap.put("about",description);
                db.child(currrentUser.getUid()).updateChildren(hashMap);
                //getCaddieData();
                Toast.makeText(CaddieEditProfileActivity.this, "Updated!", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(CaddieEditProfileActivity.this, CaddieDashboardActivity.class));
                finish();
            }
        });
        getCaddieData();
        changeStatusBarColor(this,R.color.yellow);
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


    private void getManageImages() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Caddie").child(currrentUser.getUid())
                .child("Manage Image");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    SlideimageList.clear();
                    // model = new ManageImageModel(null, true, R.drawable.ic_add);
                    for (DataSnapshot ds: snapshot.getChildren()){
                        ManageImageModel model = ds.getValue(ManageImageModel.class);
                        String image = model.getImage().toString();
                        if (!image.equals("")) {
                            SliderItem sliderItem = new SliderItem(image);
                            SlideimageList.add(sliderItem);
                        }
                    }

                    ImageSliderCaddieAdapter adapter = new ImageSliderCaddieAdapter(CaddieEditProfileActivity.this,
                            SlideimageList);

                    sliderView.setSliderAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void checkPermission()
    {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(CaddieEditProfileActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(CaddieEditProfileActivity.this, new String[] {
                    Manifest.permission.READ_EXTERNAL_STORAGE }, STORAGE_PERMISSION_CODE);
        }
        else {
            uploadImage();
            Toast.makeText(CaddieEditProfileActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                uploadImage();
                Toast.makeText(CaddieEditProfileActivity.this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(CaddieEditProfileActivity.this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private ArrayList<ServiceListModel> serviceListModels = new ArrayList<>();
    private void getCaddieData() {
        db.child(currrentUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Model_Caddie model = snapshot.getValue(Model_Caddie.class);
                            name = model.getName();
                            place = model.getPlace();
                            state = model.getState();
                            description = model.getAbout();
                            caddieName.setText(name);
                            placeCaddie.setText(place);
                            nameTxt.setText(name);
                            aboutTxt.setText(description);
                            locationTxt.setText(model.getPlace());
                            image = model.getImage();
                            Glide.with(CaddieEditProfileActivity.this)
                                    .load(image)
                                    .placeholder(R.drawable.bi_person_fill)
                                    .into(profileImg);
                            for (int i = 0; i < states.length; i++){

                                if(states[i].equals(state)){
                                    spinnerStates.setSelection(i);
                                }
                            }


                            Query query = db.child(currrentUser.getUid()).child("services")
                                    .orderByChild("price").limitToFirst(1);

                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()){
                                        for (DataSnapshot ds : snapshot.getChildren()){
                                            ServiceListModel model = ds.getValue(ServiceListModel.class);
                                            priceGolfer.setText("USD$ "+ model.getPrice());
                                            priceTxt.setText("USD$ "+ model.getPrice());
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

/*                            db.child(currrentUser.getUid()).child("services").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()){
                                        for (DataSnapshot ds : snapshot.getChildren()){
                                            ServiceListModel model = ds.getValue(ServiceListModel.class);
                                            serviceListModels.add(model);
                                        }
                                        b.priceGolfer.setText("US$ "+ serviceListModels.get(0).getPrice());
                                        b.etPrice.setText("US$ "+ serviceListModels.get(0).getPrice());
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });*/
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void uploadImage() {

        ImagePicker.with(CaddieEditProfileActivity.this)
                .crop(600, 600)
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start();

/*        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Choose Image"), 2);*/
      /*  Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"SELECT IMAGE"),PICK_IMAGE_REQUEST);*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
                try{
                    if (resultCode == RESULT_OK && data != null) {
                        uri = data.getData();
                        profileImg.setImageURI(uri);
                        overlay.setVisibility(View.VISIBLE);
                        addImg.setVisibility(View.VISIBLE);

                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        saveInformation();
                    } else {
                        Toast.makeText(this, "Please Select Images", Toast.LENGTH_SHORT).show();
                    }
                }  catch (Exception e){
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        /*if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK &&
                data != null && data.getData() != null) {
            uri = data.getData();
            profile.setImageURI(uri);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                saveInformation();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }*/
    }
    private void saveInformation() {
        dialog = new ProgressDialog(CaddieEditProfileActivity.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setMessage("Uploading your profile....");
        dialog.show();
        if (uri != null) {
            profileImg.setDrawingCacheEnabled(true);
            profileImg.buildDrawingCache();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream);
            byte[] thumb_byte_data = byteArrayOutputStream.toByteArray();
            String uId = currrentUser.getUid();
            final StorageReference reference = mStorage.child("Profile Images").child(uId + ".jpg");
            final UploadTask uploadTask = reference.putBytes(thumb_byte_data);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return reference.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();
                                image = downloadUri.toString();
                                dialog.dismiss();
                            }
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }else {
            Toast.makeText(getApplicationContext(), "Please Select Image ", Toast.LENGTH_LONG).show();

        }
    }

}