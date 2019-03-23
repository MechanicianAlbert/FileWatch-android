package com.albertech.demo;

import android.app.Application;

import com.albertech.demo.fileobserver.practice.GLobalFileWatchSingleton;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        GLobalFileWatchSingleton.getInstance().init();

//        startService(new Intent(getApplicationContext(), GlobalFileWatchService.class));
    }
}
