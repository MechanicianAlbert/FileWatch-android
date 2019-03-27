package com.albertech.filewatch.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.albertech.filewatch.watch.FileWatchDispatcher;


public class FileWatchService extends Service {

    private FileWatchDispatcher mBinder;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBinder = new FileWatchDispatcher();
        mBinder.init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinder.release();
        mBinder = null;
    }

}
