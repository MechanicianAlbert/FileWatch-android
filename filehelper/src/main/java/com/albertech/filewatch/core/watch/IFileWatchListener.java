package com.albertech.filewatch.core.watch;


/**
 * 文件操作事件信息监听接口
 */
public interface IFileWatchListener {

    /**
     * 文件操作事件发生回调
     * @param event 事件编码
     * @param parentPath 事件父路径, 即上报信息的观察者观察的路径
     * @param fullPath 事件发生路径, 即事件操作的路径
     */
    void onEvent(int event, String parentPath, String fullPath);
}
