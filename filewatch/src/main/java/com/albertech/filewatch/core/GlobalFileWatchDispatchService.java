package com.albertech.filewatch.core;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.FileObserver;

import com.albertech.filewatch.api.IFileWatchSubscriber;

import java.util.HashMap;
import java.util.Map;




public class GlobalFileWatchDispatchService extends Service {

    public static class FileWatchBinder extends Binder {

        private GlobalFileWatchDispatchService mService;


        private FileWatchBinder(GlobalFileWatchDispatchService service) {
            mService = service;
        }

        public void subscribeFileWatch(IFileWatchSubscriber subscriber, String path) {
            if (mService != null) {
                mService.subscribeFileWatch(subscriber, path != null ? path : "/");
            }
        }

        public void unsubscribeFileWatch(IFileWatchSubscriber subscriber) {
            if (mService != null) {
                mService.unsubscribeFileWatch(subscriber);
            }
        }

        private void releaseService() {
            mService = null;
        }
    }

    private final Map<IFileWatchSubscriber, String> SUBSCRIBERS = new HashMap<>();

    private final String WATCH_ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    private final int WATCH_EVENT_MASK = FileObserver.CREATE | FileObserver.DELETE;

    private final IGlobalFileWatch WATCHER = new GlobalFileWatchManager.Builder()
            .path(WATCH_ROOT_PATH)
            .eventMask(WATCH_EVENT_MASK)
            .listener(new IFileWatchSubscriber() {
                @Override
                public void onEvent(int event, String path) {
                    dispatchFileEvents(event, path);
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
        WATCHER.startWatching();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SUBSCRIBERS.clear();
        WATCHER.stopWatching();
        mBinder.releaseService();
        mBinder = null;
    }


    void subscribeFileWatch(IFileWatchSubscriber subscriber, String path) {
        SUBSCRIBERS.put(subscriber, path);
    }

    void unsubscribeFileWatch(IFileWatchSubscriber subscriber) {
        SUBSCRIBERS.remove(subscriber);
    }


    private void dispatchFileEvents(int event, String path) {
        for (IFileWatchSubscriber subscriber : SUBSCRIBERS.keySet()) {
            if (subscriber != null) {
                String subscribePath = SUBSCRIBERS.get(subscriber);
                if (path.startsWith(subscribePath)) {
                    subscriber.onEvent(event, path);
                }
            }
        }
    }
}
