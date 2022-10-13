package com.moutamid.misscaddie;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CaddieManageImagesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView heading;
    ManageImageAdapter adapter;
    ArrayList<ManageImageModel> list;
    ManageImageModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caddie_manage_images);

        recyclerView = findViewById(R.id.manageImageRV);
        heading = findViewById(R.id.text_heading);

        list = new ArrayList<>();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(false);
        model = new ManageImageModel(null, true, R.drawable.ic_add);
        list.add(model);

        adapter = new ManageImageAdapter(CaddieManageImagesActivity.this, list);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 101) {
                try{
                    if (resultCode == RESULT_OK && data != null && data.getClipData() != null) {
                        int currentImage = 0;
                        while (currentImage < data.getClipData().getItemCount()) {
                                //CarImagesModel carImagesModel= new CarImagesModel(data.getClipData().getItemAt(currentImage).getUri());
                            model = new ManageImageModel(data.getClipData().getItemAt(currentImage).getUri(), false, R.drawable.ic_delete);
                            list.add(model);
                            currentImage++;
                        }
                        adapter = new ManageImageAdapter(CaddieManageImagesActivity.this, list);
                        recyclerView.setAdapter(adapter);
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