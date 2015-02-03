package com.kaidi.fordrinking.photopicker;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by kaidi on 15-2-3.
 */
public interface ImageCallback {
    public void run(ImageView imageView, Bitmap bitmap);
}

