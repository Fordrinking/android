package com.kaidi.fordrinking.fragment;

//import android.app.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.kaidi.fordrinking.AuthActivity;
import com.kaidi.fordrinking.MainActivity;
import com.kaidi.fordrinking.R;
import com.kaidi.fordrinking.model.User;
import com.kaidi.fordrinking.util.JsonUtil;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: kaidi
 * Date: 01/28, 2015
 */
public class SignupFragment extends Fragment {

    private AuthActivity activity;

    private TextView errorPromptText;
    private EditText emailEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;

    private Button   signupButton;
    private int      signupBtnState;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_auth_signup, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        activity         = (AuthActivity) getActivity();
        errorPromptText  = (TextView) activity.findViewById(R.id.signup_error_prompt);
        emailEditText    = (EditText) activity.findViewById(R.id.signup_email_input);
        usernameEditText = (EditText) activity.findViewById(R.id.signup_username_input);
        passwordEditText = (EditText) activity.findViewById(R.id.signup_password_input);
        signupButton     = (Button)   activity.findViewById(R.id.signup_btn);
        signupBtnState   = 0;

        emailEditText.addTextChangedListener(new EmailEditTextChangeListener());
        usernameEditText.addTextChangedListener(new UsernameEditTextChangeListener());
        passwordEditText.addTextChangedListener(new PasswordEditTextChangeListener());
        signupButton.setOnClickListener(new SignupButtonClickListener());
    }

    private class EmailEditTextChangeListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (usernameEditText.getText().toString().trim().equals("")) {
                signupButton.setBackgroundColor(Color.rgb(255, 155, 155));
                return;
            }
            if (passwordEditText.getText().toString().trim().equals("")) {
                signupButton.setBackgroundColor(Color.rgb(255, 155, 155));
                return;
            }
            if (emailEditText.getText().toString().trim().equals("")) {
                signupButton.setBackgroundColor(Color.rgb(255, 155, 155));
            } else {
                signupButton.setBackgroundColor(Color.rgb(255, 81, 81));
                signupBtnState = 1;
            }
        }
    }

    private class UsernameEditTextChangeListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (emailEditText.getText().toString().trim().equals("")) {
                signupButton.setBackgroundColor(Color.rgb(255, 155, 155));
                return;
            }
            if (passwordEditText.getText().toString().trim().equals("")) {
                signupButton.setBackgroundColor(Color.rgb(255, 155, 155));
                return;
            }
            if (usernameEditText.getText().toString().trim().equals("")) {
                signupButton.setBackgroundColor(Color.rgb(255, 155, 155));
            } else {
                signupButton.setBackgroundColor(Color.rgb(255, 81, 81));
                signupBtnState = 1;
            }
        }
    }

    private class PasswordEditTextChangeListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (emailEditText.getText().toString().trim().equals("")) {
                signupButton.setBackgroundColor(Color.rgb(255, 155, 155));
                return;
            }
            if (usernameEditText.getText().toString().trim().equals("")) {
                signupButton.setBackgroundColor(Color.rgb(255, 155, 155));
                return;
            }
            if (passwordEditText.getText().toString().trim().equals("")) {
                signupButton.setBackgroundColor(Color.rgb(255, 155, 155));
            } else {
                signupButton.setBackgroundColor(Color.rgb(255, 81, 81));
                signupBtnState = 1;
            }
        }
    }

    private class SignupButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            if (signupBtnState == 0) {
                return;
            }
            final String email = emailEditText.getText().toString().trim();
            final String password = passwordEditText.getText().toString().trim();
            final String username = usernameEditText.getText().toString().trim();

            //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
            Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
            Matcher m = p.matcher(email);
            if (!m.matches()) {
                errorPromptText.setText("Invalid Email Format");
                return;
            }
            if (username.length() < 4 || username.length() > 20) {
                errorPromptText.setText("Username Length: 4~16");
                return;
            }
            if (password.length() < 4 || password.length() > 16) {
                errorPromptText.setText("Password Length: 4~16");
                return;
            }

            final HttpClient httpClient = activity.getHttpClient();
            new Thread() {
                @Override
                public void run() {
                    try {
                        HttpPost httpPost = new HttpPost(activity.getResources().getString(R.string.url_signup));
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("email", email));
                        params.add(new BasicNameValuePair("username", username));
                        params.add(new BasicNameValuePair("password", password));
                        httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                        HttpResponse httpResponse = httpClient.execute(httpPost);
                        if (httpResponse.getStatusLine().getStatusCode() == 200) {
                            String msg = EntityUtils.toString(httpResponse.getEntity());
                            Looper.prepare();
                            dealResponse(msg);
                            Looper.loop();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                private void dealResponse(String msg) {
                    if (msg.equals("email-exist")) {
                        Toast.makeText(activity, "Email exist, please Log in", Toast.LENGTH_SHORT).show();
                    } else if (msg.equals("user-exist")) {
                        Toast.makeText(activity, "Username exit, change another", Toast.LENGTH_SHORT).show();
                    } else  {
                        User user = JsonUtil.parseUser(msg);

                        String shareFileName = getResources().getString(R.string.share_preference_file);
                        SharedPreferences sharedPref = getActivity().getSharedPreferences(shareFileName, Context.MODE_PRIVATE);

                        String uidsKey = getResources().getString(R.string.key_auth_uid);

                        String uids = sharedPref.getString(uidsKey, "");
                        uids += user.getUid() + ",";

                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString(getString(R.string.key_auth_uid), uids);
                        editor.apply();

                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                }
            }.start();
        }
    }
}





















