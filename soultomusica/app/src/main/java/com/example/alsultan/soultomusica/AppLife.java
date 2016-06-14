package com.example.alsultan.soultomusica;

import android.app.Application;

import com.firebase.client.Firebase;
import com.parse.Parse;

/**
 * Created by Al Sultan on 5/21/2016.
 */
public class AppLife extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);

        //PARSE
        Parse.initialize(this, "Zq8onFf4i6UMn5q0pjbrnjSWAuNq40jDDXMehOZy", "FP9kiFlMzM9eDfQ1xjfhWJuo9bJNVy7PSlW3J2RX");
        //    Parse.initialize(new Parse.Configuration.Builder(this)
        //          .applicationId("Zq8onFf4i6UMn5q0pjbrnjSWAuNq40jDDXMehOZy")
        //        .clientKey("FP9kiFlMzM9eDfQ1xjfhWJuo9bJNVy7PSlW3J2RX")
        //      .server("http://thespiritmusic.parseapp.com:1337/parse/")
        //    .build()
        // );


    }
}
