package com.albertech.filehelper.core.usb;

/**
 * U盘监听
 */
public interface IUSBListener {

    /**
     * U盘插入
     */
    void onUsbDeviceAttach();

    /**
     * U盘挂载
     * @param path U盘路径
     */
    void onUsbDeviceMount(String path);

    /**
     * U盘移除
     * @param path U盘路径
     */
    void onUsbDeviceEject(String path);

    /**
     * U盘解除挂载
     * @param path U盘路径
     */
    void onUsbDeviceUnmount(String path);

    /**
     * U盘扫描完成
     * @param path U盘路径
     */
    void onUsbDeviceScanned(String path);

}
