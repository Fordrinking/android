package com.melnykov.fab;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;
import com.kaidi.fordrinking.fragment.HomeFragment;
import com.kaidi.fordrinking.util.DataShare;

public class ObservableScrollView extends ScrollView{

    public interface OnScrollChangedListener {
        void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt);
    }

    private OnScrollChangedListener mOnScrollChangedListener;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedListener != null) {
            mOnScrollChangedListener.onScrollChanged(this, l, t, oldl, oldt);
        }
        View view = getChildAt(getChildCount()-1);

        // Calculate the scrolldiff
        int diff = (view.getBottom()-(getHeight()+getScrollY()));

        // if diff is zero, then the bottom has been reached
        if(diff == 0 )
        {
            // notify that we have reached the bottom
            Boolean isLoaded = (Boolean) DataShare.getInstance().retrieve("isMoreBlogLoaded");
            if (!isLoaded) {
                HomeFragment homeFragment = (HomeFragment) DataShare.getInstance().retrieve("HomeFragment");
                homeFragment.loadMoreBlogs();
                DataShare.getInstance().save("isMoreBlogLoaded", true);
            }
        }
    }

    public void setOnScrollChangedListener(OnScrollChangedListener listener) {
        mOnScrollChangedListener = listener;
    }
}