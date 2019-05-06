package com.albertech.filehelper.core.watch;

import android.os.FileObserver;
import android.util.Log;

import java.io.File;

/**
 * 单路径文件观察者, 不支持递归观察子目录
 */
public class SingleDirectoryObserver extends FileObserver {

    private static final String TAG = SingleDirectoryObserver.class.getSimpleName();

    /**
     * 观察路径
     */
    private final String SELF_PATH;
    /**
     * 文件操作监听者
     * 当观察到文件操作事件时, 向其上报操作信息
     */
    private IFileWatchListener mListener;

    /**
     *
     * @param path 观察路径
     * @param mask 观察事件掩码 (观察事件为独热编码)
     * @param listener 文件操作监听者
     */
    SingleDirectoryObserver(String path, int mask, IFileWatchListener listener) {
        super(path, mask);
        SELF_PATH = path;
        mListener = listener;
        // 被创建后直接开启观察
        startWatching();
        Log.d(TAG, "Start watch path: " + SELF_PATH);
    }

    /**
     * 释放资源
     */
    void release() {
        // 释放监听
        mListener = null;
        // 停止观察路径
        stopWatching();
        Log.d(TAG, "Stop watch path: " + SELF_PATH);
    }

    /**
     * 观察到文件操作事件的回调
     * @param event 存储文件操作事件编码的整数
     * @param path 文件操作事件发生的相对路径, 基于观察路径
     */
    @Override
    public void onEvent(int event, String path) {
        // 同全事件掩码做 与 操作, 获得具体事件编码
        event &= ALL_EVENTS;
        // 获取发生路径的绝对路径, 如发生路径不存在, 则使用观察路径
        String realPath = path != null ? (SELF_PATH + File.separator + path) : SELF_PATH;
        if (mListener != null) {
            // 上报操作事件信息
            mListener.onEvent(event, SELF_PATH, realPath);
        }
    }

}
