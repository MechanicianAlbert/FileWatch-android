package com.albertech.filehelper.core.scan;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.albertech.filehelper.core.IConstant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 文件扫描实现类
 * 主动通知系统开始对指定路径的扫描, 并将扫描完成的结果上报到分发器
 * <p>
 * 扫描是发送广播通知系统自行扫描, 系统同一时间只能执行一项扫描任务, 不可并发
 */
public class FileScanner implements IFileScan, IConstant, MediaScannerConnection.OnScanCompletedListener {

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
                    mScanningPath = SCAN_TASK_QUEUE.take();
                    // 通知系统扫描此任务路径
                    scanPath(mScanningPath);
                    // 标识扫描正在进行中
                    mIsScanning = true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    /**
     * 扫描轮询线程唤醒单元, 扫描任务结束后, 通知任务轮询线程停止阻塞, 执行下一个扫描任务
     */
    private final Runnable SCAN_TASK_NOTIFIER = new Runnable() {
        @Override
        public void run() {
            synchronized (SCAN_TASK_QUEUE) {
                Log.d(TAG, "Notify scan next task");
                // 标识未正在进行扫描
                mIsScanning = false;
                // 扫描结束, 通知执行单元停止阻塞, 执行下一个扫描任务
                SCAN_TASK_QUEUE.notify();
            }
        }
    };

    /**
     * 延迟执行唤醒单元的的Handler, 运行在主线程, 保证在程序未退出或崩溃时始终能够执行延迟任务
     */
    private final Handler HANDLER = new Handler(Looper.getMainLooper());

    private final String[] EMPTY_STRING_ARRAY = new String[0];


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
    /**
     * 正在进行扫描的任务的根路径, 上层订阅扫描结果使用的路径
     */
    private volatile String mScanningPath;
    /**
     * 正在进行扫描的任务列表最末路径, 此路径扫描结束后, 标识一个递归扫描任务完成
     */
    private volatile String mScanningLastPath;


    public FileScanner(Context context, IFileScanListener listener) {
        if (context == null) {
            throw new NullPointerException("Context is null");
        }
        mContext = context;
        mListener = listener;
    }


    /**
     * 开启扫描任务执行线程
     */
    private void prepareScanThread() {
        mScanThread = new Thread(SCAN_TASK_EXECUTOR);
        mScanThread.setDaemon(true);
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
     * MediaScannerConnection的扫描不支持自动递归目录, 需要自行组织递归目录扫描逻辑
     *
     * @param path 路径
     */
    private void scanPath(String path) {
        // 递归扫描开始
        Log.d(TAG, "Scan task started, path: " + path);
        File f = new File(path);
        List<String> list = new ArrayList<>();
        addSubPathRecursively(list, f);
        final String[] paths = list.toArray(EMPTY_STRING_ARRAY);
        mScanningLastPath = paths[paths.length - 1];
        MediaScannerConnection.scanFile(mContext, paths, null, this);
    }

    /**
     * 向集合中递归添加某文件的路径及其所有子文件路径
     *
     * @param list 路径集合
     * @param f    文件
     */
    private void addSubPathRecursively(List<String> list, File f) {
        if (f != null && list != null) {
            list.add(f.getAbsolutePath());
            if (f.isDirectory()) {
                File[] files = f.listFiles();
                for (File file : files) {
                    addSubPathRecursively(list, file);
                }
            }
        }
    }

    @Override
    public void init() {
        // 初始化系统扫描工作线程
        prepareScanThread();
        Log.d(TAG, "Scanner init success");
    }

    @Override
    public void release() {
        mContext = null;
        // TODO: 2019/5/16  退出扫描线程
        Log.d(TAG, "Scanner release success");
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
            Log.d(TAG, "Scan task is not duplicated, add to queue");
            // 队列中没有包含本任务路径的任务, 添加任务进入队列
            SCAN_TASK_QUEUE.add(TextUtils.isEmpty(path) ? SD_CARD_PATH : path);
        } else {
            Log.d(TAG, "Scan task is duplicated, drop");
        }
        // 每次不论扫描任务是否需要添加, 都检查线程是否可用
        checkScanThread();
    }

    @Override
    public void onScanCompleted(String path, Uri uri) {
        // 单路径扫描完成
        Log.d(TAG, "Single path scan finished, path: " + path);
        // 取消上一个单路径扫描设置的延迟唤醒
        HANDLER.removeCallbacksAndMessages(null);
        if (TextUtils.equals(path, mScanningLastPath)) {
            // 所有路径扫描完成
            Log.d(TAG, "The last path of this recursive scan finished");
            // 唤醒扫描任务轮询线程
            SCAN_TASK_NOTIFIER.run();
            // 上报扫描结果
            if (mListener != null) {
                mListener.onScanResult(mScanningPath);
            }
        } else {
            // 在每个单路径扫描结束, 未完全扫描所有任务路径时, 重新设置延迟3秒唤醒任务轮询线程
            // 扫描可能异常中断, 如扫描完成之前拔出U盘, 不设置延迟唤醒, 任务轮询线程将持续阻塞
            HANDLER.postDelayed(SCAN_TASK_NOTIFIER, 3000);
        }
    }

}
