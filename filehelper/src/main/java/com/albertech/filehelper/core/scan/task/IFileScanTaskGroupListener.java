package com.albertech.filehelper.core.scan.task;

/**
 * 文件批量扫描监听
 */
public interface IFileScanTaskGroupListener {

    /**
     * 批量扫描完成结果回调
     * @param path 批量任务根路径
     * @param completed 批量任务全部完成
     */
    void onScanBatchResult(String path, boolean completed);

}
