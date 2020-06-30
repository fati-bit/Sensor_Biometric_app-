package com.example.sensorbasebiometricauthentification;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.Manifest;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import weka.classifiers.Classifier;


public class fingerpage extends AppCompatActivity implements SensorEventListener {
    private TextView mheadinglabel;
    private ImageView mfingerprintimage;
    private TextView mparalabel;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;   // var to know if lock is unable or not
    //--------------------accelerometre

    public Sensor mySensor;
    public SensorManager SM;
    public TextView xText;
    public TextView yText;
    public TextView zText;
    public TextView id_1;

    public static Context mContext;
    ListView lst;


    private Context context;

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
        return super.openOrCreateDatabase(name, mode, factory);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerpage);


        xText = (TextView) findViewById(R.id.xtext);
        yText = (TextView) findViewById(R.id.ytext);
        zText = (TextView) findViewById(R.id.ztext);
        id_1 = (TextView) findViewById(R.id.id);
        final String X = xText.getText().toString();
        final String Y = yText.getText().toString();
        final String Z = zText.getText().toString();


        mContext = getApplicationContext();

        lst = (ListView) findViewById(R.id.listView);
        try {
            Classifier  cls = (Classifier) weka.core.SerializationHelper.read(getAssets().open("wekaSTRIPPED"));
        } catch (Exception e) {
            e.printStackTrace();
        }

//----------------------ACCELEROMETRE -+-----------------------------------
        // Create our Sensor Manager
        SM = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Accelerometer Sensor
        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Register sensor Listener
        SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);


        //--------------------------------------------------------------------------

        mheadinglabel = (TextView) findViewById(R.id.headinglabel);
        mfingerprintimage = (ImageView) findViewById(R.id.fingerprintimage);
        mparalabel = (TextView) findViewById(R.id.paralabel);
        //----------------ACCELEROMETRE --------------

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // virifier le version d'android ...travail just pour marchmello ou les versions plus recents
            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE); // verifier le fingerprint scanner in mobile
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE); //lock

            if (!fingerprintManager.isHardwareDetected()) {
                mparalabel.setText("Fingerprint Scanner not detected in Device");// scanner not work
            } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) { //check the permission for app to use finger scanner
                mparalabel.setText("Permission not granted to use Fingerprint Scanner");
            } else if (!keyguardManager.isKeyguardSecure()) {
                mparalabel.setText("Add Lock to your Phone ");
            } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                mparalabel.setText("You should add atleast 1 Fingerprint to use this Feature");// check if there is anu fingerprint
            } else {
                mparalabel.setText("Place your Finger on Scanner to Access the App.");

                fingerprintHandler fingerprintHandler = new fingerprintHandler(this);
                fingerprintHandler.startAuth(fingerprintManager, null);
                showData();

            }
            }



            }







    public static String xVal;
    public static String yVal;
    public static String zVal;
    public static String id_user;


    @Override
    public void onSensorChanged(SensorEvent event) {



        xText.setText("X  " + event.values[0]);
        yText.setText("Y  " + event.values[1]);
        zText.setText("Z  " + event.values[2]);


        xVal = String.valueOf(event.values[0]);
        yVal = String.valueOf(event.values[1]);
        zVal = String.valueOf(event.values[2]);


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void showData() {
        databaseHelper db = new databaseHelper(mContext);
        ArrayList<String> listData = db.getAllrecord1();
        ArrayAdapter arayAdapter = new ArrayAdapter(fingerpage.this, android.R.layout.simple_list_item_1, listData);
        lst.setAdapter(arayAdapter);
        Log.d("sql error", "add values seccuss");

    }
}
