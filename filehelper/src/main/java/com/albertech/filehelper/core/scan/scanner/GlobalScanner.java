package com.albertech.filehelper.core.scan.scanner;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;

import com.albertech.filehelper.log.LogUtil;
import com.albertech.filehelper.log.ITAG;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 全局媒体库扫描器客户端
 */
public class GlobalScanner implements MediaScannerConnection.MediaScannerConnectionClient {

    // *********** 单例 ************
    private enum Holder {
        SINGLETON;

        private final GlobalScanner INSTANCE;

        Holder() {
            INSTANCE = new GlobalScanner();
        }
    }

    private GlobalScanner() {
        if (Holder.SINGLETON != null && Holder.SINGLETON.INSTANCE != null) {
            throw new RuntimeException("This class should not be instantiate more than once");
        }
    }

    public static GlobalScanner getInstance() {
        return Holder.SINGLETON.INSTANCE;
    }
    // *********** 单例 ************



    private static final String TAG = GlobalScanner.class.getSimpleName();

    /**
     * 待提交路径队列
     */
    private final LinkedBlockingQueue<String> QUEUE = new LinkedBlockingQueue<>();
    /**
     * 提交线程执行单元, 串行提交队列中的待扫描路径
     */
    private final Runnable COMMIT_EXECUTOR = this::loop;

    private Context mContext;
    /**
     * 媒体库扫描服务长连接
     */
    private MediaScannerConnection mConnection;
    /**
     * 扫描完成监听, 通常由扫描任务组担任
     */
    private MediaScannerConnection.OnScanCompletedListener mListener;
    /**
     * 提交线程
     */
    private Thread mCommitThread;


    /**
     * 初始化
     * @param context
     */
    public void init(Context context) {
        if (context == null) {
            throw new NullPointerException("Context should not be null");
        }
        LogUtil.d(ITAG.SCAN, "GlobalScanner init");
        mContext = context;
        // 初始化提交线程
        resetCommitThread();
        // 初始化媒体库扫描服务长连接
        resetConnection();
        LogUtil.d(ITAG.SCAN, "GlobalScanner init success");
    }

    /**
     * 设置扫描完成监听
     * @param listener 扫描完成监听器
     */
    public void setListener(MediaScannerConnection.OnScanCompletedListener listener) {
        mListener = listener;
    }

    /**
     * 释放监听器
     * 用于释放已执行完成的任务组实例, 仅当传入的监听器与正在引用的监听器是相同实例时才释放
     * 当任务组调用此方法传入自身实例时, 如当前监听扫描器的监听者不是该任务组, 则不会被干扰
     * @param listener
     */
    public void releaseListener(MediaScannerConnection.OnScanCompletedListener listener) {
        if (mListener == listener) {
            mListener = null;
        }
    }

    /**
     * 提交扫描任务
     * @param paths 待扫描路径数组
     */
    public void commitSingleScanTask(String... paths) {
        for (String path : paths) {
            QUEUE.offer(path);
        }
        checkCommitThread();
    }

    /**
     * 检查提交线程
     */
    private void checkCommitThread() {
        if (mCommitThread == null || !mCommitThread.isAlive() || mCommitThread.isInterrupted()) {
            LogUtil.e(ITAG.SCAN, "Commit thread is unavailable, build and start a new one ");
            // 如线程不可用, 重建
            resetCommitThread();
        } else {
            LogUtil.d(ITAG.SCAN, "Commit thread is available");
        }
    }

    /**
     * 重建提交线程
     */
    private void resetCommitThread() {
        mCommitThread = new Thread(COMMIT_EXECUTOR);
        mCommitThread.setDaemon(true);
        // 在非预期异常中重启线程, 防止线程意外退出, 扫描任务得不到执行
        mCommitThread.setUncaughtExceptionHandler((t, e) -> {
            LogUtil.e(ITAG.SCAN, "Commit thread exception, build and start a new one");
            resetCommitThread();
        });
        mCommitThread.start();
        LogUtil.d(ITAG.SCAN, "A new commit thread has been built and started");
    }

    /**
     * 检查媒体库扫描服务长连接是否可用
     * @return 是否可用
     */
    private boolean isConnectionInvalid() {
        try {
            if (mConnection == null || !mConnection.isConnected()) {
                LogUtil.e(ITAG.SCAN, "Connection is invalid, should rebuild a new one");
                resetConnection();
                return true;
            } else {
                LogUtil.d(ITAG.SCAN, "Connection is valid");
                return false;
            }
        } catch (Exception e) {
            LogUtil.e(ITAG.SCAN, "Connection check exception: ", e);
            LogUtil.e(ITAG.SCAN, "Connection is invalid, should rebuild a new one");
            resetConnection();
            return true;
        }
    }

    /**
     * 重建媒体库扫描服务长连接
     */
    private void resetConnection() {
        mConnection = new MediaScannerConnection(mContext, this);
        mConnection.connect();
    }

    /**
     * 提交线程死循环
     */
    private void loop() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                // 获取下一个待扫描路径, 如队列为空则阻塞至有新路径加入
                String next = QUEUE.take();
                // 检查媒体库扫描服务长连接是否失效
                if (isConnectionInvalid()) {
                    LogUtil.e(ITAG.SCAN, "Connection unavailable, block");
                    // 长连接失效, 立刻阻塞, 不再提交, 待长连接重建成功后恢复提交
                    lock();
                }
                // 提交至系统扫描服务
                commitPathToConnection(next);
                LogUtil.d(ITAG.SCAN,
                        "Scan task commit, block to wait for result",
                        "path: " + next);
                // 阻塞, 直到系统扫描完成通知, 恢复执行后续提交
                lock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        LogUtil.e(ITAG.SCAN, "Commit thread stop");
    }

    /**
     * 阻塞
     * @throws InterruptedException 中断异常
     */
    private void lock() throws InterruptedException {
        synchronized (QUEUE) {
            QUEUE.wait();
        }
    }

    /**
     * 停止阻塞
     */
    private void unlock() {
        try {
            synchronized (QUEUE) {
                QUEUE.notify();
            }
        } catch (Exception e) {
            LogUtil.e(ITAG.SCAN, "unlock exception: ", e);
        }
    }

    /**
     * 提交待扫描路径到媒体库扫描服务
     * @param next 待扫描路径
     */
    private void commitPathToConnection(String next) {
        try {
            mConnection.scanFile(next, null);
        } catch (Exception e) {
            LogUtil.e(ITAG.SCAN, "Commit path to connection exception: ", e);
        }
    }


    /**
     * 与媒体库扫描服务建立长连接完成回调
     */
    @Override
    public void onMediaScannerConnected() {
        LogUtil.d(ITAG.SCAN, "MediaScanner service connected, notify to scan the taken path");
        // 通知提交线程, 长连接就绪, 可用提交待扫描路径
        unlock();
    }

    /**
     * 单路径扫描完成回调
     * @param path 扫描完成路径
     * @param uri 文件uri
     */
    @Override
    public void onScanCompleted(String path, Uri uri) {
        LogUtil.d(ITAG.SCAN, "onScanCompleted, path: " + path);
        // 上报扫描完成
        if (mListener != null) {
            mListener.onScanCompleted(path, uri);
        }
        LogUtil.d(ITAG.SCAN, "Last task scan finished, notify to take next path to scanner");
        // 通知提交线程, 当前扫描任务完成, 可以提交下一个待扫描路径
        unlock();
    }

}