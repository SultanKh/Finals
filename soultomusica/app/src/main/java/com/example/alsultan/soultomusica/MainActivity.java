package com.example.alsultan.soultomusica;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {





    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView login8=(TextView) findViewById(R.id.mainpagelogin);
        TextView signup2=(TextView) findViewById(R.id.mainpagesignup);
        TextView starting=(TextView) findViewById(R.id.starting);
        TextView results1=(TextView) findViewById(R.id.showresults) ;
        TextView searching=(TextView) findViewById(R.id.searchtxt) ;
        TextView musicplayer=(TextView) findViewById(R.id.music_player) ;
        TextView goyoutube=(TextView) findViewById(R.id.goyoutube) ;
        TextView go7dig=(TextView) findViewById(R.id.gosevend) ;
        TextView gofingering=(TextView) findViewById(R.id.audioreco) ;
        TextView searchinyoutube=(TextView) findViewById(R.id.youtubelist) ;






        //===========================

        searchinyoutube.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent i=new Intent(getApplicationContext(),using_youtube.class);
                startActivity(i);

                return false;
            }
        });

        gofingering.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent i = new Intent(getApplicationContext(), audiofingerprint.class);
                startActivity(i);

                return false;
            }
        });

        go7dig.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent i = new Intent(getApplicationContext(), songfrom7digital.class);
                startActivity(i);

                return false;
            }
        });

        goyoutube.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent i = new Intent(getApplicationContext(), searchsongyoutube.class);
                startActivity(i);
                return false;
            }
        });

        searching.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent i = new Intent(getApplicationContext(), searchforsong.class);
                startActivity(i);
                return false;
            }
        });


        login8.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent i = new Intent(getApplicationContext(), fragmentlogin.class);
                startActivity(i);
                return false;
            }
        });


        signup2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent i = new Intent(getApplicationContext(), signupclass.class);
                startActivity(i);
                return false;
            }
        });

        starting.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent i = new Intent(getApplicationContext(), starting.class);
                startActivity(i);
                return false;
            }
        });

        results1.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View view, MotionEvent motionEvent){
                Intent i = new Intent(getApplicationContext(), songanalyzeresults.class);
                startActivity(i);
                return false;
            }
        });


        musicplayer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent i = new Intent(getApplicationContext(), musicplayer1.class);
                startActivity(i);
                return false;
            }
        });

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
                "Main Page", // TODO: Define a title for the content shown.
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
                "Main Page", // TODO: Define a title for the content shown.
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