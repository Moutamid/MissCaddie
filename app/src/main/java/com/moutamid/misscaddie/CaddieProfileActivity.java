package com.moutamid.misscaddie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.misscaddie.adapters.CaddieProfileVPadapter;
import com.moutamid.misscaddie.adapters.ImageSliderCaddieAdapter;
import com.moutamid.misscaddie.fragments.CaddieInfoFragment;
import com.moutamid.misscaddie.fragments.CaddieReviewsFragment;
import com.moutamid.misscaddie.fragments.CaddieServicesFragment;
import com.moutamid.misscaddie.models.ManageImageModel;
import com.moutamid.misscaddie.models.Model_Caddie;
import com.moutamid.misscaddie.models.ServiceListModel;
import com.moutamid.misscaddie.models.SliderItem;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CaddieProfileActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    ArrayList<SliderItem> SlideimageList;
    TextView contactCaddie;
    TabItem info, services, reviews;
    ImageView backBtn;
    SliderView sliderView;
    private CircleImageView profileImg;
    private TextView caddieName,placeCaddie,price;
   // private ActivityCaddieProfileBinding b;
    private String userId;
    FirebaseAuth mAuth;
    FirebaseUser currrentUser;
    private DatabaseReference db;
    private String name,place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  b = ActivityCaddieProfileBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_caddie_profile);
        userId = getIntent().getStringExtra("uId");
        db = FirebaseDatabase.getInstance().getReference().child("Caddie");
        tabLayout = findViewById(R.id.tabsLayout);
        viewPager = findViewById(R.id.ProfileViewpager);
        info = findViewById(R.id.infoTab);
        services = findViewById(R.id.servicesTab);
        reviews = findViewById(R.id.reviewsTab);
        caddieName = findViewById(R.id.caddie_name);
        placeCaddie = findViewById(R.id.place_caddie);
        price = findViewById(R.id.price_golfer);
        profileImg = findViewById(R.id.profile_img);
        contactCaddie = findViewById(R.id.contact_caddie);
        backBtn = findViewById(R.id.back_btn);
        sliderView = findViewById(R.id.image_slider);
        mAuth = FirebaseAuth.getInstance();
        currrentUser = mAuth.getCurrentUser();
        SlideimageList = new ArrayList<>();
        getCaddieData();
      /*  String img1 = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg";
        String img2 = "https://media.istockphoto.com/photos/mountain-landscape-picture-id517188688?k=20&m=517188688&s=612x612&w=0&h=i38qBm2P-6V4vZVEaMy_TaTEaoCMkYhvLCysE7yJQ5Q=";
        String img3 = "https://media.istockphoto.com/photos/wild-grass-in-the-mountains-at-sunset-picture-id1322277517?b=1&k=20&m=1322277517&s=170667a&w=0&h=BSN_5NMGYJY2qPwI3_vOcEXVSX_hmGBOmXebMBxTLX0=";

        SlideimageList.add(new SliderItem(img1));
        SlideimageList.add(new SliderItem(img2));
        SlideimageList.add(new SliderItem(img3));

        ImageSliderGolferAdapter adapter = new ImageSliderGolferAdapter(this, SlideimageList);

        sliderView.setSliderAdapter(adapter);*/

        getManageImages();

        Bundle bundle = new Bundle();
        bundle.putString("uId", userId);
        CaddieProfileVPadapter caddieProfileVPadapter = new CaddieProfileVPadapter(getSupportFragmentManager());
        CaddieInfoFragment caddieInfoFragment = new CaddieInfoFragment();
        caddieInfoFragment.setArguments(bundle);
        CaddieServicesFragment caddieServicesFragment = new CaddieServicesFragment();
        caddieServicesFragment.setArguments(bundle);
        CaddieReviewsFragment caddieReviewsFragment = new CaddieReviewsFragment();
        caddieReviewsFragment.setArguments(bundle);
        caddieProfileVPadapter.addFragment(caddieInfoFragment, "Info");
        caddieProfileVPadapter.addFragment(caddieServicesFragment, "Services");
        caddieProfileVPadapter.addFragment(caddieReviewsFragment, "Reviews");

        viewPager.setAdapter(caddieProfileVPadapter);
        tabLayout.setupWithViewPager(viewPager);

        contactCaddie.setOnClickListener(v -> {
            Intent intent = new Intent(CaddieProfileActivity.this, CaddieContactActivity.class);
            intent.putExtra("userId",userId);
            startActivity(intent);
            Animatoo.animateSwipeLeft(CaddieProfileActivity.this);
        });

        backBtn.setOnClickListener(v -> {
            startActivity(new Intent(CaddieProfileActivity.this, Dashboard_Golfer.class));
            Animatoo.animateSwipeLeft(CaddieProfileActivity.this);
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
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Caddie").child(userId)
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

                    ImageSliderCaddieAdapter adapter = new ImageSliderCaddieAdapter(CaddieProfileActivity.this,
                            SlideimageList);

                    sliderView.setSliderAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private ArrayList<ServiceListModel> serviceListModels = new ArrayList<>();
    private void getCaddieData() {
        db.child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Model_Caddie model = snapshot.getValue(Model_Caddie.class);
                            name = model.getName();
                            place = model.getState();
                            caddieName.setText(name);
                            placeCaddie.setText(place);
                            Glide.with(CaddieProfileActivity.this)
                                    .load(model.getImage())
                                    .placeholder(R.drawable.bi_person_fill)
                                    .into(profileImg);
                            db.child(model.getId()).child("services").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()){
                                        for (DataSnapshot ds : snapshot.getChildren()){
                                            ServiceListModel model = ds.getValue(ServiceListModel.class);
                                            serviceListModels.add(model);
                                        }
                                        price.setText("US$ "+ serviceListModels.get(0).getPrice());
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

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