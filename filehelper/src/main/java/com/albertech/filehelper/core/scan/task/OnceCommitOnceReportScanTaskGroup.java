package com.albertech.filehelper.core.scan.task;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.text.TextUtils;

import com.albertech.filehelper.log.LogUtil;
import com.albertech.filehelper.core.CommonExecutor;
import com.albertech.filehelper.log.ITAG;

import java.util.concurrent.ScheduledFuture;

/**
 * 单次上报扫描任务类
 * 对路径组扫描任务全部完成后仅进行1次上报
 */
public class OnceCommitOnceReportScanTaskGroup implements IFileScanTaskGroup, MediaScannerConnection.OnScanCompletedListener {

    private static final String TAG = OnceCommitOnceReportScanTaskGroup.class.getSimpleName();

    /**
     * 上报扫描结果的任务
     */
    private final Runnable REPORTER = new Runnable() {
        @Override
        public void run() {
            // 上报扫描结果
            if (mListener != null) {
                mListener.onScanBatchResult(mRootPath, true);
            }
            // 上报后, 释放自身, 以便被自动回收空间
            // 扫描任务理论上不很频繁, 暂不做复用
            release();
        }
    };

    /**
     * 扫描任务的根路径, 上层订阅扫描结果使用的路径
     */
    private String mRootPath;

    /**
     * 扫描结果监听
     */
    private IFileScanTaskGroupListener mListener;

    /**
     * 正在进行扫描的任务列表最末路径, 此路径扫描结束后, 标识一个递归扫描任务完成
     */
    private String mLastPath;

    /**
     * 延迟上报任务的future实例, 用于取消延迟上报任务
     */
    private ScheduledFuture mDelayReport;


    @Override
    public void scanPathTree(Context context, String rootPath, IFileScanTaskGroupListener listener) {
        mRootPath = rootPath;
        mListener = listener;
        commitScan(context);
    }


    private void commitScan(Context context) {
        try {
            LogUtil.d(ITAG.SCAN,
                    "Scan task started",
                    "path: " + mRootPath);
            final String[] paths = PathUtil.getPathTreeArray(mRootPath);
            mLastPath = paths[paths.length - 1];
            MediaScannerConnection.scanFile(context, paths, null, this);
            LogUtil.d(ITAG.SCAN, "Scan task commited");
        } catch (Exception e) {
            LogUtil.e(ITAG.SCAN, "Scan task exception: ", e);
            e.printStackTrace();
        }
    }

    private void release() {
        mListener = null;
        mDelayReport = null;
    }

    private void cancelReport() {
        if (mDelayReport != null) {
            mDelayReport.cancel(false);
        }
    }


    /**
     * 如果使用MediaScannerConnection.scanFile(...)方法对多个路径布置扫描任务,
     * 系统每完成一个单路径的扫描, 就会回调一次扫描完成方法
     * 系统回调次方法时, 不代表布置的扫描任务全部完成
     * 仅当最后一个路径扫描完成, 扫描任务才全部完成
     *
     * @param path 扫描完成的单一路径
     * @param uri 文件uri
     */
    @Override
    public void onScanCompleted(String path, Uri uri) {
        LogUtil.d(ITAG.SCAN,
                "Single path scan finished",
                "path: " + path);
        // 取消上一个单路径扫描设置的延迟唤醒
        cancelReport();
        if (TextUtils.equals(path, mLastPath)) {
            // 所有路径扫描完成
            LogUtil.d(ITAG.SCAN, "The last path of this task bas been finished scanning");
            // 唤醒扫描任务轮询线程
            CommonExecutor.exe(REPORTER);
        } else {
            // 在每个单路径扫描结束, 未完全扫描所有任务路径时, 重新设置延迟300毫秒秒唤醒任务轮询线程
            // 因为递归扫描任务可能异常中断, 如扫描完成之前拔出U盘,
            // 不设置延迟唤醒, 任务轮询线程在异常中断时, 将持续阻塞
            mDelayReport = CommonExecutor.schedule(REPORTER, 300);
        }
    }

}
