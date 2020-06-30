package com.example.sensorbasebiometricauthentification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        ImageView fingerbutton = (ImageView) findViewById(R.id.empreint);
        fingerbutton.bringToFront();
        fingerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(homepage.this,fingerpage.class);
                startActivity(myIntent);
            }
        });
        TextView fingertxt = (TextView) findViewById(R.id.fin);
        fingertxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(homepage.this,fingerpage.class);
                startActivity(myIntent);
            }
        });
        ImageView voicebutton = (ImageView) findViewById(R.id.voice);
        voicebutton.bringToFront();
        voicebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(homepage.this,voicepage.class);
                startActivity(myIntent);
            }
        });
        TextView voicetxt = (TextView) findViewById(R.id.fin2);
        voicetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(homepage.this,voicepage.class);
                startActivity(myIntent);
            }
        });
    }
}
