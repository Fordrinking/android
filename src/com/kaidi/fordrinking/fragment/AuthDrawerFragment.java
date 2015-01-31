package com.kaidi.fordrinking.fragment;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.drawer_before, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loginItem = (LinearLayout) getActivity().findViewById(R.id.auth_login_item);
        signupItem = (LinearLayout) getActivity().findViewById(R.id.auth_signup_item);
        exploreItem = (LinearLayout) getActivity().findViewById(R.id.auth_explore_item);

        loginItem.setOnClickListener(new AuthItemClickListener());
        signupItem.setOnClickListener(new AuthItemClickListener());
        exploreItem.setOnClickListener(new AuthItemClickListener());
    }

    private class AuthItemClickListener implements View.OnClickListener {
        @Override
        public void onClick(View vew) {
            FragmentManager fm = getFragmentManager();
            // 开启Fragment事务
            FragmentTransaction transaction = fm.beginTransaction();
            activity = (AuthActivity)getActivity();

            switch (vew.getId()) {
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
                    if (activity.getExporeFragment() == null) {
                        ExploreFragment exporeFragment = new ExploreFragment();
                        activity.setExporeFragment(exporeFragment);
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
}















