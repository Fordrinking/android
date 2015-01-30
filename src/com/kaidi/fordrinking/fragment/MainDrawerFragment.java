package com.kaidi.fordrinking.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.kaidi.fordrinking.AuthActivity;
import com.kaidi.fordrinking.MainActivity;
import com.kaidi.fordrinking.R;
import com.kaidi.fordrinking.model.User;
import com.kaidi.fordrinking.model.UserManager;

import java.io.*;
import java.net.URL;

import static com.kaidi.fordrinking.util.Constants.*;

/**
 * Author: kaidi
 * Date: 01/28, 2015
 */
public class MainDrawerFragment extends Fragment {
    private MainActivity activity;

    private LinearLayout homeItem;
    private LinearLayout messageItem;
    private LinearLayout exploreItem;
    private LinearLayout logoutItem;

    private ImageView avatarImageView;
    private TextView  usernameView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.drawer_after, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        avatarImageView = (ImageView) getActivity().findViewById(R.id.image_gravatar);
        usernameView    = (TextView)  getActivity().findViewById(R.id.text_username);

        homeItem = (LinearLayout) getActivity().findViewById(R.id.main_home_item);
        messageItem = (LinearLayout) getActivity().findViewById(R.id.main_message_item);
        exploreItem = (LinearLayout) getActivity().findViewById(R.id.main_explore_item);
        logoutItem = (LinearLayout) getActivity().findViewById(R.id.main_logout_item);

        homeItem.setOnClickListener(new MainItemClickListener());
        messageItem.setOnClickListener(new MainItemClickListener());
        exploreItem.setOnClickListener(new MainItemClickListener());

        logoutItem.setOnClickListener(new LogoutItemClickListener());

        ShowUserInfo();
    }

    private void ShowUserInfo() {
        String shareFileName = getResources().getString(R.string.share_preference_file);
        SharedPreferences sharedPref = getActivity().getSharedPreferences(shareFileName, Context.MODE_PRIVATE);

        String uidsKey = getResources().getString(R.string.key_auth_uid);
        String uids = sharedPref.getString(uidsKey, "");

        final int uid = Integer.parseInt(uids.split(",")[0]);

        User user = UserManager.getUser(uid);

        final String username  = user.getUsername();
        final String avatarURL = user.getAvatar();

        final Bitmap bitmap;

        usernameView.setText(username);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x123) {
                   // avatarImageView.setImageResource();
                }

            }
        };

        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(avatarURL);
                    InputStream inputStream = url.openStream();
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        File sdCardDir = Environment.getExternalStorageDirectory();

                        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(
                                sdCardDir.getCanonicalPath() + ""));

                        byte[] buff = new byte[1024];
                        int hasRead = 0;
                        while ((hasRead = inputStream.read(buff)) > 0) {
                            bos.write(buff, 0, hasRead);
                        }

                        inputStream.close();
                        bos.close();

                    } else {
                        /*OutputStream outputStream = getActivity().openFileOutput(username + uid + ".png",
                                Context.MODE_WORLD_READABLE);
                        byte[] buff = new byte[1024];
                        int hasRead = 0;
                        while ((hasRead = inputStream.read(buff)) > 0) {
                            outputStream.write(buff, 0, hasRead);
                        }
                        inputStream.close();
                        outputStream.close();*/
                    }


                    handler.sendEmptyMessage(0x123);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }

    private class MainItemClickListener implements View.OnClickListener {
        @Override
        public void onClick(View vew) {
            FragmentManager fm = getFragmentManager();
            // 开启Fragment事务
            FragmentTransaction transaction = fm.beginTransaction();
            activity = (MainActivity) getActivity();

            switch (vew.getId()) {
                case R.id.main_home_item:
                    if (activity.getHomeFragment() == null) {
                        HomeFragment homeFragment = new HomeFragment();
                        activity.setHomeFragment(homeFragment);
                    }
                    transaction.replace(R.id.main_content, activity.getHomeFragment());
                    activity.getDrawerLayout().closeDrawer(Gravity.START);
                    activity.setDrawerTabState(DRAWER_HOME_STATE);
                    break;

                case R.id.main_message_item:
                    if (activity.getMessageFragment() == null) {
                        MessageFragment messageFragment = new MessageFragment();
                        activity.setMessageFragment(messageFragment);
                    }
                    transaction.replace(R.id.main_content, activity.getMessageFragment());
                    activity.getDrawerLayout().closeDrawer(Gravity.START);
                    activity.setDrawerTabState(DRAWER_MESSAGE_STATE);
                    break;

                case R.id.main_explore_item:
                    if (activity.getExporeFragment() == null) {
                        ExploreFragment exploreFragment = new ExploreFragment();
                        activity.setExporeFragment(exploreFragment);
                    }
                    transaction.replace(R.id.main_content, activity.getExporeFragment());
                    activity.getDrawerLayout().closeDrawer(Gravity.START);
                    activity.setDrawerTabState(DRAWER_EXPLORE_STATE);
                    break;
            }
            // transaction.addToBackStack();
            // 事务提交
            transaction.commit();
        }
    }

    private class LogoutItemClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String shareFileName = getResources().getString(R.string.share_preference_file);
            SharedPreferences sharedPref = getActivity().getSharedPreferences(shareFileName, Context.MODE_PRIVATE);

            String uidsKey = getResources().getString(R.string.key_auth_uid);

            String uids = "";

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(uidsKey, uids);
            editor.apply();

            Intent intent = new Intent(getActivity(), AuthActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }
}
















