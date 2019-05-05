package com.albertech.filewatch.core.dispatch;

import com.albertech.filewatch.api.IFileWatchSubscriber;

/**
 * 文件事件分发逻辑层接口
 */
public interface IFileWatchDispatch {

    /**
     * 订阅
     * @param subscriber 订阅者
     * @param type 订阅文件类型
     * @param path 订阅文件路径
     */
    void subscribeFileWatch(IFileWatchSubscriber subscriber, int type, String path);

    /**
     * 反订阅
     * @param subscriber
     */
    void unsubscribeFileWatch(IFileWatchSubscriber subscriber);

}
