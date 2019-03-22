package com.albertech.demo;

import android.app.Application;
import android.content.Intent;

import com.albertech.demo.fileobserver.practice.FileWatchService;
import com.albertech.demo.fileobserver.practice.GLobalFileSystemObserver;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        GLobalFileSystemObserver.getInstance().init();

//        startService(new Intent(getApplicationContext(), FileWatchService.class));
    }
}
