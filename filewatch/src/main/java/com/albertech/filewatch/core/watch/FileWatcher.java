package com.albertech.filewatch.core.watch;

import android.os.Environment;
import android.os.FileObserver;
import android.text.TextUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class FileWatcher implements IFileWatch, IFileWatchListener {

    private static final String TAG = FileWatcher.class.getSimpleName();


    private final Map<String, SingleDirectoryObserver> OBSERVERS = new ConcurrentHashMap<>();

    private final ExecutorService EXECUTOR = Executors.newCachedThreadPool();

    private final FileFilter VALID_READABLE_DIRECTORY_FILTER = new FileFilter() {

        @Override
        public boolean accept(File f) {
            return isValidReadableDirectory(f);
        }
    };

    private final Runnable OBSERVER_CREATER = new Runnable() {

        @Override
        public void run() {
            Stack<String> s = new Stack<>();
            s.push(WATCH_PATH);
            while (!s.isEmpty()) {
                String path = s.pop();

                FileObserver o = OBSERVERS.get(path);
                if (o != null) {
                    o.startWatching();
                } else {
                    createSingleDirectoryObserver(path);
                }

                File f = new File(path);
                File[] children = f.listFiles(VALID_READABLE_DIRECTORY_FILTER);
                if (children != null) {
                    for (File child : children) {
                        s.push(child.getAbsolutePath());
                    }
                }
            }
        }
    };

    private final Runnable RESUME_WATCHING = new Runnable() {

        @Override
        public void run() {
            for (FileObserver o : OBSERVERS.values()) {
                o.startWatching();
            }
        }
    };

    private final Runnable PAUSE_WATCHING = new Runnable() {

        @Override
        public void run() {
            for (FileObserver o : OBSERVERS.values()) {
                o.stopWatching();
            }
        }
    };

    private final Runnable OBSERVER_DESTROYER = new Runnable() {

        @Override
        public void run() {
            mListener = null;
            Iterator<String> i = OBSERVERS.keySet().iterator();
            while (i.hasNext()) {
                releaseInvalidPathObserver(i.next());
            }
        }
    };

    private final String WATCH_PATH;
    private final int EVENT_MASK;


    private IFileWatchListener mListener;


    private FileWatcher(String path, int eventMask, IFileWatchListener listener) {
        WATCH_PATH = path;
        EVENT_MASK = eventMask & FileObserver.ALL_EVENTS;
        mListener = listener;
    }


    private void createSingleDirectoryObserver(String path) {
        OBSERVERS.put(path, new SingleDirectoryObserver(path, EVENT_MASK, this));
    }

    private void releaseInvalidPathObserver(String path) {
        SingleDirectoryObserver o = OBSERVERS.remove(path);
        if (o != null) {
            o.release();
        }
    }

    private boolean isValidReadableDirectoryPath(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        } else {
            File f = new File(path);
            return isValidReadableDirectory(f);
        }
    }

    private boolean isValidReadableDirectory(File f) {
        return f != null
                && f.canRead()
                && f.isDirectory()
                && !".".equals(f.getName())
                && !"..".equals(f.getName());
    }


    @Override
    public final void startWatching() {
        EXECUTOR.execute(OBSERVER_CREATER);
    }

    @Override
    public void resumeWatching() {
        EXECUTOR.execute(RESUME_WATCHING);
    }

    @Override
    public final void pauseWatching() {
        EXECUTOR.execute(PAUSE_WATCHING);
    }

    @Override
    public final void stopWatching() {
        EXECUTOR.execute(OBSERVER_DESTROYER);
    }

    @Override
    public final void onEvent(int event, String parentPath, String fullPath) {
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
        if (mListener != null && event > 0) {
            mListener.onEvent(event, parentPath, fullPath);
        }
    }


    public static final class Builder {

        private static final String DEFAULT_WATCH_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
        private static final int DEFAULT_WATCH_EVENT = FileObserver.ALL_EVENTS;


        private String mWatchPath = DEFAULT_WATCH_PATH;
        private int mEventMask = DEFAULT_WATCH_EVENT;
        private IFileWatchListener mListener;


        public Builder path(String path) {
            mWatchPath = path;
            return this;
        }

        public Builder eventMask(int eventMask) {
            mEventMask = eventMask;
            return this;
        }

        public Builder listener(IFileWatchListener listener) {
            mListener = listener;
            return this;
        }

        public FileWatcher build() {
            try {
                return new FileWatcher(mWatchPath, mEventMask, mListener);
            } finally {
                mListener = null;
            }
        }

    }

}