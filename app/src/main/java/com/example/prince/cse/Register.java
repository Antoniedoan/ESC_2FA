package com.example.prince.cse;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.SecureRandom;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static android.Manifest.permission.SEND_SMS;

public class Register extends AppCompatActivity {


    private BackgroundTask backgroundTask = null;

    EditText inputName;
    EditText inputEmail;
    EditText inputPassword;
    EditText inputConfirmPassword;


    Button buttonRegister;


    public static String pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputName = (EditText) findViewById(R.id.editName);
        inputEmail = (EditText) findViewById(R.id.editEmail);
        inputPassword = (EditText) findViewById(R.id.editPassword);
        inputConfirmPassword = (EditText) findViewById(R.id.editConfirmPassword);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                attemptSendEmail();

            }
        });

    }

    private void attemptSendEmail() {
        if (backgroundTask != null) {
            return;
        }

        // Reset errors.
        inputName.setError(null);
        inputEmail.setError(null);
        inputPassword.setError(null);
        inputConfirmPassword.setError(null);

        // Store values at the time of the login attempt.
        String name = inputName.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmPassword = inputConfirmPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid full name.
        if (TextUtils.isEmpty(name)) {
            inputName.setError(getString(R.string.error_field_required));
            focusView = inputName;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            inputEmail.setError(getString(R.string.error_field_required));
            focusView = inputEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            inputEmail.setError(getString(R.string.error_invalid_email));
            focusView = inputEmail;
            cancel = true;
        }


        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            inputPassword.setError(getString(R.string.error_invalid_password));
            focusView = inputPassword;
            cancel = true;
        }

        if (TextUtils.isEmpty(confirmPassword) || !isPasswordValid(confirmPassword)) {
            inputConfirmPassword.setError(getString(R.string.error_invalid_password));
            focusView = inputConfirmPassword;
            cancel = true;
        }

        //check if password and confirm password is the same

        if (!(password.equals(confirmPassword))) {

            inputConfirmPassword.setError(getString(R.string.password_not_matching));
            focusView = inputConfirmPassword;
            cancel = true;

        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            //sendEmail();


            Intent intent = new Intent(getApplicationContext(), OTPVerification.class);
            intent.putExtra("name",inputName.getText().toString());
            intent.putExtra("password",inputPassword.getText().toString());
            intent.putExtra("email",inputEmail.getText().toString());
            startActivity(intent);


        }
    }



    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
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

    private void sendEmail(){
//        final String to = inputEmail.getText().toString();
//        final String from = "antoniedoan@gmail.com";
//        final String pw = "1234abcd!@#$";
//
//
//        Properties properties = System.getProperties();
//        properties.put("mail.smtp.starttls.enable", "true");
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.host", "smtp.gmail.com");
//        properties.put("mail.smtp.port", "587");
//
//        Session session = Session.getInstance(properties, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(to, pw);
//            }
//        });
//
//        try{
//            MimeMessage message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(from));
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//
//            message.setSubject("Verification code for account activation");
//            message.setText("Please key in this code to activate your account: " + generateOTP());
//            Transport.send(message);
//            System.out.println("Message sent successfully");
//
//        } catch (MessagingException e){
//            throw new RuntimeException(e);
//        }

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
            Log.i("Email sent successfully", "");
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

    }

}
