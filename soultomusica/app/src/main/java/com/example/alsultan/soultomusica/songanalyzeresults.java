package com.example.alsultan.soultomusica;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Created by Al Sultan on 4/29/2016.
 */
public class songanalyzeresults extends Activity {

    private GoogleApiClient client;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_analysis_results);

        /*
        Example query:
        http://developer.echonest.com/api/v4/track/profile?api_key=ZSLN415AFXNHTTRAS&format=json&id=TRTLKZV12E5AC92E11&bucket=audio_summary


        "track": {
             "artist": "Weezer",
             "audio_md5": "e16bde82eaecd13bde9261b2710aa991",
             "audio_summary": {
                 "analysis_url": "https://echonest-analysis.s3.amazonaws.com/TR/A1B2C3D4E5F6G7/3/full.json",
                 "danceability": 0.5164314670162907,
                 "duration": 243.64363,
                 "energy": 0.6617689403520844,
                 "key": 1,
                 "loudness": -4.613,
                 "mode": 1,
                 "speechiness": 0.16405298937493515,
                 "acousticness": 0.1331355,
                 "liveness": 0.05298937493515,
                 "tempo": 74.694,
                 "time_signature": 4
             }

         */

        /*
        send url request to API ECHONEST
         */




        /*
        bulding graph baseed on results
         */
        GraphView graph = (GraphView) findViewById(R.id.graph);
        GraphView graph2 = (GraphView) findViewById(R.id.graphreults);
        GraphView graph3 = (GraphView) findViewById(R.id.lyricresultsgraph);


        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1), //danceability
                new DataPoint(1, 5),//energy
                new DataPoint(2, 3),//loudness
                new DataPoint(3, 2),//speechiness
                new DataPoint(4, 6),//acousticness
                new DataPoint(5,5)//tempo
        });
        graph.addSeries(series);


//*****************************************SOCOND GRAPH*******************************
        BarGraphSeries<DataPoint> series2 = new BarGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, -1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });


// styling
        series2.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });

        series2.setSpacing(50);

// draw values on top
        series2.setDrawValuesOnTop(true);
        series2.setValuesOnTopColor(Color.RED);
        graph2.addSeries(series2);
//series.setValuesOnTopSize(50);






        /*
        Analysis For Lyric s
         */

        LineGraphSeries<DataPoint> series3 = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1), //danceability
                new DataPoint(1, 5),//energy
                new DataPoint(2, 3),//loudness
                new DataPoint(3, 2),//speechiness
                new DataPoint(4, 6),//acousticness
                new DataPoint(5,5)//tempo
        });
        graph3.addSeries(series3);





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
                "songanalyzeresults Page", // TODO: Define a title for the content shown.
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
                "songanalyzeresults Page", // TODO: Define a title for the content shown.
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
