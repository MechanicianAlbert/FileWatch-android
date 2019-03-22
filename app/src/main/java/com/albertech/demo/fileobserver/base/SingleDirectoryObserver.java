package com.albertech.demo.fileobserver.base;

import android.os.FileObserver;



public class SingleDirectoryObserver extends FileObserver {

    private String mPath;
    private IFileEvent mListener;


    SingleDirectoryObserver(String path, int mask) {
        super(path, mask);
        mPath = path;
    }

    void setListener(IFileEvent listener) {
        mListener = listener;
    }

    @Override
    public void onEvent(int event, String path) {
        event &= ALL_EVENTS;
        if (event == DELETE_SELF) {
            path = mPath;
        } else if (event == MOVE_SELF) {
            path = mPath;
        }
        mListener.onFileEvent(event, path);
    }
}
