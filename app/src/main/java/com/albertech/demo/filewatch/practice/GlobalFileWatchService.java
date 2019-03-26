package com.albertech.demo.filewatch.practice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;


import com.albertech.demo.filewatch.api.IFileWatch;
import com.albertech.demo.filewatch.core.IRecursiveFileWatcher;
import com.albertech.demo.filewatch.core.GlobalFileWatcher;

import java.util.HashMap;
import java.util.Map;


public class GlobalFileWatchService extends Service implements FileWatchDefaultConfig {

    public static class FileWatchBinder extends Binder {

        private GlobalFileWatchService mService;


        private FileWatchBinder(GlobalFileWatchService service) {
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

        private void releaseService() {
            mService = null;
        }
    }


    private final Map<IFileWatch, String> WATCHERS = new HashMap<>();

    private final IRecursiveFileWatcher OBSERVER = new GlobalFileWatcher.Builder()
            .path(PATH)
            .eventMask(EVENTS)
            .listener(new IFileWatch() {
                @Override
                public void onEvent(int event, String path) {
                    notifyFileEvents(event, path);
                }
            })
            .build();


    private FileWatchBinder mBinder;


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
