package com.albertech.demo.filewatch.core;

import android.os.FileObserver;
import android.util.Log;

import java.io.File;


public class SingleDirectoryObserver extends FileObserver {

    private static final String TAG = SingleDirectoryObserver.class.getSimpleName();


    private final String SELF_PATH;
    private IFileEventListener mListener;


    SingleDirectoryObserver(String path, int mask, IFileEventListener listener) {
        super(path, mask);
        SELF_PATH = path;
        mListener = listener;
        startWatching();
        Log.d(TAG, "Start watch path: " + SELF_PATH);
    }

    void release() {
        mListener = null;
        stopWatching();
        Log.d(TAG, "Stop watch path: " + SELF_PATH);
    }

    @Override
    public void onEvent(int event, String path) {
        event &= ALL_EVENTS;
        String realPath = SELF_PATH + File.separator + path;
        if (mListener != null) {
            mListener.onFileEvent(event, realPath);
        }
    }
}