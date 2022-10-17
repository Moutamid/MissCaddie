package com.moutamid.misscaddie;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.moutamid.misscaddie.adapters.ManageImageAdapter;
import com.moutamid.misscaddie.fragments.CaddiesRequestsActivity;
import com.moutamid.misscaddie.models.ManageImageModel;

import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;

public class GolferProfileEditActivity extends AppCompatActivity {
    ImageView back_btn;
    CircleImageView logo;
    Uri imageURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_golfer_profile_edit);

        back_btn = findViewById(R.id.back_btn);
        logo = findViewById(R.id.logo);

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
}