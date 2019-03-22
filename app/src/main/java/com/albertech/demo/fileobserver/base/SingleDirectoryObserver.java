package com.albertech.demo.fileobserver.base;

import android.os.FileObserver;



public class SingleDirectoryObserver extends FileObserver {

    private final String SELF_PATH;
    private IFileEvent mListener;


    SingleDirectoryObserver(String path, int mask) {
        super(path, mask);
        SELF_PATH = path;
    }

    void setListener(IFileEvent listener) {
        mListener = listener;
    }

    void removeListener() {
        mListener = null;
    }

    @Override
    public void onEvent(int event, String path) {
        event &= ALL_EVENTS;
        if (event == DELETE_SELF) {
            path = SELF_PATH;
        } else if (event == MOVE_SELF) {
            path = SELF_PATH;
        }
        if (mListener != null) {
            mListener.onFileEvent(SELF_PATH, event, path);
        }
    }
}
