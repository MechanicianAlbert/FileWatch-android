package com.albertech.filehelper.core.usb;

import com.albertech.filehelper.core.scan.IFileScan;

/**
 * U盘管理接口
 */
public interface IUSB {

    /**
     * 初始化
     */
    void init(IFileScan scanner);

    /**
     * 释放
     */
    void release();

}
