package com.hanj.appvtsquuaaaaa;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.List;

import retrofit.Call;

public class MainActivity extends Activity {

    private Button loginButton;
    private EditText id;
    private EditText pw;
    private DivitServer server;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        LocalProfile.initialize(this);
        DivitServerAccess.initialize(this);
        server = DivitServerAccess.getServer();

        id = (EditText)findViewById(R.id.numberEditText);
        int maxLength = 10;
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(maxLength);
        id.setFilters(fArray);
        pw = (EditText)findViewById(R.id.pwordEditText);

        loginButton = (Button) findViewById(R.id.logInButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean wrongID = (id.getText().toString().trim().length() != 10);
                boolean wrongPW = (pw.getText().toString().trim().equals(""));
                if(wrongID)
                    id.setBackgroundColor(Color.RED);
                if(wrongPW)
                    pw.setBackgroundColor(Color.RED);
                if(!wrongID && !wrongPW) {
                    new AuthenticateTask(id.getText().toString(), pw.getText().toString()).execute();

                }


            }
        });
    }

    private class AuthenticateTask extends AsyncTask<Void, Void, User> {
        String phone;
        String pass;

        public AuthenticateTask(String id, String password) {
            phone = id;
            pass = password;
        }

        @Override
        protected User doInBackground(Void... params) {
            Call<User> userCall = server.signIn(phone, pass);
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
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            } else {
                LocalProfile.setNameFirst(user.getFirst_name());
                LocalProfile.setNameLast(user.getLast_name());
                LocalProfile.setPhone(user.getPhone());
                LocalProfile.saveSettings();
                startActivity(new Intent(getApplicationContext(), CameraActivity.class));
            }
        }
    }
}
