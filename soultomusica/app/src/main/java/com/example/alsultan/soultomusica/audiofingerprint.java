package com.example.alsultan.soultomusica;


import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.acrcloud.rec.sdk.ACRCloudClient;
import com.acrcloud.rec.sdk.ACRCloudConfig;
import com.acrcloud.rec.sdk.IACRCloudListener;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by Al Sultan on 4/30/2016.
 *
 */


public class audiofingerprint extends Activity implements IACRCloudListener {

    private GoogleApiClient client;


    private ACRCloudClient mClient;
    private ACRCloudConfig mConfig;

    private TextView mVolume, mResult, tv_time;

    private boolean mProcessing = false;
    private boolean initState = false;

    private String path = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_fingerprint);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        //lets play...

        //System.loadLibrary("acrcloud-android-sdk-1.2.8");
        path = Environment.getExternalStorageDirectory().toString()
                + "/acrcloud/model";


        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }

        mVolume = (TextView) findViewById(R.id.volume);
        mResult = (TextView) findViewById(R.id.result);
        tv_time = (TextView) findViewById(R.id.time);

        Button startBtn = (Button) findViewById(R.id.start);

        Button stopBtn = (Button) findViewById(R.id.stop);

        Button cancelBtn = (Button) findViewById(R.id.cancel);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });


        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopRecordToRecognize();
            }
        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });



    }

    public void start() {
        if(!this.initState) {
            this.mConfig = new ACRCloudConfig();
            this.mConfig.acrcloudListener =  this;//(IACRCloudListener) this     2)
            this.mConfig.context = this;
            this.mConfig.host = "eu-west-1.api.acrcloud.com";
            this.mConfig.accessKey = "7e1852beb7c1b421b872307c4cdd297f";
            this.mConfig.accessSecret = "Gb2f0x9OfoN4Up7OhNxkNqc1kMhsXg52LJcRn3lu";
            this.mConfig.reqMode = ACRCloudConfig.ACRCloudRecMode.REC_MODE_REMOTE;

            // offline db setting example.
//			this.mConfig = new ACRCloudConfig();
//			this.mConfig.acrcloudListener = this;
//			this.mConfig.context = this;
//			this.mConfig.host = path; // you can change it with other path.
//			this.mConfig.accessKey = "XXXXXXXX";
//			this.mConfig.accessSecret = "XXXXXXXX";
//			this.mConfig.reqMode = ACRCloudConfig.ACRCloudRecMode.REC_MODE_LOCAL;

            this.mClient = new ACRCloudClient();
            this.initState = this.mClient.initWithConfig(this.mConfig);
            if (!this.initState) {
                Toast.makeText(this, "init error", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (!mProcessing) {
            mProcessing = true;
            mVolume.setText("");
            mResult.setText("");
            if (this.mClient == null || !this.mClient.startRecognize()) {
                mProcessing = false;
                mResult.setText("start error!");
            }
        }
    }

    protected void stopRecordToRecognize() {
        if (mProcessing && this.mClient != null) {
            this.mClient.stopRecordToRecognize();
        }
        mProcessing = false;
    }

    protected void cancel() {
        if (mProcessing && this.mClient != null) {
            mProcessing = false;
            this.mClient.cancel();
            tv_time.setText("");
            mResult.setText("");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onResult(String result) {
        if (this.mClient != null) {
            this.mClient.cancel();
            mProcessing = false;
        }

        String tres = "\n";

        try {
            JSONObject j = new JSONObject(result);
            JSONObject j1 = j.getJSONObject("status");
            int j2 = j1.getInt("code");
            if(j2 == 0){
                JSONObject metadata = j.getJSONObject("metadata");
                //
                if (metadata.has("humming")) {
                    JSONArray hummings = metadata.getJSONArray("humming");
                    for(int i=0; i<hummings.length(); i++) {
                        JSONObject tt = (JSONObject) hummings.get(i);
                        String title = tt.getString("title");
                        JSONArray artistt = tt.getJSONArray("artists");
                        JSONObject art = (JSONObject) artistt.get(0);
                        String artist = art.getString("name");
                        tres = tres + (i+1) + ".  Title: " + title + "    Artist: " + artist + "\n";
                    }
                }
                if (metadata.has("music")) {
                    JSONArray musics = metadata.getJSONArray("music");
                    for(int i=0; i<musics.length(); i++) {
                        JSONObject tt = (JSONObject) musics.get(i);
                        String title = tt.getString("title");
                        JSONArray artistt = tt.getJSONArray("artists");
                        JSONObject art = (JSONObject) artistt.get(0);
                        String artist = art.getString("name");
                        tres = tres + (i+1) + ".  Title: " + title + "    Artist: " + artist + "\n";
                    }
                }
                if (metadata.has("streams")) {
                    JSONArray musics = metadata.getJSONArray("streams");
                    for(int i=0; i<musics.length(); i++) {
                        JSONObject tt = (JSONObject) musics.get(i);
                        String title = tt.getString("title");
                        JSONArray channelId = tt.getJSONArray("channel_id");
                        tres = tres + (i+1) + ".  Title: " + title + "    Channel Id: " + channelId + "\n";
                    }
                }
                if (metadata.has("custom_files")) {
                    JSONArray musics = metadata.getJSONArray("custom_files");
                    for(int i=0; i<musics.length(); i++) {
                        JSONObject tt = (JSONObject) musics.get(i);
                        String title = tt.getString("title");
                        tres = tres + (i+1) + ".  Title: " + title + "\n";
                    }
                }
                tres = tres + "\n\n" + result;
            }else{
                tres = result;
            }
        } catch (JSONException e) {
            tres = result;
            e.printStackTrace();
        }

        mResult.setText(tres);
    }

    @Override
    public void onVolumeChanged(double volume) {
        mVolume.setText("Volume:" + volume);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("MainActivity", "release");
        if (this.mClient != null) {
            this.mClient.release();
            this.initState = false;
            this.mClient = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "audiofingerprint Page", // TODO: Define a title for the content shown.
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
                "audiofingerprint Page", // TODO: Define a title for the content shown.
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
