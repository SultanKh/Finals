package com.example.alsultan.soultomusica;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


/**
 * Created by Al Sultan on 5/25/2016.
 */
public class using_youtube extends Activity {
    final String YOUTUBE_FUCKING_APIKEY="AIzaSyAB3I1TWR4oN84BHaC1VLoaxVbe5KyDFI0s";
    private static final String TAG = "Playing Youtube";
    private Context context = using_youtube.this;
    private ProgressBar progressBar, progressBarHor;
    private EditText etSearch;
    private ListView listView;
    private ParserAdapter parserAdapter;
    private Button buttonSearch;
    private GrabYoutubeTask youtubeTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usingyoutube);
        buttonSearch = (Button) findViewById(R.id.buttonSearch);
        etSearch = (EditText) findViewById(R.id.editSearch);


        UICreate();
    }

    private View.OnClickListener l = new View.OnClickListener() {
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        public void onClick(View v) {

            if (buttonSearch.getText().toString().equals("Search")) {
                updateList();
                String searchString = etSearch.getText().toString().trim().replace(' ', '+');

                youtubeTask = new GrabYoutubeTask();
                youtubeTask.execute(searchString);


                buttonSearch.setText("Stop");
            } else {
                youtubeTask.cancel(true);
                buttonSearch.setText("Search");
            }

        }


    };


    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String url = parserAdapter.getItem(i).getUrl();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        }
    };

    // Initialize User Interface
    private void UICreate() {

        buttonSearch.setOnClickListener(l);


        parserAdapter = new ParserAdapter(context);
        youtubeTask = new GrabYoutubeTask();

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(parserAdapter);
        listView.setOnItemClickListener(itemClickListener);


        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);

        progressBarHor = (ProgressBar) findViewById(R.id.progressBar);
        progressBarHor.setVisibility(View.GONE);
        progressBarHor.setMax(60);
        progressBarHor.setProgress(0);


    }

    // Update of ListView
    private void updateList() {
        if (!parserAdapter.isEmpty()) {
            parserAdapter.clear();
            parserAdapter.notifyDataSetChanged();
        }
    }

    // Saving search results data to DB before exit via Back button
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(context)
                .setTitle("Close program?")
                .setMessage("You really want close?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(TAG, "before onDestroy");
                        DbSavingTask dbSavingTask = new DbSavingTask();
                        dbSavingTask.execute();

                    }
                }).create().show();
    }

    // Grabbing data from Youtube
    private class GrabYoutubeTask extends AsyncTask<String, List<Entry>, Void> {///////////<------------------------------------------

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            progressBarHor.setVisibility(View.VISIBLE);
            progressBarHor.setProgress(0);


        }

        @Override
        protected Void doInBackground(String... searchString) {

            YoutubeParser xmlYoutubeParser = new YoutubeParser();
            for (int i = 1; i <= 300; i += 10) {
                String strUrl2=" https://www.googleapis.com/youtube/v3/videos?id=7lCDEYXw3mM&key="+YOUTUBE_FUCKING_APIKEY+"&part=snippet,contentDetails,statistics,status";

                String strUrl = "https://www.googleapis.com/youtube/v3/videos?id=" +searchString[0] +
                        "&start-index=" + i + "&max-results=5&v=2" +
                        "&key="+YOUTUBE_FUCKING_APIKEY;
                List<Entry> list = xmlYoutubeParser.parse(HttpGetter.GetHttpResponseReader(strUrl));
                publishProgress(list);
                if (isCancelled()) return null;
            }


            return null;
        }

        @Override
        protected void onProgressUpdate(List<Entry>... values) {
            if(values != null) {
                for (Entry e : values[0]) parserAdapter.add(e);
                progressBarHor.incrementProgressBy(1);
                parserAdapter.notifyDataSetChanged();
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(context, "Parsed", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            progressBarHor.setVisibility(View.GONE);

        }

        @Override
        protected void onCancelled() {
            Toast.makeText(context, "Canceled", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            progressBarHor.setVisibility(View.GONE);
        }
    }


    // Saving parsed data to DB before exit
    private class DbSavingTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(context, "Data Saving", "Saving to Database...");
            Log.d(TAG, "in onPreExecute");

        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "Begin doInBackground");
            parserAdapter.addItemsToDB();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            parserAdapter.closeAll();
            Log.d(TAG, "in onPostExecute");
            finish();

        }


    }

    private static class HttpGetter {

        private static final String TAG = "HttpGetter";

        public static BufferedReader GetHttpResponseReader(String url) {
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();

                return new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }



}
