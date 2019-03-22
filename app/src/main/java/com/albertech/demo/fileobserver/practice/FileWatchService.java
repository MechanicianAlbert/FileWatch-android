package com.albertech.demo.fileobserver.practice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;


import com.albertech.demo.fileobserver.api.IFileWatch;
import com.albertech.demo.fileobserver.base.IRecursiveFileObserver;
import com.albertech.demo.fileobserver.base.SimpleRecursiveFileObserverImpl;

import java.util.HashMap;
import java.util.Map;


public class FileWatchService extends Service implements FileWatchConstants {

    public static class FileWatchBinder extends Binder {

        private final FileWatchService SERVICE;


        public FileWatchBinder(FileWatchService service) {
            SERVICE = service;
        }

        public void registerFileSystemWatch(IFileWatch watcher, String subscribePath) {
            SERVICE.registerFileSystemWatch(watcher, subscribePath);
        }

        public void unregisterFileSystemWatch(IFileWatch watcher) {
            SERVICE.unregisterFileSystemWatch(watcher);
        }
    }


    private final Map<IFileWatch, String> WATCHERS = new HashMap<>();
    private final FileWatchBinder mBinder = new FileWatchBinder(this);


    private final IRecursiveFileObserver OBSERVER = new SimpleRecursiveFileObserverImpl() {
        @Override
        protected String path() {
            return PATH;
        }

        @Override
        protected int eventMask() {
            return EVENTS;
        }

        @Override
        protected void onEvent(String parentPath, int event, String path) {
            notifyFileEvents(parentPath, event, path);
        }
    };


    @Override
    public FileWatchBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        OBSERVER.createObservers();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        WATCHERS.clear();
        OBSERVER.stopWatching();
    }


    void registerFileSystemWatch(IFileWatch watch, String subscribePath) {
        WATCHERS.put(watch, subscribePath);
    }

    void unregisterFileSystemWatch(IFileWatch watch) {
        WATCHERS.remove(watch);
    }

    private void notifyFileEvents(String parentPath, int event, String path) {
        for (IFileWatch watcher : WATCHERS.keySet()) {
            if (watcher != null) {
                String subscribePath = WATCHERS.get(watcher);
                if (parentPath.startsWith(subscribePath)) {
                    watcher.onEvent(event, path);
                }
            }
        }
    }
}
