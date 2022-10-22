package com.moutamid.misscaddie.models;

import android.net.Uri;

public class ManageImageModel {
    String id;
    String Image;
    boolean state;
 //   int drawable;

    public ManageImageModel() {
    }

    public ManageImageModel(String id,String image, boolean state) {
        this.id = id;
        Image = image;
        this.state = state;
      //  this.drawable = drawable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

}
