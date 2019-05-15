package com.albertech.filehelper.core.usb;

/**
 * U盘监听
 */
public interface IUSBListener {

    /**
     * U盘挂载
     * @param path U盘路径
     */
    void onUsbDeviceMount(String path);

    /**
     * U盘解除挂载
     * @param path U盘路径
     */
    void onUsbDeviceUnmount(String path);

}
