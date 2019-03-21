package com.albertech.demo.fileobserver.base;

import android.os.FileObserver;
import android.util.Log;

import com.albertech.demo.fileobserver.base.IRecursiveFileObserver;

import java.io.File;
import java.io.FileFilter;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;



public class SimpleRecursiveFileObserverImpl implements IRecursiveFileObserver, FileObserverFactory.IFileEvent {


    private final Map<String, FileObserver> OBSERVERS = new ConcurrentHashMap<>();

    private final FileObserverFactory FACTORY = new FileObserverFactory(OBSERVERS, this, eventMask());

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
                if (!OBSERVERS.containsKey(path)) {
                    FACTORY.create(path);
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


    @Override
    public void createObservers() {
        new Thread(OBSERVER_CREATER).start();
    }

    @Override
    public final void startWatching() {
        new Thread(START_WATCHING).start();
    }

    @Override
    public final void stopWatching() {
        new Thread(STOP_WATCHING).start();
    }

    @Override
    public final void onFileEvent(int event, String path) {
        event &= FileObserver.ALL_EVENTS;
        if (event == FileObserver.CREATE) {
            createObservers();
        }
        onEvent(event, path);
    }


    protected String path() {
        return "/storage/emulated/0/DCIM";
    }

    protected int eventMask() {
        return FileObserver.ALL_EVENTS;
    }

    protected void onEvent(int event, String path) {

    }

}
