package com.albertech.filehelper.api;

import com.albertech.filehelper.core.scan.IFileScanListener;
import com.albertech.filehelper.core.usb.IUSBListener;
import com.albertech.filehelper.core.watch.IFileWatchListener;

/**
 * 文件事件订阅者接口
 */
public interface IFileWatchSubscriber
        extends IFileWatchListener,
        IFileScanListener,
        IUSBListener {

    String PATH_USB_ONLY = "USB_ONLY";

}
