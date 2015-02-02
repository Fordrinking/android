package com.kaidi.fordrinking;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;
import com.kaidi.fordrinking.R;
import com.kaidi.fordrinking.fragment.*;


/**
 * Created by kaidi on 15-2-2.
 */
public class AddContentActivity extends Activity {

    private AddBlogFragment addBlogFragment;
    private AddPhotoFragment addPhotoFragment;
    private AddSoundFragment addSoundFragment;
    private AddVideoFragment addVideoFragment;
    private AddMessageFragment addMessageFragment;
    private AddPollFragment addPollFragment;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcontent);

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