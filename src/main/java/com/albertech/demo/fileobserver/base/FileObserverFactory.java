package com.albertech.demo.fileobserver.base;

import android.os.FileObserver;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Map;


class FileObserverFactory {

    interface IFileEvent {
        void onFileEvent(int event, String path);
    }

    class SingleDirectoryObserver extends FileObserver {

        private IFileEvent mListener;


        SingleDirectoryObserver(String path, int mask) {
            super(path, mask);
        }

        void setListener(IFileEvent listener) {
            mListener = listener;
        }

        @Override
        public void onEvent(int event, String path) {
            mListener.onFileEvent(event, path);
        }
    }


    private final Map<String, FileObserver> OBSERVERS;
    private final IFileEvent LISTENER;
    private final int EVENT_MASK;


    FileObserverFactory(@NonNull Map<String, FileObserver> observerMap, @NonNull IFileEvent listener) {
        this(observerMap, listener, FileObserver.ALL_EVENTS);
    }

    FileObserverFactory(@NonNull Map<String, FileObserver> observerMap, @NonNull IFileEvent listener, int mask) {
        if (observerMap == null) {
            throw new NullPointerException("Observer map cannot be null");
        }
        if (listener == null) {
            throw new NullPointerException("File event listener cannot be null");
        }
        LISTENER = listener;
        OBSERVERS = observerMap;
        EVENT_MASK = mask;
    }


    FileObserver create(String path) {
        Log.e("AAA", "为路径 " + path + " 生成新的监听器");
        SingleDirectoryObserver o = new SingleDirectoryObserver(path, EVENT_MASK);
        OBSERVERS.put(path, o);
        o.setListener(LISTENER);
        return o;
    }
}
