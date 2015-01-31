package com.kaidi.fordrinking.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Author: kaidi
 * Date: 01/23, 2015
 */
public class IconFontView extends TextView {

    public IconFontView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public IconFontView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IconFontView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fontawesome.ttf");
            setTypeface(tf);
        }
    }

}
