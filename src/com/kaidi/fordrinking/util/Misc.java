package com.kaidi.fordrinking.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Author: kaidi
 * Date: 01/23, 2015
 */
public class Misc {
    private static final String TAG = "Misc: ";

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

    public static void compressImage(Context context, String path)
            throws IOException
    {
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(path, op);

        int wRatio = (int) Math.ceil(op.outWidth / 540.0);

        if (wRatio > 1) {
            op.inSampleSize = wRatio;
        }
        op.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, op);

        OutputStream outputStream = context.openFileOutput("tmp_upload.jpg", Context.MODE_PRIVATE);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outputStream); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
        outputStream.flush();
        outputStream.close(); // do not forget to close the stream
    }
}
