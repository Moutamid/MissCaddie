package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.moutamid.misscaddie.adapters.ManageImageAdapter;
import com.moutamid.misscaddie.listners.ManageImageListner;
import com.moutamid.misscaddie.models.ManageImageModel;

import java.util.ArrayList;
import java.util.Collections;

public class CaddieManageImagesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView heading;
    ManageImageAdapter adapter;
    ArrayList<ManageImageModel> list;
    ManageImageModel model;
    ImageView backbtn;
    private String image = "";
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

        GridLayoutManager gridLayout = new GridLayoutManager(this, 2);

        recyclerView.setLayoutManager(gridLayout);
        recyclerView.setHasFixedSize(false);

//        Collections.reverse(list);
        getManageImages();

        backbtn.setOnClickListener(v -> {
            onBackPressed();
        });

    }

    private void getManageImages() {
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    list.clear();
                    model = new ManageImageModel(null, true);
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
                    model = new ManageImageModel(null, true);
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
            Intent intent = new Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(Intent.createChooser(intent, ""), 101);
        }
    };

    @Override
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
    }
}