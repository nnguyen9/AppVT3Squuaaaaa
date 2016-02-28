package com.hanj.appvtsquuaaaaa;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
        private Button loginButton;
        private EditText id;
        private EditText pw;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home);

            id = (EditText)findViewById(R.id.numberEditText);
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
                    if(!wrongID && !wrongPW)
                        startActivity(new Intent(getApplicationContext(), ItemlistActivity.class));
                }
            });
        }
}
