package com.moutamid.misscaddie;

import android.net.Uri;

public class ManageImageModel {
    Uri Image;
    boolean state;
    int drawable;

    public ManageImageModel() {
    }

    public ManageImageModel(Uri image, boolean state, int drawable) {
        Image = image;
        this.state = state;
        this.drawable = drawable;
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

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }
}
