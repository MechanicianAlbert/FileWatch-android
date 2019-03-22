package com.albertech.demo.fileobserver.practice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;


import com.albertech.demo.fileobserver.api.IFileWatch;
import com.albertech.demo.fileobserver.base.IRecursiveFileObserver;
import com.albertech.demo.fileobserver.base.SimpleRecursiveFileObserverImpl;

import java.util.HashSet;
import java.util.Set;



public class FileWatchService extends Service implements FileWatchConstants {

    public static class FileWatchBinder extends Binder {

        private final FileWatchService SERVICE;


        public FileWatchBinder(FileWatchService service) {
            SERVICE = service;
        }

        public void registerFileSystemWatch(IFileWatch watcher) {
            SERVICE.registerFileSystemWatch(watcher);
        }

        public void unregisterFileSystemWatch(IFileWatch watcher) {
            SERVICE.unregisterFileSystemWatch(watcher);
        }
    }


    private final Set<IFileWatch> WATCHERS = new HashSet<>();
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
        OBSERVER.createObservers();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        WATCHERS.clear();
        OBSERVER.stopWatching();
    }


    void registerFileSystemWatch(IFileWatch watch) {
        WATCHERS.add(watch);
    }

    void unregisterFileSystemWatch(IFileWatch watch) {
        WATCHERS.remove(watch);
    }

    private void notifyFileEvents(int event, String path) {
        for (IFileWatch watcher : WATCHERS) {
            if (watcher != null) {
                watcher.onEvent(event, path);
            }
        }
    }
}
