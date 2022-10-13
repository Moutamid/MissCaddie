package com.moutamid.misscaddie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class CaddieProfileActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    ArrayList<SliderItem> SlideimageList;
    TextView contactCaddie;
    TabItem info, services, reviews;
    ImageView backBtn;
    SliderView sliderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caddie_profile);
        tabLayout = findViewById(R.id.tabsLayout);
        viewPager = findViewById(R.id.ProfileViewpager);
        info = findViewById(R.id.infoTab);
        services = findViewById(R.id.servicesTab);
        reviews = findViewById(R.id.ReviewsTab);
        contactCaddie = findViewById(R.id.contact_caddie);
        backBtn = findViewById(R.id.back_btn);
        sliderView = findViewById(R.id.image_slider);

        SlideimageList = new ArrayList<>();

        String img1 = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg";
        String img2 = "https://media.istockphoto.com/photos/mountain-landscape-picture-id517188688?k=20&m=517188688&s=612x612&w=0&h=i38qBm2P-6V4vZVEaMy_TaTEaoCMkYhvLCysE7yJQ5Q=";
        String img3 = "https://media.istockphoto.com/photos/wild-grass-in-the-mountains-at-sunset-picture-id1322277517?b=1&k=20&m=1322277517&s=170667a&w=0&h=BSN_5NMGYJY2qPwI3_vOcEXVSX_hmGBOmXebMBxTLX0=";

        SlideimageList.add(new SliderItem(img1));
        SlideimageList.add(new SliderItem(img2));
        SlideimageList.add(new SliderItem(img3));

        ImageSliderAdapter adapter = new ImageSliderAdapter(this, SlideimageList);

        sliderView.setSliderAdapter(adapter);

        CaddieProfileVPadapter caddieProfileVPadapter = new CaddieProfileVPadapter(getSupportFragmentManager());
        caddieProfileVPadapter.addFragment(new CaddieInfoFragment(), "Info");
        caddieProfileVPadapter.addFragment(new CaddieServicesFragment(), "Services");
        caddieProfileVPadapter.addFragment(new CaddieReviewsFragment(), "Reviews");

        viewPager.setAdapter(caddieProfileVPadapter);
        tabLayout.setupWithViewPager(viewPager);

        contactCaddie.setOnClickListener(v -> {
            startActivity(new Intent(CaddieProfileActivity.this, CaddieContactActivity.class));
            Animatoo.animateSwipeLeft(CaddieProfileActivity.this);
        });

        backBtn.setOnClickListener(v -> {
            startActivity(new Intent(CaddieProfileActivity.this, Dashboard_Golfer.class));
            Animatoo.animateSwipeLeft(CaddieProfileActivity.this);
        });

    }

}