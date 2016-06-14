package com.example.alsultan.soultomusica;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Al Sultan on 4/25/2016.
 */


public class fragmentlogin extends AppCompatActivity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.fragment_login);
        setContentView(R.layout.fragment_login);

        ImageButton btnlogn=(ImageButton) findViewById(R.id.loginbtn);
       final EditText name2=(EditText) findViewById(R.id.loginusername);
        final EditText namepass2=(EditText) findViewById(R.id.loginuserpass);
        final TextView a=(TextView) findViewById(R.id.textView);



        /*
        btnlogn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                a.setText("Login...");
                ParseQuery<ParseObject> query = ParseQuery.getQuery("ClientData");
                query.whereEqualTo("FullName",name2);
                query.whereEqualTo("Password",namepass2);
                query.findInBackground(new FindCallback() {
                    @Override
                    public void done(List objects, ParseException e) {
                        if(e==null){
                            //go to user page
                            TextView a=(TextView) findViewById(R.id.textView);
                            a.setText("User Exit");

                            Intent i = new Intent(getApplicationContext(), starting.class);
                            startActivity(i);

                        }
                        else{
                            //user does not exist

                            a.setText("User Dont Exit");
                        }
                    }


                });
            }
        });

*/




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
                "fragmentlogin Page", // TODO: Define a title for the content shown.
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
                "fragmentlogin Page", // TODO: Define a title for the content shown.
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

