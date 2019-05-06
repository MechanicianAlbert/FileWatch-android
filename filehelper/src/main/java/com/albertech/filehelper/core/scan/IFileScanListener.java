package com.albertech.filehelper.core.scan;

/**
 * 文件扫描监听
 */
public interface IFileScanListener {

    /**
     * 扫描完成结果回调
     * @param path 扫描路径
     */
    void onScanResult(String path);

}
