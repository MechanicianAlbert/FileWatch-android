package com.albertech.demo.fileobserver.practice;


import com.albertech.demo.fileobserver.api.IFileWatch;
import com.albertech.demo.fileobserver.base.SimpleRecursiveFileObserverImpl;

import java.util.HashSet;
import java.util.Set;


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


    private final Set<IFileWatch> WATCHERS = new HashSet<>();


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


    public void init() {
        createObservers();
    }

    public void registerFileSystemWatch(IFileWatch watch) {
        WATCHERS.add(watch);
    }

    public void unregisterFileSystemWatch(IFileWatch watch) {
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
