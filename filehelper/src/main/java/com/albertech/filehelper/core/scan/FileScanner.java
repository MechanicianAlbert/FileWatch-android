package com.albertech.filehelper.core.scan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.albertech.filehelper.core.IConstant;

import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 文件扫描实现类
 * 主动通知系统开始对指定路径的扫描, 并将扫描完成的结果上报到分发器
 */
public class FileScanner extends BroadcastReceiver implements IFileScan, IConstant {

    // 日志标签
    private static final String TAG = FileScanner.class.getSimpleName();

    /**
     * 扫描任务队列 (阻塞队列), 没有扫描任务时, 获取元素会阻塞
     */
    private final LinkedBlockingQueue<String> SCAN_TASK_QUEUE = new LinkedBlockingQueue<>();

    /**
     * 扫描任务执行单元, 串行执行队列中等待的扫描任务
     */
    private final Runnable SCAN_TASK_EXECUTOR = new Runnable() {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    // 如当前正在进行扫描任务, 持续阻塞
                    while (mIsScanning) {
                        synchronized (SCAN_TASK_QUEUE) {
                            SCAN_TASK_QUEUE.wait();
                        }
                    }
                    // 获取下一个扫描任务的扫描路径
                    String path = SCAN_TASK_QUEUE.take();
                    // 通知系统扫描此任务路径
                    mContext.sendBroadcast(getScanIntent(path));
                    // 标识扫描正在进行中
                    mIsScanning = true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };


    private Context mContext;
    /**
     * 扫描监听
     */
    private IFileScanListener mListener;
    /**
     * 扫描线程
     */
    private Thread mScanThread;
    /**
     * 扫描正在进行标识
     */
    private volatile boolean mIsScanning;


    public FileScanner(Context context, IFileScanListener listener) {
        if (context == null) {
            throw new NullPointerException("Context is null");
        }
        mContext = context;
        mListener = listener;
    }

    @Override
    public void init() {
        // 开启
        startWatchScan();
        startExecuteScanTask();
    }

    @Override
    public void release() {
        // TODO: 2019/3/28
    }

    @Override
    public void scan(String path) {
        SCAN_TASK_QUEUE.add(TextUtils.isEmpty(path) ? SD_CARD_PATH : path);
        checkScanThread();
    }

    /**
     * 注册接收扫描完成结果的系统广播, 用于获得扫描完成结果后传递给分发器
     */
    private void startWatchScan() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_MEDIA_SCANNER_STARTED);
        filter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);
        filter.addDataScheme("file");
        filter.setPriority(Integer.MAX_VALUE);
        mContext.registerReceiver(this, filter);
    }

    /**
     * 开启扫描任务执行线程
     */
    private void startExecuteScanTask() {
        mScanThread = new Thread(SCAN_TASK_EXECUTOR);
        // 在非预期异常中重启线程, 防止线程意外退出, 扫描任务得不到执行
        mScanThread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                startExecuteScanTask();
            }
        });
        mScanThread.start();
    }

    /**
     * 检查扫描任务执行线程
     */
    private void checkScanThread() {
        if (mScanThread == null || !mScanThread.isAlive() || mScanThread.isInterrupted()) {
            // 如线程不可用, 重建
            startExecuteScanTask();
        }
    }

    /**
     * 获得发送广播通知系统扫描指定路径的意图
     * @param path 路径
     * @return 扫描广播意图
     */
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
        // 获得广播信息
        if (intent != null
                && !TextUtils.isEmpty(action = intent.getAction())
                && (uri = intent.getData()) != null
                && !TextUtils.isEmpty(path = uri.getPath())) {

            if (Intent.ACTION_MEDIA_SCANNER_STARTED.equals(action)) {
                // 扫描开始
                Log.d(TAG, "Scan started, path: " + path);
            } else if (Intent.ACTION_MEDIA_SCANNER_FINISHED.equals(action)) {
                // 扫描完成
                Log.d(TAG, "Scan finished, path: " + path);
                synchronized (SCAN_TASK_QUEUE) {
                    // 标识未正在进行扫描
                    mIsScanning = false;
                    // 通知执行单元执行下一个扫描任务
                    SCAN_TASK_QUEUE.notify();
                }
                // 上报扫描结果
                if (mListener != null) {
                    mListener.onScanResult(path);
                }
            }
        }
    }

}
