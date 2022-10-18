package com.moutamid.misscaddie.models;

import android.net.Uri;

public class ManageImageModel {
    Uri Image;
    boolean state;
 //   int drawable;

    public ManageImageModel() {
    }

    public ManageImageModel(Uri image, boolean state) {
        Image = image;
        this.state = state;
      //  this.drawable = drawable;
    }

    public Uri getImage() {
        return Image;
    }

    public void setImage(Uri image) {
        Image = image;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

}
