package com.example.sensorbasebiometricauthentification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class signup extends AppCompatActivity {
    EditText mTextUsername;
    EditText mTextEmail;
    EditText mTextPassword;
    EditText mTextCPassword;
    Button mbuttonRegistre;
    databaseHelper db;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        db = new databaseHelper(this);
        mTextUsername = (EditText)findViewById(R.id.r_user);
        mTextPassword = (EditText)findViewById(R.id.r_pass);
        mTextCPassword = (EditText)findViewById(R.id.r_conf);
        mTextEmail = (EditText)findViewById(R.id.r_email);
        mbuttonRegistre = (Button)findViewById(R.id.button_r);

        mbuttonRegistre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = mTextUsername.getText().toString().trim();
                String email = mTextEmail.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                String cnf_pwd = mTextCPassword.getText().toString().trim();


                if(pwd.equals(cnf_pwd)){
                    long val = db.addUser(user,pwd);
                    if(val > 0){
                        Toast.makeText(signup.this,"You have registered",Toast.LENGTH_SHORT).show();
                        Intent moveToLogin = new Intent(signup.this,login.class);
                        startActivity(moveToLogin);
                    }
                    else{
                        Toast.makeText(signup.this,"Registeration Error",Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(signup.this,"Password is not matching",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
