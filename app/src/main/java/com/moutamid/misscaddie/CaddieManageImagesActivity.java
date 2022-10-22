package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.moutamid.misscaddie.listners.ManageImageListner;
import com.moutamid.misscaddie.models.ManageImageModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class CaddieManageImagesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView heading;
    ManageImageAdapter adapter;
    ArrayList<ManageImageModel> list;
    ManageImageModel model;
    ImageView backbtn;
    private ProgressDialog dialog;
    private Uri uri;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap = null;
    private StorageReference mStorage;
    private static final int STORAGE_PERMISSION_CODE = 101;
    private DatabaseReference db;
    FirebaseAuth mAuth;
    FirebaseUser currrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caddie_manage_images);

        recyclerView = findViewById(R.id.manageImageRV);
        heading = findViewById(R.id.text_heading);
        backbtn = findViewById(R.id.back_btn);
        mAuth = FirebaseAuth.getInstance();
        currrentUser = mAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference().child("Caddie").child(currrentUser.getUid())
                .child("Manage Image");
        mStorage = FirebaseStorage.getInstance().getReference();

        list = new ArrayList<>();
        dialog = new ProgressDialog(CaddieManageImagesActivity.this);

        GridLayoutManager gridLayout = new GridLayoutManager(this, 2);

        recyclerView.setLayoutManager(gridLayout);
        recyclerView.setHasFixedSize(false);

//        Collections.reverse(list);
        getManageImages();

        backbtn.setOnClickListener(v -> {
            onBackPressed();
        });
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
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    list.clear();
                    model = new ManageImageModel("",null, true);
                    for (DataSnapshot ds: snapshot.getChildren()){
                        ManageImageModel model = ds.getValue(ManageImageModel.class);
                        list.add(model);
                    }
                    Collections.reverse(list);
                    heading.setText("Manage (" + (list.size() - 1) + ")");
                    adapter = new ManageImageAdapter(CaddieManageImagesActivity.this, list, clickListner, heading);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else {
                    String key = db.push().getKey();
                    model = new ManageImageModel(key,"", true);
                    db.child(key).setValue(model);
                    list.add(model);
                    adapter = new ManageImageAdapter(CaddieManageImagesActivity.this, list, clickListner, heading);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private final ManageImageListner clickListner = new ManageImageListner() {
        @Override
        public void onClick(ManageImageModel Model) {
           /* Intent intent = new Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(Intent.createChooser(intent, "Choose Images"), 101);*/

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "SELECT IMAGE"), 3);

        }
    };

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 101) {
                try{
                    if (resultCode == RESULT_OK && data != null && data.getClipData() != null) {
                        int currentImage = 0;
                        while (currentImage < data.getClipData().getItemCount()) {
                            model = new ManageImageModel(data.getClipData().getItemAt(currentImage).getUri(),
                                    false);
                            String key = db.push().getKey();
                            db.child(key).setValue(model);
                            //list.add(model);
                            currentImage++;
                        }

                    } else {
                        Toast.makeText(this, "Please Select Multiple Images", Toast.LENGTH_SHORT).show();
                    }
                }  catch (Exception e){
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK &&
                data != null && data.getData() != null) {
            if (requestCode == 3) {
                uri = data.getData();
             //   imageView.setImageURI(uri);
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    saveImage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void saveImage() {
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setMessage("Uploading....");
        dialog.show();
        if (uri != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream);
            byte[] thumb_byte_data = byteArrayOutputStream.toByteArray();

            final StorageReference reference = mStorage.child("Managed Images")
                    .child(System.currentTimeMillis() + ".jpg");
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
                                String image = downloadUri.toString();
                                String key = db.push().getKey();
                                model = new ManageImageModel(key,image,false);
                                db.child(key).setValue(model);
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
            Toast.makeText(CaddieManageImagesActivity.this, "Please Select Image ", Toast.LENGTH_LONG).show();

        }
    }
}