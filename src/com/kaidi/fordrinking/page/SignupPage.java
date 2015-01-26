package com.kaidi.fordrinking.page;

import com.kaidi.fordrinking.MainActivity;

/**
 * Author: kaidi
 * Date: 01/26, 2015
 */
public class SignupPage extends BasicPage{
    private MainActivity activity;

    private static SignupPage initialDrawer = null;

    private SignupPage(MainActivity activity) {
        this.activity = activity;
    }

    public static synchronized SignupPage getSignupPage(MainActivity activity){
        if(initialDrawer == null){
            initialDrawer = new SignupPage(activity);
        }
        return initialDrawer;
    }
}
