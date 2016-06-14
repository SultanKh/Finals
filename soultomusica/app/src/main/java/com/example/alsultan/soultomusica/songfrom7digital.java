package com.example.alsultan.soultomusica;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by Al Sultan on 5/19/2016.
 */
public class songfrom7digital extends Activity {

    private GoogleApiClient client;

    private EditText textsong;
    private ImageButton rocking;
    private TextView showing;

    private String API_7DIGITAL="7d36w6etje7b";

    private Button buttonPlay;
private Button buttonStop;
    MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_from_7d);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        rocking=(ImageButton) findViewById(R.id.searchbtnsong);
        textsong=(EditText) findViewById(R.id.songtitle);
        showing=(TextView) findViewById(R.id.resultsintext);
        buttonPlay=(Button) findViewById(R.id.play11);
        buttonStop=(Button) findViewById(R.id.stop2);


        final String GET_LINK="http://api.7digital.com/1.2/artist/browse?letter=p&format=json&oauth_consumer_key="+API_7DIGITAL;
        final String FROM_7DIG="http://api.7digital.com/1.2/track/search?q="+textsong.getText() +
                "&oauth_consumer_key="+API_7DIGITAL +
                "&country=GB" +
                "&pagesize=2" +
                "&usageTypes=download,subscriptionstreaming,adsupportedstreaming";


//a.replaceAll("\\s+",""); remove blank spaces


        //execute searching ladys and gentlemen
        rocking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String results;
                results = "NO Results";
                try {
                    results=new SendGetRequestToEchoNest().execute(GET_LINK).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                showing.setText(results);
            }
        });





        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer  = new MediaPlayer();
                String urlsong= "http://programmerguru.com/android-tutorial/wp-content/uploads/2013/04/hosannatelugu.mp3";
                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


                try {
                    mPlayer.setDataSource(urlsong);
                } catch (IllegalArgumentException e) {
                    Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (SecurityException e) {
                    Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (IllegalStateException e) {
                    Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    mPlayer.prepare();
                } catch (IllegalStateException e) {
                    Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                }

                mPlayer.start();
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mPlayer!=null && mPlayer.isPlaying()){
                    mPlayer.stop();
                }
            }
        });



    }

    // check network connection
    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }


    //GET return String
    public static String GET_AS_String(String url) {
        String jsonString = null;
        // defaultHttpClient
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpPost = new HttpGet(url);

        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpEntity httpEntity = httpResponse.getEntity();
        // xml = EntityUtils.toString(httpEntity);


        try {
            jsonString = EntityUtils.toString(httpEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return jsonString;
    }
    //GET requesting Lyrics FROM Echo as JSON
    private class SendGetRequestToEchoNest extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {


            return GET_AS_String(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.

        /*
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();

            //display the requested vales
            resp.setText(result);
        }
        */
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "songfrom7digital Page", // TODO: Define a title for the content shown.
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
                "songfrom7digital Page", // TODO: Define a title for the content shown.
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
