package com.kaidi.fordrinking.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * Author: kaidi
 * Date: 01/29, 2015
 */
public class NetworkUtil {

    public static void DownloadImage(String urlStr, String newName) {
        final String imageURL = urlStr;
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(imageURL);
                    InputStream inputStream = url.openStream();




                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
