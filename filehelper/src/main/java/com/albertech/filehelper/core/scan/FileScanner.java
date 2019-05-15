package com.albertech.filehelper.core.scan;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.albertech.filehelper.core.IConstant;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 文件扫描实现类
 * 主动通知系统开始对指定路径的扫描, 并将扫描完成的结果上报到分发器
 * <p>
 * 扫描是发送广播通知系统自行扫描, 系统同一时间只能执行一项扫描任务, 不可并发
 */
public class FileScanner implements IFileScan, IConstant, MediaScannerConnection.MediaScannerConnectionClient {

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
                    // 如当前正在进行扫描任务, 持续阻塞, 下一个任务等待上一个执行完成后才可以执行
                    while (mIsScanning) {
                        synchronized (SCAN_TASK_QUEUE) {
                            SCAN_TASK_QUEUE.wait();
                        }
                    }
                    // 获取下一个扫描任务的扫描路径
                    String path = SCAN_TASK_QUEUE.take();
                    // 通知系统扫描此任务路径
                    scanPath(path);
//                    mContext.sendBroadcast(getScanIntent(path));
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
     * 文件扫描系统服务连接
     */
    private MediaScannerConnection mScanConn;
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


    /**
     * 初始化系统扫描服务
     */
    private void initScan() {
        mScanConn = new MediaScannerConnection(mContext, this);
        mScanConn.connect();
        if (mScanConn != null && mScanConn.isConnected()) {
            Log.d(TAG, "Scan service connect success");
        } else {
            Log.e(TAG, "Scan service connect failed");
        }
    }

    /**
     * 开启扫描任务执行线程
     */
    private void prepareScanThread() {
        mScanThread = new Thread(SCAN_TASK_EXECUTOR);
        // 在非预期异常中重启线程, 防止线程意外退出, 扫描任务得不到执行
        mScanThread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                Log.e(TAG, "Scan thread exception, rebuild and start scan thread");
                prepareScanThread();
            }
        });
        mScanThread.start();
        Log.d(TAG, "Scan thread has been built and restarted");

    }

    /**
     * 检查扫描任务执行线程
     */
    private void checkScanThread() {
        if (mScanThread == null || !mScanThread.isAlive() || mScanThread.isInterrupted()) {
            Log.e(TAG, "Scan thread is unavailable, rebuild a new one and start");
            // 如线程不可用, 重建
            prepareScanThread();
        } else {
            Log.d(TAG, "Scan thread is available");
        }
    }

    /**
     * 通知系统服务对路径进行扫描
     * @param path 路径
     */
    private void scanPath(String path) {
        // 扫描开始
        Log.d(TAG, "Scan started, path: " + path);
        if (mScanConn != null
                && mScanConn.isConnected()
                && !TextUtils.isEmpty(path)) {
            mScanConn.scanFile(path, null);
        }
    }

    @Override
    public void init() {
        Log.d(TAG, "Scanner init start");
        // 初始化系统扫描和工作线程
        initScan();
        prepareScanThread();
        Log.d(TAG, "Scanner init success");

    }

    @Override
    public void release() {
        try {
            Log.d(TAG, "Scanner release start");
            mContext = null;
            if (mScanConn != null) {
                mScanConn.disconnect();
            }
            Log.d(TAG, "Scanner release success");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Scanner release exception: " + e.getMessage(), e);
        }
    }

    @Override
    public void scan(String path) {
        if (TextUtils.isEmpty(path)) {
            // 若路径为空, 默认扫描SD卡根目录
            path = SD_CARD_PATH;
        }
        boolean isDuplicateScanTask = false;
        // 如果当前正在进行扫描, 无论正在进行的任务是否包含此路径, 都视为不多余.
        // 因为当前扫描可能已经将此路径扫描过了.

        // 遍历任务队列, 判断是否添加了多余的扫描任务
        for (String pathInQueue : SCAN_TASK_QUEUE) {
            // 队列中已存在任务路径为本次扫描任务路径的父路径的任务, 不需要再添加
            isDuplicateScanTask = path.startsWith(pathInQueue);
            if (isDuplicateScanTask) {
                break;
            }
        }
        if (!isDuplicateScanTask) {
            // 队列中没有包含本任务路径的任务, 添加任务进入队列
            SCAN_TASK_QUEUE.add(TextUtils.isEmpty(path) ? SD_CARD_PATH : path);
        }
        // 每次不论扫描任务是否需要添加, 都检查线程是否可用
        checkScanThread();
    }

    @Override
    public void onMediaScannerConnected() {
        Log.d(TAG, "MediaScan service connected");
    }

    @Override
    public void onScanCompleted(String path, Uri uri) {
        // 扫描完成
        Log.d(TAG, "Scan finished, path: " + path);
        synchronized (SCAN_TASK_QUEUE) {
            // 标识未正在进行扫描
            mIsScanning = false;
            // 扫描结束, 通知执行单元停止阻塞, 执行下一个扫描任务
            SCAN_TASK_QUEUE.notify();
        }
        // 上报扫描结果
        if (mListener != null) {
            mListener.onScanResult(path);
        }
    }

}
