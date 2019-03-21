package com.albertech.demo.fileobserver;

import android.os.Environment;
import android.os.FileObserver;
import android.util.Log;

import com.albertech.demo.fileobserver.base.IRecursiveFileObserver;
import com.albertech.demo.fileobserver.base.SimpleRecursiveFileObserverImpl;


public class GLobalFileSystemObserver extends SimpleRecursiveFileObserverImpl {

    private static class Holder {
        private static GLobalFileSystemObserver INSTANCE = new GLobalFileSystemObserver();
    }

    private GLobalFileSystemObserver() {
        if (Holder.INSTANCE != null) {
            throw new RuntimeException("This class cannot be instantiate more than once");
        }
    }

    public static GLobalFileSystemObserver getInstance() {
        return Holder.INSTANCE;
    }


    private static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    private static final int EVENTS = FileObserver.CREATE
            | FileObserver.DELETE_SELF
            | FileObserver.DELETE
            | FileObserver.MOVE_SELF
            | FileObserver.MOVED_FROM
            | FileObserver.MOVED_TO
            | FileObserver.MODIFY;


    @Override
    protected String path() {
        return PATH;
    }

    @Override
    protected int eventMask() {
        return FileObserver.ALL_EVENTS;
    }

    @Override
    protected void onEvent(int event, String path) {
        Log.e("AAA", Helper.name(event) + ": " + path);
    }

    public void init() {
        createObservers();
    }
}
