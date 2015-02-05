package com.kaidi.fordrinking.photopicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import com.kaidi.fordrinking.R;

/**
 * Created by kaidi on 2/4/15.
 */
public class ImageHolder extends FrameLayout {
    private int checkedState;
    private Context context;

    public ImageHolder(Context c) {
        super(c);
        context      = c;
        checkedState = 0;
        LayoutInflater.from(c);
    }

    public void setChecked() {
        if (this.getChildAt(1) == null) {
            ImageView iv = new ImageView(context);
            iv.setImageResource(R.drawable.image_selected_bg);
            iv.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            this.addView(iv);
        }
    }

    public void setUnChecked() {
        if (this.getChildAt(1) != null) {
            this.removeViewAt(1);
        }
    }

    public int getCheckedState() {
        return checkedState;
    }

    public void setCheckedState(int checkedState) {
        this.checkedState = checkedState;
    }
}
