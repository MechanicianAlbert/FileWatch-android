package com.albertech.filewatch.core.dispatch;

import android.content.Context;
import android.os.Binder;
import android.os.Environment;
import android.os.FileObserver;
import android.text.TextUtils;

import com.albertech.filewatch.api.IFileWatchSubscriber;
import com.albertech.filewatch.core.query.FileQueryer;
import com.albertech.filewatch.core.query.IFileQuery;
import com.albertech.filewatch.core.scan.FileScanner;
import com.albertech.filewatch.core.scan.IFileScan;
import com.albertech.filewatch.core.scan.IFileScanListener;
import com.albertech.filewatch.core.watch.FileWatcher;
import com.albertech.filewatch.core.watch.IFileWatch;
import com.albertech.filewatch.core.watch.IFileWatchListener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class FileWatchDispatcher extends Binder implements IFileWatchDispatch,
        IFileWatchListener,
        IFileScanListener {

    private final Map<IFileWatchSubscriber, String> SUBSCRIBED_PATH = new ConcurrentHashMap<>();
    private final Map<IFileWatchSubscriber, Integer> SUBSCRIBED_TYPE = new ConcurrentHashMap<>();

    private final String WATCH_ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    private final int WATCH_EVENT_MASK = FileObserver.CREATE | FileObserver.DELETE;


    private Context mContext;
    private IFileWatch mWatcher;
    private IFileScan mScanner;
    private IFileQuery mQureyer;


    void init(Context context) {
        if (context == null) {
            throw new NullPointerException("Context cannot be null");
        }
        mContext = context;

        mWatcher = new FileWatcher.Builder()
                .path(WATCH_ROOT_PATH)
                .eventMask(WATCH_EVENT_MASK)
                .listener(this)
                .build();
        mWatcher.startWatching();
        mScanner = new FileScanner(mContext, this);
        mScanner.init();
        mQureyer = new FileQueryer();
    }

    void release() {
        SUBSCRIBED_PATH.clear();
        SUBSCRIBED_TYPE.clear();

        if (mWatcher != null) {
            mWatcher.stopWatching();
            mWatcher = null;
        }
        if (mScanner != null) {
            mScanner.release();
            mScanner = null;
        }
    }

    @Override
    public void subscribeFileWatch(IFileWatchSubscriber subscriber, int type, String path) {
        path = !TextUtils.isEmpty(path) ? path : WATCH_ROOT_PATH;
        SUBSCRIBED_PATH.put(subscriber, path);
        SUBSCRIBED_TYPE.put(subscriber, type);
    }

    @Override
    public void unsubscribeFileWatch(IFileWatchSubscriber subscriber) {
        SUBSCRIBED_PATH.remove(subscriber);
        SUBSCRIBED_TYPE.remove(subscriber);
    }

    @Override
    public void onEvent(int event, String parentPath, String childPath) {
        dispatchFileEvents(event, parentPath, childPath);
        if (mScanner != null) {
            mScanner.scan(parentPath);
        }
    }


    @Override
    public void onScanResult(String path) {
        dispatchScanResult(path);
    }


    private void dispatchFileEvents(int event, String parentPash, String childPath) {
        for (IFileWatchSubscriber subscriber : SUBSCRIBED_PATH.keySet()) {
            if (subscriber != null) {
                String subscribePath = SUBSCRIBED_PATH.get(subscriber);
                if (subscribePath != null && parentPash.startsWith(subscribePath)) {
                    subscriber.onEvent(event, parentPash, childPath);
                }
            }
        }
    }

    private void dispatchScanResult(String path) {
        for (IFileWatchSubscriber subscriber : SUBSCRIBED_PATH.keySet()) {
            if (subscriber != null) {
                String subscribePath = SUBSCRIBED_PATH.get(subscriber);
                if (subscribePath != null && path.startsWith(subscribePath)) {
                    subscriber.onScanResult(path);
                }
            }
        }
    }

}
