package com.example.prince.cse;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.SecureRandom;
import java.util.Random;
import static android.Manifest.permission.SEND_SMS;

/**
 * Created by doanthanh on 3/4/18.
 */

public class OTPVerification extends AppCompatActivity{
    private BackgroundTask backgroundTask = null;


    EditText inputOTP;

    Button buttonVerify;

    static String pin;
    String name;
    String email;
    String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);


        String[] to = {"esc.kyc1@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "OTP VERIFICATION");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Please enter this OTP: "+generateOTP());
        try{
            startActivity(Intent.createChooser(emailIntent, "Sending email..."));
            //setContentView(R.layout.activity_verify_email);
            //Log.i("Email sent successfully", "");
        } catch (ActivityNotFoundException ex){

        }
//        try {
//            String phoneNo = "6586178715";
//
//            SmsManager sms = SmsManager.getDefault();
//            sms.sendTextMessage(phoneNo, null, generateOTP(), null, null);
//            Toast.makeText(this, "Sent", Toast.LENGTH_SHORT).show();
//
////            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
////            sendIntent.putExtra("sms_body", generateOTP());
////            sendIntent.setType("vnd.android-dir/mms-sms");
////            startActivity(sendIntent);
//
//        } catch (Exception e) {
//            Toast.makeText(getApplicationContext(),
//                    "SMS failed, please try again later!",
//                    Toast.LENGTH_LONG).show();
//            e.printStackTrace();
//        }


        inputOTP = (EditText) findViewById(R.id.verifiedOTP);
        final String enteredOTP =  inputOTP.getText().toString();


        buttonVerify = (Button) findViewById(R.id.buttonVerify);

        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (enteredOTP.equals(pin)){
                    attemptRegister();
                } else {
                    inputOTP.setError("OTP does not match.");
                }
            }
        });


        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");



    }


    private void attemptRegister(){

        String method = "register";
        backgroundTask = new BackgroundTask(getApplicationContext());
        backgroundTask.execute(method,name,email,password);

        finish();

    }


    private static String generateOTP() {

        String chars = "abcdefghijklmnopqrstuvwxyz"
                + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789!@%$%&^?|~'\"#+="
                + "\\*/.,:;[]()-_<>";

        final int PW_LENGTH = 6;
        Random rnd = new SecureRandom();
        StringBuilder pass = new StringBuilder();
        for (int i = 0; i < PW_LENGTH; i++)
            pass.append(chars.charAt(rnd.nextInt(chars.length())));

        pin = pass.toString();
        return pin;
    }
}
