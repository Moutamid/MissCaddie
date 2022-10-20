package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.moutamid.misscaddie.adapters.ManageImageAdapter;
import com.moutamid.misscaddie.models.ManageImageModel;
import com.moutamid.misscaddie.models.Model_Golfer;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class GolferProfileEditActivity extends AppCompatActivity {
    ImageView back_btn;
    CircleImageView logo;
    Uri imageURI;
    private Bitmap bitmap = null;
    private StorageReference mStorage;
    private String name,email,password;
    private String image = "";
    private ProgressDialog dialog;
    FirebaseAuth mAuth;
    FirebaseUser currrentUser;
    private DatabaseReference db;
    private EditText nameTxt,emailTxt,passwordTxt;
    private TextView saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_golfer_profile_edit);

        back_btn = findViewById(R.id.back_btn);
        logo = findViewById(R.id.logo);
        nameTxt = findViewById(R.id.personName);
        emailTxt = findViewById(R.id.email);
        passwordTxt = findViewById(R.id.password);
        saveBtn = findViewById(R.id.save_btn);
        mAuth = FirebaseAuth.getInstance();
        currrentUser = mAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference().child("Golfer");
        mStorage = FirebaseStorage.getInstance().getReference();
        logo.setOnClickListener(v -> {
            ImagePicker.with(GolferProfileEditActivity.this)
                    .crop(600, 600)
                    .compress(1024)
                    .maxResultSize(1080, 1080)
                    .start();
            // getImageFromGallery();
        });

        back_btn.setOnClickListener(v -> {
            Intent intent = new Intent(GolferProfileEditActivity.this , CaddiesRequestsActivity.class);
            startActivity(intent);
            Animatoo.animateSlideRight(GolferProfileEditActivity.this);
        });

        getUserDetails();
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameTxt.getText().toString();
                email = emailTxt.getText().toString();
                password = passwordTxt.getText().toString();

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !image.equals("")){
                 updateUser();
                }
            }
        });
    }

    private void updateUser() {
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("name",name);
        hashMap.put("email",email);
        hashMap.put("password",password);
        hashMap.put("image",image);
        db.child(currrentUser.getUid()).updateChildren(hashMap);
        startActivity(new Intent(GolferProfileEditActivity.this,MessagesActivity.class));
        finish();
        Animatoo.animateSlideRight(GolferProfileEditActivity.this);
    }

    private void getUserDetails() {
        db.child(currrentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Model_Golfer model_golfer = snapshot.getValue(Model_Golfer.class);
                    name = model_golfer.getName();
                    image = model_golfer.getImage();
                    email = model_golfer.getEmail();
                    password = model_golfer.getPassword();

                    nameTxt.setText(name);
                    emailTxt.setText(email);
                    passwordTxt.setText(password);
                    Glide.with(GolferProfileEditActivity.this)
                            .load(image)
                            .placeholder(R.drawable.img3)
                            .into(logo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, ""), 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            try{
                if (resultCode == RESULT_OK && data != null) {
                    imageURI = data.getData();
                    logo.setImageURI(data.getData());
                    saveInformation();
                } else {
                    Toast.makeText(this, "Please Select An Images", Toast.LENGTH_SHORT).show();
                }
            }  catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void saveInformation() {
        dialog = new ProgressDialog(GolferProfileEditActivity.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setMessage("Uploading your profile....");
        dialog.show();
        if (imageURI != null) {
            logo.setDrawingCacheEnabled(true);
            logo.buildDrawingCache();
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