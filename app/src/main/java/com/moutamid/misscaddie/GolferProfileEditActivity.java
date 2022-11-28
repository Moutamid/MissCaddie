package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
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

public class GolferProfileEditActivity extends Fragment {
    CircleImageView logo;
    Uri imageURI;
    private Bitmap bitmap = null;
    private StorageReference mStorage;
    private String name,email,password;
    private String image = "";
    CardView privacybtn, termsbtn;
    private ProgressDialog dialog;
    FirebaseAuth mAuth;
    FirebaseUser currrentUser;
    private DatabaseReference db;
    private EditText nameTxt,passwordTxt;
    private TextView saveBtn,emailTxt,logout;
    private GoogleApiClient mGoogleSignInClient;
    private SharedPreferencesManager manager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_golfer_profile_edit, container, false);
        if (isAdded()) {
            logo = view.findViewById(R.id.logo);
            manager = new SharedPreferencesManager(getActivity());
            nameTxt = view.findViewById(R.id.personName);
            emailTxt = view.findViewById(R.id.email);
            passwordTxt = view.findViewById(R.id.password);
            saveBtn = view.findViewById(R.id.save_btn);
            termsbtn = view.findViewById(R.id.termsbtn);
            privacybtn = view.findViewById(R.id.privacybtn);
            logout = view.findViewById(R.id.logout);
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

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
                    GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            mGoogleSignInClient = new GoogleApiClient.Builder(requireActivity())
                    .enableAutoManage(requireActivity(), new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    }).addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();

            getUserDetails();
            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    name = nameTxt.getText().toString();
                    password = passwordTxt.getText().toString();
                    updateUser();
                }
            });
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    manager.storeString("module", "");
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
            termsbtn.setOnClickListener(v -> {
                Uri webpage = Uri.parse("https://www.google.com");
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                try {
                    startActivity(webIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            privacybtn.setOnClickListener(v -> {
                Uri webpage = Uri.parse("https://www.google.com");
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                try {
                    startActivity(webIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        return view;
    }


    private void updateUser() {
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("name",name);
        hashMap.put("password",password);
        hashMap.put("image",image);
        db.child(currrentUser.getUid()).updateChildren(hashMap);
        getUserDetails();
        Toast.makeText(getActivity(), "Updated!", Toast.LENGTH_SHORT).show();

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
                            .placeholder(R.drawable.bi_person_fill)
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            try{
                if (resultCode == getActivity().RESULT_OK && data != null) {
                    imageURI = data.getData();
                    logo.setImageURI(data.getData());
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageURI);
                    saveInformation();
                } else {
                    Toast.makeText(getActivity(), "Please Select An Images", Toast.LENGTH_SHORT).show();
                }
            }  catch (Exception e){
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void saveInformation() {
        dialog = new ProgressDialog(getActivity());
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
            Toast.makeText(getActivity(), "Please Select Image ", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleSignInClient != null && mGoogleSignInClient.isConnected()) {
            mGoogleSignInClient.stopAutoManage(requireActivity());
            mGoogleSignInClient.disconnect();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mGoogleSignInClient != null && mGoogleSignInClient.isConnected()) {
            mGoogleSignInClient.stopAutoManage(requireActivity());
            mGoogleSignInClient.disconnect();
        }
    }
}