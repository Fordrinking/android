package com.kaidi.fordrinking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * Author: kaidi
 * Date: 01/28, 2015
 */
public class StartActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String uidsKey = getResources().getString(R.string.auth_users_id);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String uids = sharedPref.getString(uidsKey, "");

        Intent intent;
        if (uids.equals("")) {
            intent = new Intent(this, AuthActivity.class);
        } else {
            intent = new Intent(this, MainActivity.class);
        }

        startActivity(intent);
        finish();
    }
}