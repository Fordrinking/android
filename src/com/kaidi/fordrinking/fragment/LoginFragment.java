package com.kaidi.fordrinking.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.kaidi.fordrinking.model.UserManager;
import com.kaidi.fordrinking.util.JsonUtil;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: kaidi
 * Date: 01/28, 2015
 */
public class LoginFragment extends Fragment {
    private AuthActivity activity;

    private TextView errorPromptText;
    private EditText emailEditText;
    private EditText passwordEditText;

    private Button   loginButton;
    private int      loginBtnState;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_auth_login, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        activity         = (AuthActivity) getActivity();
        errorPromptText  = (TextView) activity.findViewById(R.id.login_error_prompt);
        emailEditText    = (EditText) activity.findViewById(R.id.login_email_input);
        passwordEditText = (EditText) activity.findViewById(R.id.login_password_input);
        loginButton      = (Button)   activity.findViewById(R.id.login_btn);
        loginBtnState    = 0;

        emailEditText.addTextChangedListener(new EmailEditTextChangeListener());
        passwordEditText.addTextChangedListener(new PasswordEditTextChangeListener());
        loginButton.setOnClickListener(new SignupButtonClickListener());
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
            if (passwordEditText.getText().toString().trim().equals("")) {
                loginButton.setBackgroundColor(Color.rgb(255, 155, 155));
                return;
            }
            if (emailEditText.getText().toString().trim().equals("")) {
                loginButton.setBackgroundColor(Color.rgb(255, 155, 155));
            } else {
                loginButton.setBackgroundColor(Color.rgb(255, 81, 81));
                loginBtnState = 1;
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
                loginButton.setBackgroundColor(Color.rgb(255, 155, 155));
                return;
            }
            if (passwordEditText.getText().toString().trim().equals("")) {
                loginButton.setBackgroundColor(Color.rgb(255, 155, 155));
            } else {
                loginButton.setBackgroundColor(Color.rgb(255, 81, 81));
                loginBtnState = 1;
            }
        }
    }

    private class SignupButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            if (loginBtnState == 0) {
                return;
            }
            final String email    = emailEditText.getText().toString().trim();
            final String password = passwordEditText.getText().toString().trim();

            final HttpClient httpClient = activity.getHttpClient();
            new Thread() {
                @Override
                public void run() {
                    try {
                        HttpPost httpPost = new HttpPost(activity.getResources().getString(R.string.url_login));
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("loginMail", email));
                        params.add(new BasicNameValuePair("loginPass", password));
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
                    if (msg.equals("no-email")) {
                        Toast.makeText(activity, "Mail Not Exist", Toast.LENGTH_SHORT).show();
                    } else if (msg.equals("wrong-password")) {
                        Toast.makeText(activity, "Wrong Password", Toast.LENGTH_SHORT).show();
                    } else  {
                        User user = JsonUtil.parseUser(msg);
                        UserManager.addUser(user);
                        UserManager.switchToCurrent(getActivity(), user.getUid());

                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                }
            }.start();
        }
    }
}
