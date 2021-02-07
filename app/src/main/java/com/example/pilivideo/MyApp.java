package com.example.pilivideo;

import android.app.Application;

import com.dancechar.pilivideo.PlayerSDK;

public class MyApp  extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PlayerSDK.init(
                this
        );
    }
}
