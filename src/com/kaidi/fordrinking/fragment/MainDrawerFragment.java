package com.kaidi.fordrinking.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.kaidi.fordrinking.AuthActivity;
import com.kaidi.fordrinking.MainActivity;
import com.kaidi.fordrinking.R;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.drawer_after, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        homeItem = (LinearLayout) getActivity().findViewById(R.id.main_home_item);
        messageItem = (LinearLayout) getActivity().findViewById(R.id.main_message_item);
        exploreItem = (LinearLayout) getActivity().findViewById(R.id.main_explore_item);
        logoutItem = (LinearLayout) getActivity().findViewById(R.id.main_logout_item);

        homeItem.setOnClickListener(new MainItemClickListener());
        messageItem.setOnClickListener(new MainItemClickListener());
        exploreItem.setOnClickListener(new MainItemClickListener());

        logoutItem.setOnClickListener(new LogoutItemClickListener());
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
                    transaction.replace(R.id.auth_content, activity.getMessageFragment());
                    activity.getDrawerLayout().closeDrawer(Gravity.START);
                    activity.setDrawerTabState(DRAWER_SIGNUP_STATE);
                    break;

                case R.id.main_explore_item:
                    if (activity.getExporeFragment() == null) {
                        ExploreFragment exploreFragment = new ExploreFragment();
                        activity.setExporeFragment(exploreFragment);
                    }
                    transaction.replace(R.id.auth_content, activity.getExporeFragment());
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
















