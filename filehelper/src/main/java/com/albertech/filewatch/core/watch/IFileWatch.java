package com.albertech.filewatch.core.watch;

/**
 * 可递归文件观察者接口,
 * 递归观察逻辑的封装层
 */
public interface IFileWatch {

    /**
     * 开始观察
     * 递归创建对观察路径及其所有子路径进行观察的观察者, 并开始收集观察者上报的事件
     */
    void startWatching();

    /**
     * 恢复观察
     * 恢复处理现有观察者上报的事件, 不重新创建观察者
     */
    void resumeWatching();

    /**
     * 暂停观察
     * 暂停处理所有观察者上报的事件, 不释放观察者
     */
    void pauseWatching();

    /**
     * 停止观察
     * 停止处理所有观察者上报的事件, 释放所有观察者
     */
    void stopWatching();

}
