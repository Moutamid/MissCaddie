package com.moutamid.misscaddie;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

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

        GridLayoutManager gridLayout = new GridLayoutManager(this, 2);

        recyclerView.setLayoutManager(gridLayout);
        recyclerView.setHasFixedSize(false);
        model = new ManageImageModel(null, true, R.drawable.ic_add);
        list.add(model);

        Collections.reverse(list);

        adapter = new ManageImageAdapter(CaddieManageImagesActivity.this, list, clickListner, heading);
        recyclerView.setAdapter(adapter);

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
                            model = new ManageImageModel(data.getClipData().getItemAt(currentImage).getUri(), false, R.drawable.ic_delete);
                            list.add(model);
                            heading.setText("Manage (" + (list.size() - 1) + ")");
                            currentImage++;
                        }
                        Collections.reverse(list);
                        adapter = new ManageImageAdapter(CaddieManageImagesActivity.this, list, clickListner, heading);
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