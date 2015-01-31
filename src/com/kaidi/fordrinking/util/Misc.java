package com.kaidi.fordrinking.util;

import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
}
