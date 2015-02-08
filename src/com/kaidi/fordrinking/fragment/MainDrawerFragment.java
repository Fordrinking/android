package com.kaidi.fordrinking.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.*;
import android.util.Log;
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
    private LinearLayout helpItem;
    private LinearLayout settingItem;
    private LinearLayout feedbackItem;

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
        helpItem     = (LinearLayout) getActivity().findViewById(R.id.drawer_help);
        settingItem  = (LinearLayout) getActivity().findViewById(R.id.drawer_settings);
        feedbackItem = (LinearLayout) getActivity().findViewById(R.id.drawer_feedback);

        MainItemClickListener mainItemClickListener = new MainItemClickListener();

        homeItem.setOnClickListener(mainItemClickListener);
        messageItem.setOnClickListener(mainItemClickListener);
        exploreItem.setOnClickListener(mainItemClickListener);
        helpItem.setOnClickListener(mainItemClickListener);
        settingItem.setOnClickListener(mainItemClickListener);
        feedbackItem.setOnClickListener(mainItemClickListener);

        logoutItem.setOnClickListener(new LogoutItemClickListener());

        homeItem.setActivated(true);
        ShowUserInfo();
    }

    private void ShowUserInfo() {
        User currentUser = UserManager.getCurrentUser(getActivity());

        //final int    uid       = currentUser.getUid();
        final String username  = currentUser.getUsername();
        final String avatarURL = currentUser.getAvatar();

        usernameView.setText(username);

        String avatarPath = getActivity().getFilesDir().toString() + "/avatar.png";
        File file = new File(avatarPath);
        try {
            if(file.exists()) {
                FileInputStream fis = new FileInputStream(avatarPath);
                Bitmap bitmap = BitmapFactory.decodeStream(fis);
                avatarImageView.setImageBitmap(bitmap);
            } else {
                Log.e("network", "Get Avatar From Network");
                new DownloadImageTask().execute(avatarURL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... urlStr) {
            try {
                URL url = new URL(urlStr[0]);
                InputStream inputStream = url.openStream();
                BufferedOutputStream bos = new BufferedOutputStream(
                        getActivity().openFileOutput("avatar.png", Context.MODE_PRIVATE));
                byte[] buff = new byte[1024];
                int hasRead = 0;
                while ((hasRead = inputStream.read(buff)) > 0) {
                    bos.write(buff, 0, hasRead);
                }
                inputStream.close();

                inputStream = url.openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                inputStream.close();
                bos.close();
                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap result) {
            avatarImageView.setImageBitmap(result);
        }
    }

    private class MainItemClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            homeItem.setActivated(false);
            messageItem.setActivated(false);
            exploreItem.setActivated(false);
            logoutItem.setActivated(false);
            helpItem.setActivated(false);
            settingItem.setActivated(false);
            feedbackItem.setActivated(false);

            view.setActivated(!view.isActivated());

            FragmentManager fm = getFragmentManager();
            // 开启Fragment事务
            FragmentTransaction transaction = fm.beginTransaction();
            activity = (MainActivity) getActivity();

            switch (view.getId()) {
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

                case R.id.drawer_help:
                    if (activity.getHelpFragment() == null) {
                        HelpFragment helpFragment = new HelpFragment();
                        activity.setHelpFragment(helpFragment);
                    }
                    transaction.replace(R.id.main_content, activity.getHelpFragment());
                    activity.getDrawerLayout().closeDrawer(Gravity.START);
                    activity.setDrawerTabState(DRAWER_HELP_STATE);
                    break;

                case R.id.drawer_settings:
                    if (activity.getSettingFragment() == null) {
                        SettingFragment settingFragment = new SettingFragment();
                        activity.setSettingFragment(settingFragment);
                    }
                    transaction.replace(R.id.main_content, activity.getSettingFragment());
                    activity.getDrawerLayout().closeDrawer(Gravity.START);
                    activity.setDrawerTabState(DRAWER_SETTTINGS_STATE);
                    break;

                case R.id.drawer_feedback:
                    if (activity.getFeedbackFragment() == null) {
                        FeedbackFragment feedbackFragment = new FeedbackFragment();
                        activity.setFeedbackFragment(feedbackFragment);
                    }
                    transaction.replace(R.id.main_content, activity.getFeedbackFragment());
                    activity.getDrawerLayout().closeDrawer(Gravity.START);
                    activity.setDrawerTabState(DRAWER_FEEDBACK_STATE);
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
            User currentUser = UserManager.getCurrentUser(getActivity());
            UserManager.removeUser(currentUser);

            String shareFileName = getResources().getString(R.string.share_preference_file);
            SharedPreferences sharedPref = getActivity().getSharedPreferences(shareFileName, Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(getResources().getString(R.string.share_pref_curr_uid), -1);
            editor.apply();

            String avatarPath = getActivity().getFilesDir().toString() + "/avatar.png";
            File file = new File(avatarPath);
            if (file.delete()) {
                Log.e("Logout: ", "Delete User Avatar");
            }

            Intent intent = new Intent(getActivity(), AuthActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }
}
















