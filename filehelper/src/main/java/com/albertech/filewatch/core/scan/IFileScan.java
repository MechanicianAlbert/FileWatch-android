package com.albertech.filewatch.core.scan;

/**
 * 文件扫描功能接口
 */
public interface IFileScan {

    /**
     * 初始化
     */
    void init();

    /**
     * 扫描
     * @param path 路径
     */
    void scan(String path);

    /**
     * 释放
     */
    void release();

}
