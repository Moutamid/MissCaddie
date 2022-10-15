package com.moutamid.misscaddie;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;

public class CaddieEditProfileActivity extends AppCompatActivity {
    TextView manage;
    ArrayList<SliderItem> SlideimageList;
    ImageView backBtn, addImg;
    SliderView sliderView;
    View overlay;
    CircleImageView profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caddie_edit_profile);

        manage = findViewById(R.id.manage);
        backBtn = findViewById(R.id.back_btn);
        sliderView = findViewById(R.id.image_slider);
        overlay = findViewById(R.id.overlayProfile);
        profile = findViewById(R.id.profile_img);
        addImg = findViewById(R.id.addImg);
        
        profile.setOnClickListener(v -> uploadImage());
        addImg.setOnClickListener(v -> uploadImage());


        SlideimageList = new ArrayList<>();

        String img1 = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg";
        String img2 = "https://media.istockphoto.com/photos/mountain-landscape-picture-id517188688?k=20&m=517188688&s=612x612&w=0&h=i38qBm2P-6V4vZVEaMy_TaTEaoCMkYhvLCysE7yJQ5Q=";
        String img3 = "https://media.istockphoto.com/photos/wild-grass-in-the-mountains-at-sunset-picture-id1322277517?b=1&k=20&m=1322277517&s=170667a&w=0&h=BSN_5NMGYJY2qPwI3_vOcEXVSX_hmGBOmXebMBxTLX0=";

        SlideimageList.add(new SliderItem(img1));
        SlideimageList.add(new SliderItem(img2));
        SlideimageList.add(new SliderItem(img3));

        ImageSliderAdapter adapter = new ImageSliderAdapter(this, SlideimageList);

        sliderView.setSliderAdapter(adapter);

        manage.setOnClickListener(v -> {
            startActivity(new Intent(this, CaddieManageImagesActivity.class));
        });

    }

    private void uploadImage() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, ""), 202);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 202) {
                try{
                    if (resultCode == RESULT_OK && data != null && data.getClipData() != null) {

                    } else {
                        Toast.makeText(this, "Please Select Images", Toast.LENGTH_SHORT).show();
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