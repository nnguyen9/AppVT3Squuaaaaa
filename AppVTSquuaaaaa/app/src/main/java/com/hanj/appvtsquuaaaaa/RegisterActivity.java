package com.hanj.appvtsquuaaaaa;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.HashMap;

import retrofit.Call;

public class RegisterActivity extends Activity {
    private EditText firstNameText;
    private EditText lastNameText;
    private EditText phoneText;
    private EditText capIDText;
    private EditText passwordText;
    private EditText confirmPasswordText;
    private Button registerButton;
    private DivitServer server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        server = DivitServerAccess.getServer();

        firstNameText = (EditText) findViewById(R.id.firstNameText);
        lastNameText = (EditText) findViewById(R.id.lastNameText);
        phoneText = (EditText) findViewById(R.id.phoneText);
        capIDText = (EditText) findViewById(R.id.capIDText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        confirmPasswordText = (EditText) findViewById(R.id.confirmPasswordText);
        registerButton = (Button) findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean formFilled = true;
                Log.e("Register Activity", formFilled + ": Should be true");

                if (firstNameText.getText().toString().trim().equals("")) {
                    firstNameText.setBackgroundColor(Color.parseColor("#50FF0000"));
                    formFilled = false;
                }
                else
                {
                    firstNameText.setBackgroundColor(Color.parseColor("#FF00ceb6"));
                }
                if (lastNameText.getText().toString().trim().equals("")) {
                    lastNameText.setBackgroundColor(Color.parseColor("#50FF0000"));
                    formFilled = false;          }
                else
                {
                    lastNameText.setBackgroundColor(Color.parseColor("#FF00ceb6"));
                }
                if (phoneText.getText().toString().trim().equals("") || phoneText.getText().length() < 10) {
                    formFilled = false;
                    phoneText.setBackgroundColor(Color.parseColor("#50FF0000"));
                }
                else
                {
                    phoneText.setBackgroundColor(Color.parseColor("#FF00ceb6"));
                }
                if (capIDText.getText().toString().trim().equals("")) {
                    formFilled = false;
                    capIDText.setBackgroundColor(Color.parseColor("#50FF0000"));
                }
                else
                {
                    capIDText.setBackgroundColor(Color.parseColor("#FF00ceb6"));
                }

                if (passwordText.getText().toString().trim().equals("") || confirmPasswordText.getText().toString().trim().equals("")) {
                    formFilled = false;
                    passwordText.setBackgroundColor(Color.parseColor("#50FF0000"));
                    confirmPasswordText.setBackgroundColor(Color.parseColor("#50FF0000"));

                }
                else if (!passwordText.getText().toString().equals(confirmPasswordText.getText().toString())) {
                    formFilled = false;
                    passwordText.setBackgroundColor(Color.parseColor("#FF00ceb6"));
                    confirmPasswordText.setBackgroundColor(Color.parseColor("#50FF0000"));
                }
                else {
                    passwordText.setBackgroundColor(Color.parseColor("#FF00ceb6"));
                    confirmPasswordText.setBackgroundColor(Color.parseColor("#FF00ceb6"));
                }

                if (formFilled) {
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("first_name", firstNameText.getText().toString());
                    params.put("last_name", lastNameText.getText().toString());
                    params.put("phone", phoneText.getText().toString());
                    params.put("cap_id", capIDText.getText().toString());
                    params.put("password", passwordText.getText().toString());

                    new SignUpTask(params).execute();
                }
            }
        });
    }
    private class SignUpTask extends AsyncTask<Void, Void, User> {
        HashMap<String, String> param;

        public SignUpTask(HashMap<String, String> params) {
            this.param = params;
        }

        @Override
        protected User doInBackground(Void... params) {
            Call<User> userCall = server.signUpUser(param);
            User user = null;
            try {
                user = userCall.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return user;
        }

        @Override
        protected void onPostExecute(User user) {
            if (user == null) {

            } else {
                LocalProfile.setNameFirst(user.getFirst_name());
                LocalProfile.setNameLast(user.getLast_name());
                LocalProfile.setPhone(user.getPhone());
                LocalProfile.saveSettings();
                startActivity(new Intent(getApplicationContext(), ItemlistActivity.class));
            }
        }
    }
}
