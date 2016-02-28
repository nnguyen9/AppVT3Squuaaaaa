package com.hanj.appvtsquuaaaaa;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends Activity {
    private EditText firstNameText;
    private EditText lastNameText;
    private EditText phoneText;
    private EditText capIDText;
    private EditText passwordText;
    private EditText confirmPasswordText;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
                if (firstNameText.getText().toString().trim().equals("")) {
                    firstNameText.setBackgroundColor(Color.parseColor("#FFB1F7EF"));
                }
                else
                {
                    firstNameText.setBackgroundColor(Color.parseColor("#FF00ceb6"));
                }
                if (lastNameText.getText().toString().trim().equals("")) {
                    lastNameText.setBackgroundColor(Color.parseColor("#FFB1F7EF"));
                }
                else
                {
                    lastNameText.setBackgroundColor(Color.parseColor("#FF00ceb6"));
                }
                if (phoneText.getText().toString().trim().equals("") || phoneText.getText().length() < 10) {
                    phoneText.setBackgroundColor(Color.parseColor("#FFB1F7EF"));
                }
                else
                {
                    phoneText.setBackgroundColor(Color.parseColor("#FF00ceb6"));
                }
                if (capIDText.getText().toString().trim().equals("")) {
                    capIDText.setBackgroundColor(Color.parseColor("#FFB1F7EF"));
                }
                else
                {
                    capIDText.setBackgroundColor(Color.parseColor("#FF00ceb6"));
                }

                if (passwordText.getText().toString().trim().equals("") || confirmPasswordText.getText().toString().trim().equals("")) {
                    passwordText.setBackgroundColor(Color.parseColor("#FFB1F7EF"));
                    confirmPasswordText.setBackgroundColor(Color.parseColor("#FFB1F7EF"));

                }
                else if (passwordText != confirmPasswordText) {
                    passwordText.setBackgroundColor(Color.parseColor("#FF00ceb6"));
                    confirmPasswordText.setBackgroundColor(Color.parseColor("#FF00FFE1"));
                }
                else {
                    passwordText.setBackgroundColor(Color.parseColor("#FF00ceb6"));
                    confirmPasswordText.setBackgroundColor(Color.parseColor("#FF00ceb6"));
                }
            }
        });
    }

}
