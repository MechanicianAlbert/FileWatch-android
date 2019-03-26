package com.albertech.demo.filewatch.core;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.albertech.demo.filewatch.api.IFileWatchSubscriber;
import com.albertech.demo.filewatch.api.IFileWatchUnsubscribe;



public class FileWatchServiceConnection implements ServiceConnection, IFileWatchUnsubscribe {

    private Context mContext;
    private IFileWatchSubscriber mSubscriber;
    private String mPath;
    private GlobalFileWatchDispatchService.FileWatchBinder mBinder;
    private volatile boolean mHadUnboundService;


    public FileWatchServiceConnection(Context context, IFileWatchSubscriber subscriber, String path) {
        mContext = context;
        mSubscriber = subscriber;
        mPath = path;
        mContext.bindService(createFileWatchServiceIntent(), this, Context.BIND_AUTO_CREATE);
    }


    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        if (service instanceof GlobalFileWatchDispatchService.FileWatchBinder) {
            mBinder = (GlobalFileWatchDispatchService.FileWatchBinder) service;
            mBinder.subscribeFileWatch(mSubscriber, mPath);
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

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
        return new Intent(mContext, GlobalFileWatchDispatchService.class);
    }

    private void release() {
        if (mBinder != null) {
            mBinder.unsubscribeFileWatch(mSubscriber);
            mBinder = null;
        }
        mSubscriber = null;
    }
}
