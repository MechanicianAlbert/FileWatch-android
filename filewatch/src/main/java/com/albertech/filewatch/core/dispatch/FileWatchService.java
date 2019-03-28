package com.albertech.filewatch.core.dispatch;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;


public class FileWatchService extends Service {

    private FileWatchDispatcher mDispatcher;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mDispatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mDispatcher = new FileWatchDispatcher();
        mDispatcher.init(getApplicationContext());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDispatcher.release();
        mDispatcher = null;
    }

}
