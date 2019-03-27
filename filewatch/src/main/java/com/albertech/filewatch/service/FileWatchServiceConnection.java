package com.albertech.filewatch.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.albertech.filewatch.api.IFileWatchSubscriber;
import com.albertech.filewatch.api.IFileWatchUnsubscribe;
import com.albertech.filewatch.watch.IFileWatchDispatch;


public class FileWatchServiceConnection implements ServiceConnection, IFileWatchUnsubscribe {

    private Context mContext;
    private IFileWatchSubscriber mSubscriber;
    private String mPath;
    private IFileWatchDispatch mBinder;
    private volatile boolean mHadUnboundService;


    public FileWatchServiceConnection(Context context, IFileWatchSubscriber subscriber, String path) {
        mContext = context;
        mSubscriber = subscriber;
        mPath = path;
        mContext.bindService(createFileWatchServiceIntent(), this, Context.BIND_AUTO_CREATE);
    }


    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        if (service instanceof IFileWatchDispatch) {
            mBinder = (IFileWatchDispatch) service;
            mBinder.subscribeFileWatch(mSubscriber, mPath);
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        release();
    }

    @Override
    public synchronized void unsubscribe() {
        release();
        try {
            if (!mHadUnboundService) {
                mHadUnboundService = true;
                mContext.unbindService(this);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }


    private Intent createFileWatchServiceIntent() {
        return new Intent(mContext, FileWatchService.class);
    }

    private void release() {
        if (mBinder != null) {
            mBinder.unsubscribeFileWatch(mSubscriber);
            mBinder = null;
        }
        mSubscriber = null;
    }
}
