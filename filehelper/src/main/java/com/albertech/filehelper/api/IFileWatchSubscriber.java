package com.albertech.filehelper.api;

/**
 * 文件事件订阅者接口
 */
public interface IFileWatchSubscriber {

    /**
     * 文件操作事件回调
     * @param event 事件编码
     * @param parentPath 事件父路径
     * @param childPath 事件发生路径
     */
    void onEvent(int event, String parentPath, String childPath);

    /**
     * 文件扫描完成回调
     * @param parentPath 扫描路径
     */
    void onScanResult(String parentPath);

}
