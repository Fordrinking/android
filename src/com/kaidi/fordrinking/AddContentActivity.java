package com.kaidi.fordrinking;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import com.kaidi.fordrinking.R;
import com.kaidi.fordrinking.fragment.*;
import com.kaidi.fordrinking.util.DataShare;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;


/**
 * Created by kaidi on 15-2-2.
 */
public class AddContentActivity extends ActionBarActivity {

    private AddBlogFragment addBlogFragment;
    private AddPhotoFragment addPhotoFragment;
    private AddSoundFragment addSoundFragment;
    private AddVideoFragment addVideoFragment;
    private AddMessageFragment addMessageFragment;
    private AddPollFragment addPollFragment;

    private Toolbar toolbar;

    private HttpClient httpClient;
    private int fragmentID = -1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcontent);
        DataShare.getInstance().save("AddContentActivity", this);

        toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        toolbar.setTitle("Post Blog");
        setSupportActionBar(toolbar);
        //Navigation Icon 要設定在 setSupoortActionBar 才有作用否則會出現 back button
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setOnMenuItemClickListener(onMenuItemClick);

        initFragUI();

        httpClient = new DefaultHttpClient();

    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_send_blog:
                    sendBlog(fragmentID);
                    break;
                case R.id.action_settings:
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    private void sendBlog(int viewId) {
        switch (viewId) {
            case R.id.new_blog_btn:
                addBlogFragment.send();
                break;

            case R.id.new_photo_btn:
                addPhotoFragment.send();
                break;

            case R.id.new_sound_btn:
                addSoundFragment.send();
                break;

            case R.id.new_video_btn:
                addVideoFragment.send();
                break;

            case R.id.new_message_btn:
                addMessageFragment.send();
                break;

            case R.id.new_poll_btn:
                addPollFragment.send();
                break;
            case -1:
                Toast.makeText(this, "Error !", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void initFragUI() {
        FragmentManager fm = getFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();

        Bundle bundle = getIntent().getExtras();
        fragmentID = bundle.getInt("viewID", -1);

        switch (fragmentID) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_addcontent, menu);
        return true;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }
}