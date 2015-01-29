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

        String shareFileName = getResources().getString(R.string.share_preference_file);
        SharedPreferences sharedPref = getSharedPreferences(shareFileName, Context.MODE_PRIVATE);

        String uidsKey = getResources().getString(R.string.key_auth_uid);
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