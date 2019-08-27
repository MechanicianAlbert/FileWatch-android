package com.albertech.filehelper.core.scan;

import android.content.Context;
import android.text.TextUtils;

import com.albertech.filehelper.log.LogUtil;
import com.albertech.filehelper.core.scan.task.IFileScanTaskGroup;
import com.albertech.filehelper.log.ITAG;
import com.albertech.filehelper.core.scan.scanner.GlobalScanner;
import com.albertech.filehelper.core.scan.task.IFileScanTaskGroupListener;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 文件扫描任务组调度类
 * 扫描是通知系统自行扫描, 系统同一时间只能执行一项扫描任务, 不可并发
 * 故扫描任务需要此类进行调度, 以确保串行执行
 */
public class FileScanScheduler
        implements
        IFileScan,
        IFileScanTaskGroupListener {

    // 日志标签
    private static final String TAG = FileScanScheduler.class.getSimpleName();

    /**
     * 扫描任务组队列 (阻塞队列), 没有扫描任务时, 获取元素会阻塞
     */
    private final LinkedBlockingQueue<String> QUEUE = new LinkedBlockingQueue<>();

    /**
     * 调度线程执行单元, 串行执行队列中等待的扫描任务组
     */
    private final Runnable SCHEDULE_TASK_EXECUTOR = this::scheduleLoop;

    /**
     * 调度线程唤醒单元, 任务组执行完成后, 通知调度线程停止阻塞, 执行下一个扫描任务组
     */
    private final Runnable SCAN_TASK_NOTIFIER = new Runnable() {
        @Override
        public void run() {
            synchronized (QUEUE) {
                LogUtil.d(ITAG.SCAN, "Notify scan next task");
                // 标识未正在进行扫描
                mIsScanning = false;
                // 扫描结束, 通知执行单元停止阻塞, 执行下一个扫描任务
                QUEUE.notify();
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
    private Thread mScheduleThread;
    /**
     * 扫描正在进行标识
     */
    private volatile boolean mIsScanning;


    public FileScanScheduler(Context context, IFileScanListener listener) {
        if (context == null) {
            throw new NullPointerException("Context is null");
        }
        mContext = context;
        mListener = listener;
    }


    /**
     * 检查扫描任务组调度线程
     */
    private void checkScheduleThread() {
        if (mScheduleThread == null || !mScheduleThread.isAlive() || mScheduleThread.isInterrupted()) {
            LogUtil.e(ITAG.SCAN, "Schedule thread is unavailable, build and start a new one");
            // 如线程不可用, 重建
            resetScheduleThread();
        } else {
            LogUtil.d(ITAG.SCAN, "Schedule thread is available");
        }
    }

    /**
     * 开启扫描任务组调度线程
     */
    private void resetScheduleThread() {
        mScheduleThread = new Thread(SCHEDULE_TASK_EXECUTOR);
        mScheduleThread.setDaemon(true);
        // 在非预期异常中重启线程, 防止线程意外退出, 扫描任务得不到执行
        mScheduleThread.setUncaughtExceptionHandler((t, e) -> {
            LogUtil.e(ITAG.SCAN, "Schedule thread exception, build and start a new one");
            resetScheduleThread();
        });
        mScheduleThread.start();
        LogUtil.d(ITAG.SCAN, "A new schedule thread has been built and started");
    }

    /**
     * 调度线程死循环
     */
    private void scheduleLoop() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                // 如当前正在进行扫描任务, 持续阻塞, 下一个任务等待上一个执行完成后才可以执行
                while (mIsScanning) {
                    synchronized (QUEUE) {
                        QUEUE.wait();
                    }
                }
                // 标识扫描正在进行中
                mIsScanning = true;
                // 获取下一个扫描任务的目标路径, 执行扫描任务
                scanPath(QUEUE.take());
            } catch (InterruptedException e) {
                LogUtil.e(ITAG.SCAN, "Schedule loop exception: ", e);
            }
        }
        LogUtil.e(ITAG.SCAN, "Schedule thread stop");
    }

    /**
     * 通知系统服务对路径进行扫描
     * MediaScannerConnection的扫描不支持自动递归目录, 需要自行组织递归目录扫描逻辑
     *
     * @param path 路径
     */
    private void scanPath(String path) {
        IFileScanTaskGroup task = ScanTaskGroupFactory.create(path);
        task.scanPathTree(mContext, path, this);
    }

    /**
     * 结束调度线程
     */
    private void releaseScheduleThread() {
        if (mScheduleThread != null) {
            mScheduleThread.setUncaughtExceptionHandler(null);
            mScheduleThread.interrupt();
        }
    }


    @Override
    public void init() {
        try {
            LogUtil.d(ITAG.SCAN, "Scan scheduler init");
            // 初始化系统扫描工作线程
            resetScheduleThread();
            GlobalScanner.getInstance().init(mContext);
            LogUtil.d(ITAG.SCAN, "Scan scheduler init success");
        } catch (Exception e) {
            LogUtil.e(ITAG.SCAN, "Scan scheduler init exception: ", e);
        }
    }

    @Override
    public void release() {
        try {
            LogUtil.d(ITAG.SCAN, "GlobalScanner release");
            mContext = null;
            // 结束调度线程
            releaseScheduleThread();
            LogUtil.d(ITAG.SCAN, "GlobalScanner release success");
        } catch (Exception e) {
            LogUtil.e(ITAG.SCAN, "GlobalScanner release exception: ", e);
        }
    }

    @Override
    public void scan(String path) {
        if (path == null || TextUtils.isEmpty(path.trim())) {
            // 若路径为空, 不做任何处理
            return;
        }
        // 扫描任务组重复的标识位
        boolean isDuplicateScanTask = false;

        // 队列中都是还未执行的任务组, 遍历任务组队列, 判断是否添加了多余的扫描任务组
        for (String pathInQueue : QUEUE) {
            // 队列中已存在任务组根路径为本次扫描任务组根路径的父路径的任务, 不需要再添加
            isDuplicateScanTask = path.startsWith(pathInQueue);
            if (isDuplicateScanTask) {
                break;
            }
        }
        // 如果当前正在进行扫描, 无论正在进行的任务组是否包含此路径, 都视为不多余. 只判断还未扫描的任务组路径

        if (!isDuplicateScanTask) {
            LogUtil.d(ITAG.SCAN,
                    "Scan task is not duplicated, add to queue",
                    "Path: " + path);
            // 队列中没有包含本任务组路径的任务组, 添加任务组进入队列
            QUEUE.add(path);
        } else {
            LogUtil.d(ITAG.SCAN,
                    "Scan task is duplicated, drop",
                    "Path: " + path);
        }
        // 每次不论扫描任务组是否需要添加, 都检查线程是否可用
        checkScheduleThread();
    }


    @Override
    public void onScanBatchResult(String path, boolean completed) {
        LogUtil.d(ITAG.SCAN,
                "onScanBatchResult",
                "completed: " + completed,
                "Path: " + path
        );
        if (mListener != null) {
            mListener.onScanResult(path);
        }
        if (completed) {
            SCAN_TASK_NOTIFIER.run();
        }
    }

}
