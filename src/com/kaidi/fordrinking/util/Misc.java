package com.kaidi.fordrinking.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.kaidi.fordrinking.R;

import java.io.*;

/**
 * Author: kaidi
 * Date: 01/23, 2015
 */
public class Misc {
    private static final String TAG = "Misc: ";

    public static String getHttpURL(Context context, int urlid) {
        if (context.getString(R.string.app_state).equals("local_testing")) {
            return context.getString(R.string.url_origin_local) + context.getString(urlid);
        } else {
            return context.getString(R.string.url_origin) + context.getString(urlid);
        }
    }

    public static void setTextViewTypeface(ViewGroup viewGroup, Typeface typeface)
    {
        if (viewGroup == null) return;

        int children = viewGroup.getChildCount();
        Log.d(TAG, "setTypeface " + viewGroup + " : " + children);
        for (int i=0; i<children; i++)
        {
            View view = viewGroup.getChildAt(i);
            if (view instanceof ViewGroup) setTextViewTypeface((ViewGroup)view, typeface);
            if (view instanceof TextView)
            {
                TextView textView = (TextView) view;
                textView.setTypeface(typeface);
            }
        }
    }

    public static byte[] compressImage(String path, float des_w)
            throws IOException
    {
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(path, op);

        float wRatio = op.outWidth / des_w;

        Log.e("compress image: ", String.valueOf(wRatio));

        int width;
        int height;
        if (wRatio > 1) {
            width  = (int) Math.ceil(op.outWidth  / wRatio);
            height = (int) Math.ceil(op.outHeight / wRatio);
        } else {
            width = op.outWidth;
            height = op.outHeight;
        }
        Log.e("after compress: ", String.valueOf(width) + ", " + String.valueOf(height));
        op.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, op);
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);

        ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayInputStream); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
        return byteArrayInputStream.toByteArray();
    }
}
