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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.misscaddie.adapters.CaddieProfileVPadapter;
import com.moutamid.misscaddie.adapters.ImageSliderCaddieAdapter;
import com.moutamid.misscaddie.fragments.CaddieInfoFragment;
import com.moutamid.misscaddie.fragments.CaddieReviewsFragment;
import com.moutamid.misscaddie.fragments.CaddieServicesFragment;
import com.moutamid.misscaddie.models.ManageImageModel;
import com.moutamid.misscaddie.models.Model_Caddie;
import com.moutamid.misscaddie.models.Review;
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
    private DatabaseReference db,db1;
    private TextView reviewCount;
    private String name,place;
    private RatingBar rating;

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

        reviewCount = findViewById(R.id.reviews_num);
        rating = findViewById(R.id.rating);
///        contactCaddie = findViewById(R.id.contact_caddie);
        backBtn = findViewById(R.id.back_btn);
        sliderView = findViewById(R.id.image_slider);
        mAuth = FirebaseAuth.getInstance();
        currrentUser = mAuth.getCurrentUser();
        SlideimageList = new ArrayList<>();
        getCaddieData();
        getManageImages();
        getReviewsDetail();

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

        backBtn.setOnClickListener(v -> {
            startActivity(new Intent(CaddieProfileActivity.this, Dashboard_Golfer.class));
            Animatoo.animateSwipeLeft(CaddieProfileActivity.this);
        });
      //  changeStatusBarColor(this,R.color.yellow);
    }

    float count = 0;
    int reviewChild = 0;
    private void getReviewsDetail() {

        db1 = FirebaseDatabase.getInstance().getReference().child("Review")
                .child(currrentUser.getUid());
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    reviewChild = (int) snapshot.getChildrenCount();
                    for (DataSnapshot ds : snapshot.getChildren()){
                        Review model = ds.getValue(Review.class);
                        count += model.getRating();
                    }
                    float total = (float) (count/reviewChild);
                    rating.setRating(total);
                    reviewCount.setText(reviewChild + " Reviews");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
                            Query query = db.child(model.getId()).child("services")
                                    .orderByChild("price").limitToFirst(1);

                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()){
                                        for (DataSnapshot ds : snapshot.getChildren()){
                                            ServiceListModel model = ds.getValue(ServiceListModel.class);
                                            price.setText("USD$ "+ model.getPrice());
                                        }
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