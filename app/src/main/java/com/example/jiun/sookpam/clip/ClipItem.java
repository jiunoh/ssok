package com.example.jiun.sookpam.clip;

import android.widget.ImageView;
import com.example.jiun.sookpam.R;

public  class ClipItem {
    public final String title;
    public final String category;
    public ImageView star;

    public ClipItem(String title, String category) {
        this.title = title;
        this.category = category;
        this.star.setImageResource(R.drawable.star_on);
    }

    @Override
    public String toString() {
        return title;
    }
}