package com.kaidi.fordrinking;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.kaidi.fordrinking.R;
import com.kaidi.fordrinking.fragment.*;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;


/**
 * Created by kaidi on 15-2-2.
 */
public class AddContentActivity extends SwipeBackActivity {

    private AddBlogFragment addBlogFragment;
    private AddPhotoFragment addPhotoFragment;
    private AddSoundFragment addSoundFragment;
    private AddVideoFragment addVideoFragment;
    private AddMessageFragment addMessageFragment;
    private AddPollFragment addPollFragment;

    private int[] mBgColors;

    private static int mBgIndex = 0;

    private String mKeyTrackingMode;

    private RadioGroup mTrackingModeGroup;

    private SwipeBackLayout mSwipeBackLayout;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcontent);


        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        mSwipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() {
            @Override
            public void onScrollStateChange(int state, float scrollPercent) {

            }

            @Override
            public void onEdgeTouch(int edgeFlag) {
            }

            @Override
            public void onScrollOverThreshold() {
            }
        });




        FragmentManager fm = getFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();

        Bundle bundle = getIntent().getExtras();
        int viewId = bundle.getInt("viewID", -1);

        switch (viewId) {
            case R.id.new_blog_btn:
                if (addBlogFragment == null) {
                    addBlogFragment = new AddBlogFragment();
                }
                transaction.replace(R.id.add_content, addBlogFragment);
                break;

            case R.id.new_photo_btn:
                if (addPhotoFragment == null) {
                    addPhotoFragment = new AddPhotoFragment();
                }
                transaction.replace(R.id.add_content, addPhotoFragment);
                break;

            case R.id.new_sound_btn:
                if (addSoundFragment == null) {
                    addSoundFragment = new AddSoundFragment();
                }
                transaction.replace(R.id.add_content, addSoundFragment);
                break;

            case R.id.new_video_btn:
                if (addVideoFragment == null) {
                    addVideoFragment = new AddVideoFragment();
                }
                transaction.replace(R.id.add_content, addVideoFragment);
                break;

            case R.id.new_message_btn:
                if (addMessageFragment == null) {
                    addMessageFragment = new AddMessageFragment();
                }
                transaction.replace(R.id.add_content, addMessageFragment);
                break;

            case R.id.new_poll_btn:
                if (addPollFragment == null) {
                    addPollFragment = new AddPollFragment();
                }
                transaction.replace(R.id.add_content, addPollFragment);
                break;
            case -1:
                Toast.makeText(this, "Error !", Toast.LENGTH_SHORT).show();
                break;
        }
        // transaction.addToBackStack();
        // 事务提交
        transaction.commit();
    }
}