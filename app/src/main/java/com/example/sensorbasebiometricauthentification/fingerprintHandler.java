package com.example.sensorbasebiometricauthentification;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;


@RequiresApi(api = Build.VERSION_CODES.M)
class fingerprintHandler<cryptoObject> extends FingerprintManager.AuthenticationCallback {


    public int  count;

    private Context context;
   public Handler mHandler = new Handler();

    public fingerprintHandler(Context context) {


        this.context = context;
    }

    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject) {

CancellationSignal cancellationSignal = new CancellationSignal();

            fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
      }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {

        this.update("There was an Auth Error. " + errString, false);

    }

    @Override
    public void onAuthenticationFailed() {

        this.update("Auth Failed. ", false);

    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

        this.update("Error: " + helpString, false);

    }

    public Runnable onAuthenticationSucceeded = new Runnable() {
        @Override

        public void run() {


                final databaseHelper db = new databaseHelper(fingerpage.mContext);
                update("you can acces now 1 ", true);

                boolean res = db.addvalue(fingerpage.xVal, fingerpage.yVal, fingerpage.zVal,databaseHelper.G_Id );
                if (res == true) {
                    Toast.makeText(context.getApplicationContext(), "Ok", Toast.LENGTH_LONG).show();


                } else {
                    Toast.makeText(context.getApplicationContext(), "No", Toast.LENGTH_LONG).show();
                }

            }

    };

    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        final int[] i = {0};
        final int[] finalI = {i[0]+1};
        new CountDownTimer(9000, 1000){
            public void onTick(long millisUntilFinished){
                Toast.makeText(context.getApplicationContext(),  String.valueOf(finalI[0]), Toast.LENGTH_LONG).show();
                ++finalI[0];
            }
            public  void onFinish(){
                Toast.makeText(context.getApplicationContext(),  "Finish", Toast.LENGTH_LONG).show();

            }
        }.start();
        for (i[0] = 0; i[0] < 10; i[0]++) {
            mHandler.postDelayed(onAuthenticationSucceeded, 9000);
        }

    }



    @SuppressLint("ResourceType")
    public void update(String s, final boolean b) {
        final int i=0;

    TextView paraLabel = (TextView) ((Activity) context).findViewById(R.id.paralabel);
    ImageView imageView = (ImageView) ((Activity) context).findViewById(R.id.fingerprintimage);
    paraLabel.setText(s);


    if (b == false) {

        paraLabel.setTextColor(ContextCompat.getColor(context, R.color.errorcolor));

    } else {
        paraLabel.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        imageView.setImageResource(R.mipmap.action_done2);
       }

    }




}
//mHandler.postDelayed(mToast,5000);





