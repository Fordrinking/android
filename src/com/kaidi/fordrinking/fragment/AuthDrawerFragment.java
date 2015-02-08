package com.kaidi.fordrinking.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.kaidi.fordrinking.AuthActivity;
import com.kaidi.fordrinking.R;

import static com.kaidi.fordrinking.util.Constants.*;


public class AuthDrawerFragment extends Fragment {

    private AuthActivity activity;

    private LinearLayout loginItem;
    private LinearLayout signupItem;
    private LinearLayout exploreItem;

    private LinearLayout helpItem;
    private LinearLayout settingItem;
    private LinearLayout feedbackItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.drawer_before, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loginItem    = (LinearLayout) getActivity().findViewById(R.id.auth_login_item);
        signupItem   = (LinearLayout) getActivity().findViewById(R.id.auth_signup_item);
        exploreItem  = (LinearLayout) getActivity().findViewById(R.id.auth_explore_item);
        helpItem     = (LinearLayout) getActivity().findViewById(R.id.drawer_help);
        settingItem  = (LinearLayout) getActivity().findViewById(R.id.drawer_settings);
        feedbackItem = (LinearLayout) getActivity().findViewById(R.id.drawer_feedback);

        AuthItemClickListener authItemClickListener = new AuthItemClickListener();

        loginItem.setOnClickListener(authItemClickListener);
        signupItem.setOnClickListener(authItemClickListener);
        exploreItem.setOnClickListener(authItemClickListener);
        helpItem.setOnClickListener(authItemClickListener);
        settingItem.setOnClickListener(authItemClickListener);
        feedbackItem.setOnClickListener(authItemClickListener);
        
        exploreItem.setActivated(true);
    }

    private class AuthItemClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            loginItem.setActivated(false);
            signupItem.setActivated(false);
            exploreItem.setActivated(false);
            helpItem.setActivated(false);
            settingItem.setActivated(false);
            feedbackItem.setActivated(false);
            view.setActivated(!view.isActivated());
            FragmentManager fm = getFragmentManager();
            // 开启Fragment事务
            FragmentTransaction transaction = fm.beginTransaction();
            activity = (AuthActivity)getActivity();

            switch (view.getId()) {
                case R.id.auth_login_item:
                    if (activity.getLoginFragment() == null) {
                        LoginFragment loginFragment = new LoginFragment();
                        activity.setLoginFragment(loginFragment);
                    }
                    transaction.replace(R.id.auth_content, activity.getLoginFragment());
                    activity.getDrawerLayout().closeDrawer(Gravity.START);
                    activity.setDrawerTabState(DRAWER_LOGIN_STATE);
                    break;

                case R.id.auth_signup_item:
                    if (activity.getSignupFragment() == null) {
                        SignupFragment signupFragment = new SignupFragment();
                        activity.setSignupFragment(signupFragment);
                    }
                    transaction.replace(R.id.auth_content, activity.getSignupFragment());
                    activity.getDrawerLayout().closeDrawer(Gravity.START);
                    activity.setDrawerTabState(DRAWER_SIGNUP_STATE);
                    break;

                case R.id.auth_explore_item:
                    if (activity.getExploreFragment() == null) {
                        ExploreFragment exploreFragment = new ExploreFragment();
                        activity.setExploreFragment(exploreFragment);
                    }
                    transaction.replace(R.id.auth_content, activity.getExploreFragment());
                    activity.getDrawerLayout().closeDrawer(Gravity.START);
                    activity.setDrawerTabState(DRAWER_EXPLORE_STATE);
                    break;

                case R.id.drawer_help:
                    if (activity.getHelpFragment() == null) {
                        HelpFragment helpFragment = new HelpFragment();
                        activity.setHelpFragment(helpFragment);
                    }
                    transaction.replace(R.id.auth_content, activity.getHelpFragment());
                    activity.getDrawerLayout().closeDrawer(Gravity.START);
                    activity.setDrawerTabState(DRAWER_HELP_STATE);
                    break;

                case R.id.drawer_settings:
                    if (activity.getSettingFragment() == null) {
                        SettingFragment settingFragment = new SettingFragment();
                        activity.setSettingFragment(settingFragment);
                    }
                    transaction.replace(R.id.auth_content, activity.getSettingFragment());
                    activity.getDrawerLayout().closeDrawer(Gravity.START);
                    activity.setDrawerTabState(DRAWER_SETTTINGS_STATE);
                    break;

                case R.id.drawer_feedback:
                    if (activity.getFeedbackFragment() == null) {
                        FeedbackFragment feedbackFragment = new FeedbackFragment();
                        activity.setFeedbackFragment(feedbackFragment);
                    }
                    transaction.replace(R.id.auth_content, activity.getFeedbackFragment());
                    activity.getDrawerLayout().closeDrawer(Gravity.START);
                    activity.setDrawerTabState(DRAWER_FEEDBACK_STATE);
                    break;
            }
            
            // transaction.addToBackStack();
            // 事务提交
            transaction.commit();
        }
    }
}















