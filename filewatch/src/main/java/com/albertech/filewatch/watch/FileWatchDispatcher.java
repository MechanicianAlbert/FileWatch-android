package com.albertech.filewatch.watch;

import android.os.Binder;
import android.os.Environment;
import android.os.FileObserver;

import com.albertech.filewatch.api.IFileWatchSubscriber;

import java.util.HashMap;
import java.util.Map;



public class FileWatchDispatcher extends Binder implements IFileWatchDispatch, IFileWatch {

    private final Map<IFileWatchSubscriber, String> SUBSCRIBERS = new HashMap<>();

    private final String WATCH_ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    private final int WATCH_EVENT_MASK = FileObserver.CREATE | FileObserver.DELETE;

    private final IFileWatchBus WATCHER = new FileWatchBus.Builder()
            .path(WATCH_ROOT_PATH)
            .eventMask(WATCH_EVENT_MASK)
            .listener(this)
            .build();


    public void init() {
        WATCHER.startWatching();
    }

    public void release() {
        SUBSCRIBERS.clear();
        WATCHER.stopWatching();
    }


    @Override
    public void subscribeFileWatch(IFileWatchSubscriber subscriber, String path) {
        SUBSCRIBERS.put(subscriber, path);
    }

    @Override
    public void unsubscribeFileWatch(IFileWatchSubscriber subscriber) {
        SUBSCRIBERS.remove(subscriber);
    }

    @Override
    public void onEvent(int event, String path) {
        dispatchFileEvents(event, path);
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
