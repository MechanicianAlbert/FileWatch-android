package com.albertech.filewatch.core.scan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;


public class FileScanner extends BroadcastReceiver implements IFileScan {

    private static final String TAG = FileScanner.class.getSimpleName();


    private final String EXTERNAL_ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    private final LinkedBlockingQueue<String> SCAN_TASK_QUEUE = new LinkedBlockingQueue<>();

    private final Runnable SCAN_TASK_EXECUTOR = new Runnable() {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    while (mIsScanning) {
                        synchronized (SCAN_TASK_QUEUE) {
                            SCAN_TASK_QUEUE.wait();
                        }
                    }
                    String path = SCAN_TASK_QUEUE.take();
                    mContext.sendBroadcast(getScanIntent(path));
                    mIsScanning = true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };


    private Context mContext;
    private IFileScanListener mListener;
    private Thread mScanThread;
    private volatile boolean mIsScanning;


    public FileScanner(Context context, IFileScanListener listener) {
        if (context == null) {
            throw new NullPointerException("Context is null");
        }
        mContext = context;
        mListener = listener;
        watchScan();
        startExecuteScanTask();
    }

    @Override
    public void init() {
        // TODO: 2019/3/28
    }

    @Override
    public void release() {
        // TODO: 2019/3/28
    }

    @Override
    public void scan(String path) {
        SCAN_TASK_QUEUE.add(TextUtils.isEmpty(path) ? EXTERNAL_ROOT_PATH : path);
        checkScanThread();
    }


    private void watchScan() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_MEDIA_SCANNER_STARTED);
        filter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);
        filter.addDataScheme("file");
        filter.setPriority(Integer.MAX_VALUE);
        mContext.registerReceiver(this, filter);
    }

    private void startExecuteScanTask() {
        mScanThread = new Thread(SCAN_TASK_EXECUTOR);
        mScanThread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                startExecuteScanTask();
            }
        });
        mScanThread.start();
    }

    private void checkScanThread() {
        if (mScanThread == null || !mScanThread.isAlive() || mScanThread.isInterrupted()) {
            startExecuteScanTask();
        }
    }

    private Intent getScanIntent(String path) {
        File file = new File(path);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(file));
        return intent;
    }


    @Override
    public final void onReceive(Context context, Intent intent) {
        String action;
        Uri uri;
        String path;
        if (intent != null
                && !TextUtils.isEmpty(action = intent.getAction())
                && (uri = intent.getData()) != null
                && !TextUtils.isEmpty(path = uri.getPath())) {

            if (Intent.ACTION_MEDIA_SCANNER_STARTED.equals(action)) {
                Log.d(TAG, "Scan started, path: " + path);
            } else if (Intent.ACTION_MEDIA_SCANNER_FINISHED.equals(action)) {
                Log.d(TAG, "Scan finished, path: " + path);
                synchronized (SCAN_TASK_QUEUE) {
                    mIsScanning = false;
                    SCAN_TASK_QUEUE.notify();
                }

                if (mListener != null) {
                    mListener.onScanResult(path);
                }
            }
        }
    }

}
