package com.example.alsultan.soultomusica;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.parse.ParseObject;


/**
 * Created by Al Sultan on 4/26/2016.
 */
public class signupclass extends AppCompatActivity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragmen_signup);



        final EditText name1=(EditText)findViewById(R.id.thename);
        final EditText mail1=(EditText) findViewById(R.id.themail);
        final EditText pass1=(EditText) findViewById(R.id.thepass);
        ImageButton btn=(ImageButton) findViewById(R.id.signupme);



        //obtaining user phone number - as for permition
        //at runtie ask for permitions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(this)) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_SMS}, 2909);
            } else {
                // continue with your code
            }
        } else {
            // continue with your code
        }

        //obtaining phone number
        TelephonyManager tMgr =(TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        final String mPhoneNumber = tMgr.getLine1Number();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                ParseObject testObject = new ParseObject("ClientData");
                testObject.put("FullName", name1.getText().toString());
                testObject.put("Email", mail1.getText().toString());
                testObject.put("Password", pass1.getText().toString());

                //obtaining pone number

                testObject.put("PhoneNumber", mPhoneNumber);

                testObject.saveInBackground();


                //User registered go To Pag
                Intent i = new Intent(getApplicationContext(), starting.class);
                startActivity(i);
            }
        });



        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "signupclass Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.alsultan.soultomusica/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "signupclass Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.alsultan.soultomusica/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
