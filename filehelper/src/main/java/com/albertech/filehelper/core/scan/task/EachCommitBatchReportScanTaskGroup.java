package com.albertech.filehelper.core.scan.task;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.text.TextUtils;

import com.albertech.filehelper.core.CommonExecutor;
import com.albertech.filehelper.core.scan.scanner.GlobalScanner;
import com.albertech.filehelper.log.LogUtil;
import com.albertech.filehelper.log.ITAG;

import java.io.File;
import java.util.concurrent.ScheduledFuture;

/**
 * 扫描任务组实现类
 * <p>
 * 特性: 逐一提交待扫描路径到系统媒体库扫描器, 批量上报至调度器
 */
public class EachCommitBatchReportScanTaskGroup implements IFileScanTaskGroup, MediaScannerConnection.OnScanCompletedListener {

    private static final String TAG = EachCommitBatchReportScanTaskGroup.class.getSimpleName();

    /**
     * 任务组中断上报器
     * 用于任务组异常中断时, 上报唤醒调度器, 使其停止阻塞并处理其队列中后续任务组
     * <p>
     * 说明:
     * 调度器对任务组的处理是串行的, 在一个任务组全部完成后, 才会停止阻塞, 处理其队列中后续任务组
     * 调度器在一个任务组周期中, 会多次收到批量上报,
     * 如任务组没有异常中断, 调度器会在最后一次批量上报中收到全部完成信息
     * 任务组可能出现异常中断, 如扫描完成之前拔出U盘等场景,
     * 中断将调度器无法收到任务组全部完成的信息, 调度器将持续阻塞, 后续任务组无法得到执行
     * 异常后上报中断, 可使调度器停止阻塞, 恢复调度
     */
    private final Runnable BREAK_REPORTER = new Runnable() {
        @Override
        public void run() {
            // 上报扫描结果
            if (mListener != null) {
                mListener.onScanBatchResult(mRootPath, true);
            }
            // 上报后, 释放自身, 以便被自动回收空间
            release();
        }
    };

    /**
     * 任务组批量完成上报器, 通知上层部分路径扫描完成
     */
    private final Runnable BATCH_REPORTER = new Runnable() {
        @Override
        public void run() {
            boolean hasCompleted = hasCompleted();
            LogUtil.d(ITAG.SCAN,
                    "Need to report batch scan result: ",
                    "CompleteCountInCurrentBatch: " + mCompleteCountInCurrentBatch,
                    "HasCompleted: " + hasCompleted
            );
            // 清空本批次完成任务计数
            mCompleteCountInCurrentBatch = 0;
            LogUtil.d(ITAG.SCAN, "Report to scheduler, schedular: " + mListener);
            // 上报扫描结果
            if (mListener != null) {
                mListener.onScanBatchResult(mRootPath, hasCompleted);
                LogUtil.d(ITAG.SCAN, "Report to scheduler success");
            }
            if (hasCompleted) {
                release();
            }
        }
    };

    /**
     * 批量规模数量, 每完成此数量的扫描, 就上报一次
     */
    private final int BATCH_COUNT;

    /**
     * 扫描任务的根路径, 上层订阅扫描结果使用的路径
     */
    private String mRootPath;

    /**
     * 扫描结果监听
     */
    private IFileScanTaskGroupListener mListener;

    /**
     * 任务组最末路径, 此路径扫描结束后, 标识此任务组全部扫描任务完成
     */
    private String mLastPath;

    /**
     * 当前最新完成扫描的路径;
     */
    private String mCurrentPath;

    /**
     * 当前最新一批已完成扫描的数量
     */
    private int mCompleteCountInCurrentBatch;

    /**
     * 中断上报器延迟上报的future实例, 用于取消前次对中断上报器设定的延迟执行
     */
    private ScheduledFuture mBreakReportFuture;


    public EachCommitBatchReportScanTaskGroup(int batchCount) {
        BATCH_COUNT = batchCount;
    }


    @Override
    public void scanPathTree(Context context, String rootPath, IFileScanTaskGroupListener listener) {
        mRootPath = rootPath;
        mListener = listener;
        // 监听全局扫描器的扫描完成事件
        GlobalScanner.getInstance().setListener(this);
        // 向系统媒体库扫描器提交扫描任务组
        commitScanTaskGroupToMediaScanner();
    }


    /**
     * 向系统媒体库扫描器提交扫描任务组
     */
    private void commitScanTaskGroupToMediaScanner() {
        try {
            LogUtil.d(ITAG.SCAN, "Commit scan task group", "root path: " + mRootPath);
            commitPathTreeNodeRecursively(new File(mRootPath), false);
            LogUtil.d(ITAG.SCAN, "Commit scan task group success");
        } catch (Exception e) {
            LogUtil.e(ITAG.SCAN, "Commit scan task group exception: ", e);
            e.printStackTrace();
        }
    }

    /**
     * 递归根目录, 提交其所有子文件路径及其自身路径
     *
     * @param f            根目录文件
     * @param calledBySelf 是否被递归调用, 用于判断是否完成目录树中最后一个路径的提交
     *                     在此方法内调用自身传入true, 否则均传false
     */
    private void commitPathTreeNodeRecursively(File f, boolean calledBySelf) {
        if (f != null && f.exists()) {
            if (f.isDirectory()) {
                File[] files = f.listFiles();
                for (File file : files) {
                    commitPathTreeNodeRecursively(file, true);
                }
            }
            // 从递归中结束后, 可能消耗了很长时间,
            // 此时应重新判断文件是否存在, 以免向系统媒体库扫描器提交过多失效路径
            if (f.exists()) {
                String path = f.getAbsolutePath();
                LogUtil.d(ITAG.SCAN,
                        "Commit node task to scanner"
                        , "path: " + path);
                GlobalScanner.getInstance().commitSingleScanTask(path);
                if (!calledBySelf) {
                    mLastPath = path;
                    LogUtil.d(ITAG.SCAN,
                            "The last path has been committed"
                            , "LastPath: " + mLastPath);
                }
            }
        }
    }

    /**
     * 释放自身
     */
    private void release() {
        LogUtil.d(ITAG.SCAN, "EachCommitBatchReportScanTaskGroup release");
        // TODO: 2019/8/24  mListener 应置空, 但 release 常被非预期调用, 找到原因之前, 暂不释放 mListener
        // 释放调度器
//        mListener = null;
        // 从全局扫描器中释放自身
//        GlobalScanner.getInstance().releaseListener(this);
        // 取消最新设置的中断上报器延迟执行
        cancelBreakReport();
    }

    /**
     * 取消中断上报器延迟执行
     */
    private void cancelBreakReport() {
        if (mBreakReportFuture != null) {
            mBreakReportFuture.cancel(false);
            mBreakReportFuture = null;
        }
    }

    private boolean shouldReport() {
        boolean needReport = mCompleteCountInCurrentBatch >= BATCH_COUNT || hasCompleted();
        LogUtil.d(ITAG.SCAN, "shouldReport: " + needReport);
        return needReport;
    }

    private boolean hasCompleted() {
        boolean hasCompleted = TextUtils.equals(mCurrentPath, mLastPath);
        LogUtil.d(ITAG.SCAN, "hasCompleted: " + hasCompleted);
        return hasCompleted;
    }


    /**
     * 单路径扫描完成回调
     *
     * @param path 扫描完成的路径
     * @param uri  文件uri
     */
    @Override
    public void onScanCompleted(String path, Uri uri) {
        LogUtil.d(ITAG.SCAN,
                "Single path scan finished",
                "CompleteCountInCurrentBatch: " + mCompleteCountInCurrentBatch,
                "path: " + path
        );
        // 取消前次单路径扫描完成时, 对中断上报器设置的延迟执行
        cancelBreakReport();
        // 更新当前最新完成扫描的路径
        mCurrentPath = path;
        // 更新本批次任务完成数量
        mCompleteCountInCurrentBatch++;
        // 检查是否需要上报
        if (shouldReport()) {
            // 上报批量扫描完成
            CommonExecutor.exe(BATCH_REPORTER);
        }
        // 每次单路径扫描完成, 重新设置延迟1000毫秒执行中断上报
        // 如任务组正常执行完成, 最后一次设置的延迟上报, 会在最后一次批量上报中被取消
        mBreakReportFuture = CommonExecutor.schedule(BREAK_REPORTER, 1000);
    }

}
