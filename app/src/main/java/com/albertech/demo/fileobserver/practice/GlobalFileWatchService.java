package com.albertech.demo.fileobserver.practice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;


import com.albertech.demo.fileobserver.api.IFileWatch;
import com.albertech.demo.fileobserver.core.IRecursiveFileWatcher;
import com.albertech.demo.fileobserver.core.SimpleRecursiveFileWatcherImpl;

import java.util.HashMap;
import java.util.Map;


public class GlobalFileWatchService extends Service implements FileWatchDefaultConfig {

    public static class FileWatchBinder extends Binder {

        private GlobalFileWatchService mService;


        FileWatchBinder(GlobalFileWatchService service) {
            mService = service;
        }

        public void registerFileSystemWatch(IFileWatch watcher, String subscribePath) {
            subscribePath = subscribePath != null ? subscribePath : "";
            if (mService != null) {
                mService.registerFileSystemWatch(watcher, subscribePath);
            }
        }

        public void unregisterFileSystemWatch(IFileWatch watcher) {
            if (mService != null) {
                mService.unregisterFileSystemWatch(watcher);
            }
        }

        void releaseService() {
            mService = null;
        }
    }


    private final Map<IFileWatch, String> WATCHERS = new HashMap<>();
    private FileWatchBinder mBinder;


    private final IRecursiveFileWatcher OBSERVER = new SimpleRecursiveFileWatcherImpl() {
        @Override
        protected String path() {
            return PATH;
        }

        @Override
        protected int eventMask() {
            return EVENTS;
        }

        @Override
        protected void onEvent(int event, String path) {
            notifyFileEvents(event, path);
        }
    };


    @Override
    public FileWatchBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBinder = new FileWatchBinder(this);
        OBSERVER.createObservers();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        WATCHERS.clear();
        OBSERVER.stopWatching();
        mBinder.releaseService();
        mBinder = null;
    }


    void registerFileSystemWatch(IFileWatch watch, String subscribePath) {
        WATCHERS.put(watch, subscribePath);
    }

    void unregisterFileSystemWatch(IFileWatch watch) {
        WATCHERS.remove(watch);
    }

    private void notifyFileEvents(int event, String path) {
        for (IFileWatch watcher : WATCHERS.keySet()) {
            if (watcher != null) {
                String subscribePath = WATCHERS.get(watcher);
                if (path.startsWith(subscribePath)) {
                    watcher.onEvent(event, path);
                }
            }
        }
    }
}
