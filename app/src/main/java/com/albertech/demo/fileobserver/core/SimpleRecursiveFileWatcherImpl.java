package com.albertech.demo.fileobserver.core;

import android.os.FileObserver;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SimpleRecursiveFileWatcherImpl implements IRecursiveFileWatcher, IFileEventListener {

    private final Map<String, SingleDirectoryObserver> OBSERVERS = new ConcurrentHashMap<>();

    private final ExecutorService EXECUTOR = Executors.newCachedThreadPool();

    private final FileFilter FILE_FILTER = new FileFilter() {

        @Override
        public boolean accept(File f) {
            return isValidReadableDirectory(f);
        }
    };

    private final Runnable OBSERVER_CREATER = new Runnable() {

        @Override
        public void run() {
            Stack<String> s = new Stack<>();
            s.push(path());
            while (!s.isEmpty()) {
                String path = s.pop();
                if (!OBSERVERS.containsKey(path) || OBSERVERS.get(path) == null) {
                    createSingleDirectoryObserver(path);
                }
                File f = new File(path);
                File[] children = f.listFiles(FILE_FILTER);
                if (children != null) {
                    for (File child : children) {
                        s.push(child.getAbsolutePath());
                    }
                }
            }
        }
    };

    private final Runnable START_WATCHING = new Runnable() {

        @Override
        public void run() {
            for (FileObserver o : OBSERVERS.values()) {
                o.startWatching();
            }
        }
    };

    private final Runnable STOP_WATCHING = new Runnable() {

        @Override
        public void run() {
            for (FileObserver o : OBSERVERS.values()) {
                o.stopWatching();
            }
        }
    };


    private final void createSingleDirectoryObserver(String path) {
        Log.e("AAA", "为路径 " + path + " 生成新的监听器");
        int eventMask = eventMask() & FileObserver.ALL_EVENTS;
        SingleDirectoryObserver o = new SingleDirectoryObserver(path, eventMask, this);
        OBSERVERS.put(path, o);
        o.startWatching();
    }

    private final void releaseInvalidPathObserver(String path) {
        try {
            SingleDirectoryObserver o = OBSERVERS.remove(path);
            if (o != null) {
                o.release();
                Log.e("AAA", "为路径 " + path + " 释放监听器");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final boolean isValidReadableDirectoryPath(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        } else {
            File f = new File(path);
            return isValidReadableDirectory(f);
        }
    }

    private final boolean isValidReadableDirectory(File f) {
        return f != null
                && f.canRead()
                && f.isDirectory()
                && !".".equals(f.getName())
                && !"..".equals(f.getName());
    }


    @Override
    public void createObservers() {
        EXECUTOR.execute(OBSERVER_CREATER);
    }

    @Override
    public final void startWatching() {
        EXECUTOR.execute(START_WATCHING);
    }

    @Override
    public final void stopWatching() {
        EXECUTOR.execute(STOP_WATCHING);
    }

    @Override
    public final void onFileEvent(int event, String fullPath) {
        switch (event) {
            case FileObserver.CREATE:
            case FileObserver.MOVED_TO:
                if (isValidReadableDirectoryPath(fullPath)) {
                    createSingleDirectoryObserver(fullPath);
                }
                break;
            case FileObserver.DELETE:
            case FileObserver.MOVED_FROM:
                releaseInvalidPathObserver(fullPath);
                break;
        }
        onEvent(event, fullPath);
    }


    protected String path() {
        return "/storage/emulated/0/AAAA";
    }

    protected int eventMask() {
        return FileObserver.ALL_EVENTS;
    }

    protected void onEvent(int event, String eventPath) {

    }

}
