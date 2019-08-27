package com.albertech.filehelper.core.scan.task;

import android.content.Context;

/**
 * 扫描任务组接口
 * 主动通知系统开始对指定路径的扫描, 并将扫描完成的结果上报
 */
public interface IFileScanTaskGroup {

    /**
     * 通知系统服务对路径进行扫描, 递归扫描路径树
     * MediaScannerConnection的扫描不支持自动递归目录, 需要在方法实现中自行组织递归目录逻辑
     *
     * @param rootPath 目录树根路径
     */
    void scanPathTree(Context context, String rootPath, IFileScanTaskGroupListener listener);

}
