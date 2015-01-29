package com.kaidi.fordrinking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Author: kaidi
 * Date: 01/28, 2015
 */
public class StartActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }
}