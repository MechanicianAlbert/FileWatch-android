package com.albertech.demo.fileobserver.base;

import android.os.FileObserver;
import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SimpleRecursiveFileObserverImpl implements IRecursiveFileObserver, IFileEvent {


    private final Map<String, SingleDirectoryObserver> OBSERVERS = new ConcurrentHashMap<>();

    private final ExecutorService EXECUTOR = Executors.newCachedThreadPool();

    private final FileFilter FILE_FILTER = new FileFilter() {
        @Override
        public boolean accept(File f) {
            return f.isDirectory() && !".".equals(f.getName()) && !"..".equals(f.getName());
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
        SingleDirectoryObserver o = new SingleDirectoryObserver(path, eventMask());
        OBSERVERS.put(path, o);
        o.setListener(this);
        o.startWatching();
    }

    private final void releaseInvalidPathObserver(String path) {
        try {
            SingleDirectoryObserver o = OBSERVERS.get(path);
            if (o != null) {
                o.stopWatching();
                o.removeListener();
            }
            OBSERVERS.remove(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public final void onFileEvent(String selfPath, int event, String eventPath) {
        event &= FileObserver.ALL_EVENTS;
        switch (event) {
            case FileObserver.CREATE:
            case FileObserver.MOVED_FROM:
                createObservers();
                break;
            case FileObserver.DELETE_SELF:
            case FileObserver.MOVE_SELF:
                releaseInvalidPathObserver(eventPath);
                break;
        }
        onEvent(selfPath, event, eventPath);
    }


    protected String path() {
        return "/storage/emulated/0/DCIM";
    }

    protected int eventMask() {
        return FileObserver.ALL_EVENTS;
    }

    protected void onEvent(String parentPath, int event, String eventPath) {

    }

}
