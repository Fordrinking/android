package com.kaidi.fordrinking.page;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.kaidi.fordrinking.MainActivity;
import com.kaidi.fordrinking.R;

/**
 * Author: kaidi
 * Date: 01/26, 2015
 */
public class SignupPage extends BasicPage{
    private MainActivity activity;

    private TextView errorPrompt;
    private EditText emailEdit;
    private EditText usernameEdit;
    private EditText passwdEdit;

    private Button   signupBtn;

    private static SignupPage initialDrawer = null;

    private SignupPage(MainActivity activity) {
        this.activity = activity;
        InitControls();
        RegisterEvent();
    }

    public static synchronized SignupPage getSignupPage(MainActivity activity){
        if(initialDrawer == null){
            initialDrawer = new SignupPage(activity);
        }
        return initialDrawer;
    }

    private void InitControls() {
        errorPrompt  = (TextView) activity.findViewById(R.id.signup_error_prompt);
        emailEdit    = (EditText) activity.findViewById(R.id.signup_email_input);
        usernameEdit = (EditText) activity.findViewById(R.id.signup_username_input);
        passwdEdit   = (EditText) activity.findViewById(R.id.signup_password_input);
        signupBtn    = (Button)   activity.findViewById(R.id.signup_btn);
    }

    private void RegisterEvent() {
        emailEdit.addTextChangedListener(new EmailEditListener());
        signupBtn.setOnClickListener(new SignupBtnListener());
    }

    private class SignupBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (emailEdit.getText().toString().trim().equals("")) {
                errorPrompt.setText("Please Enter Email !");
                return;
            }
            if (usernameEdit.getText().toString().trim().equals("")) {
                errorPrompt.setText("Please Enter Username !");
                return;
            }
            if (passwdEdit.getText().toString().trim().equals("")) {
                errorPrompt.setText("Please Enter Password !");
                return;
            }
        }
    }

    private class EmailEditListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
           // Toast.makeText(activity.getApplication(), "before", Toast.LENGTH_SHORT).show();
            errorPrompt.setText("");
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            //Toast.makeText(activity.getApplication(), "on change", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void afterTextChanged(Editable editable) {
           // Toast.makeText(activity.getApplication(), "after", Toast.LENGTH_SHORT).show();
        }
    }
}












