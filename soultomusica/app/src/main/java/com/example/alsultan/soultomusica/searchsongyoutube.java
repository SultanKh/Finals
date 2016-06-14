package com.example.alsultan.soultomusica;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.android.youtube.player.YouTubeThumbnailView.OnInitializedListener;

/**
 * Created by Al Sultan on 5/14/2016.
 */
public class searchsongyoutube extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener
        , OnInitializedListener{
    private GoogleApiClient client;


    final String YOUTUBE_FUCKING_APIKEY="AIzaSyDnh4u3zdhpDDrgdQ9gSnC-PxESKp5njog";
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    //youtu.be/<VIDEO_ID>
    public static final String VIDEO_ID = "BPNTC7uZYrI";
    private YouTubePlayer youTubePlayer;
    private YouTubePlayerView youTubePlayerView;
    private YouTubeThumbnailView youTubeThumbnailView;
    private YouTubeThumbnailLoader youTubeThumbnailLoader;

    private static final int RQS_ErrorDialog = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_songs_youtube);

        youTubePlayerView = (YouTubePlayerView)findViewById(R.id.youtubeviewvid);
        youTubePlayerView.initialize(YOUTUBE_FUCKING_APIKEY, this);

        youTubeThumbnailView = (YouTubeThumbnailView)findViewById(R.id.youtubethumbnailview);
        youTubeThumbnailView.initialize(YOUTUBE_FUCKING_APIKEY, this);
        youTubeThumbnailView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {




                if(youTubePlayer != null){


                    youTubePlayer.play();
                }else{

                }
            }});
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult result) {

        if (result.isUserRecoverableError()) {
            result.getErrorDialog(this, RQS_ErrorDialog).show();
        } else {
            Toast.makeText(this,
                    "YouTubePlayer.onInitializationFailure(): " + result.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {

        youTubePlayer = player;

        Toast.makeText(getApplicationContext(),
                "YouTubePlayer.onInitializationSuccess()",
                Toast.LENGTH_LONG).show();

        if (!wasRestored) {
            player.cueVideo(VIDEO_ID);
        }
    }

    @Override
    public void onInitializationFailure(YouTubeThumbnailView thumbnailView,
                                        YouTubeInitializationResult result) {

        if (result.isUserRecoverableError()) {
            result.getErrorDialog(this, RQS_ErrorDialog).show();
        } else {
            Toast.makeText(this,
                    "YouTubeThumbnailView.onInitializationFailure(): " + result.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onInitializationSuccess(YouTubeThumbnailView thumbnailView,
                                        YouTubeThumbnailLoader thumbnailLoader) {

        Toast.makeText(getApplicationContext(),
                "YouTubeThumbnailView.onInitializationSuccess()",
                Toast.LENGTH_LONG).show();

        youTubeThumbnailLoader = thumbnailLoader;
        thumbnailLoader.setOnThumbnailLoadedListener(new ThumbnailLoadedListener());

        youTubeThumbnailLoader.setVideo(VIDEO_ID);

    }

    private final class ThumbnailLoadedListener implements
            YouTubeThumbnailLoader.OnThumbnailLoadedListener {

        @Override
        public void onThumbnailError(YouTubeThumbnailView arg0, YouTubeThumbnailLoader.ErrorReason arg1) {
            Toast.makeText(getApplicationContext(),
                    "ThumbnailLoadedListener.onThumbnailError()",
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onThumbnailLoaded(YouTubeThumbnailView arg0, String arg1) {
            Toast.makeText(getApplicationContext(),
                    "ThumbnailLoadedListener.onThumbnailLoaded()",
                    Toast.LENGTH_LONG).show();

        }

    }

}

