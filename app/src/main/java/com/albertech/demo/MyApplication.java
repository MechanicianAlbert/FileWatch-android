package com.albertech.demo;

import android.app.Application;

import com.albertech.demo.fileobserver.GLobalFileSystemObserver;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        GLobalFileSystemObserver.getInstance().init();

    }
}
