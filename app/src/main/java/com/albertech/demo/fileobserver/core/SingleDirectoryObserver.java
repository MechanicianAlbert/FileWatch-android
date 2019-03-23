package com.albertech.demo.fileobserver.core;

import android.os.FileObserver;

import java.io.File;


public class SingleDirectoryObserver extends FileObserver {

    private final String SELF_PATH;
    private IFileEventListener mListener;


    SingleDirectoryObserver(String path, int mask, IFileEventListener listener) {
        super(path, mask);
        SELF_PATH = path;
        mListener = listener;
    }

    void release() {
        mListener = null;
        stopWatching();
    }

    @Override
    public void onEvent(int event, String path) {
        event &= ALL_EVENTS;
//        path = SELF_PATH + File.separator + path;
        if (event == ACCESS) {
            path = SELF_PATH + File.separator + path;
        } else if (event == MODIFY) {
            path = SELF_PATH + File.separator + path;
        } else if (event == ATTRIB) {
            path = SELF_PATH + File.separator + path;
        } else if (event == CLOSE_WRITE) {
            path = SELF_PATH + File.separator + path;
        } else if (event == CLOSE_NOWRITE) {
            path = SELF_PATH + File.separator + path;
        } else if (event == OPEN) {
            path = SELF_PATH + File.separator + path;
        } else if (event == MOVED_FROM) {
            path = SELF_PATH + File.separator + path;
        } else if (event == MOVED_TO) {
            path = SELF_PATH + File.separator + path;
        } else if (event == CREATE) {
            path = SELF_PATH + File.separator + path;
        } else if (event == DELETE) {
            path = SELF_PATH + File.separator + path;
        } else if (event == DELETE_SELF) {
            path = SELF_PATH + File.separator + path;
        } else if (event == MOVE_SELF) {
            path = SELF_PATH + File.separator + path;
        }
        if (mListener != null) {
            mListener.onFileEvent(event, path);
        }
    }
}
