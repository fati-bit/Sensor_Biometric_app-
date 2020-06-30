package com.example.sensorbasebiometricauthentification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {
    EditText mTextUsername;
    EditText mTextPassword;
    Button mButtonLogin;
    Button mbutton;
    databaseHelper db;
    ViewGroup progressView;
    protected boolean isProgressShowing = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        db = new databaseHelper(this);
        mTextUsername = (EditText)findViewById(R.id.user);
        mTextPassword = (EditText)findViewById(R.id.pass);
        mButtonLogin = (Button)findViewById(R.id.login);
        mbutton = (Button) findViewById(R.id.button);
        mbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openSign();
                }
            });
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = mTextUsername.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                Boolean res = db.checkUser(user, pwd);
                if(res)
                {
                    Intent HomePage = new Intent(login.this,homepage.class);
                    startActivity(HomePage);
                }
                else
                {
                    Toast.makeText(login.this,"Login Error",Toast.LENGTH_SHORT).show();
                }
            }
        });

        }

    private void openSign() {
        Intent signIntent = new Intent(login.this,signup.class);
        startActivity(signIntent);
    }

}
