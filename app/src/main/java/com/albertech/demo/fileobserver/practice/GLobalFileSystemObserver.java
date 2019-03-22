package com.albertech.demo.fileobserver.practice;


import com.albertech.demo.fileobserver.api.IFileWatch;
import com.albertech.demo.fileobserver.base.SimpleRecursiveFileObserverImpl;

import java.util.HashMap;
import java.util.Map;


public class GLobalFileSystemObserver extends SimpleRecursiveFileObserverImpl implements FileWatchConstants {

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


    private final Map<IFileWatch, String> WATCHERS = new HashMap<>();


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


    public void init() {
        createObservers();
    }

    public void registerFileSystemWatch(IFileWatch watch, String subscribePath) {
        WATCHERS.put(watch, subscribePath);
    }

    public void unregisterFileSystemWatch(IFileWatch watch) {
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
