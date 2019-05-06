package com.albertech.filehelper.api;

/**
 * 反订阅器接口, 用于释放订阅, 防止内存泄漏
 */
public interface IFileWatchUnsubscribe {

    /**
     * 反订阅
     */
    void unsubscribe();

}
