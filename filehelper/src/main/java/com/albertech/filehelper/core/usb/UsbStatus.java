package com.albertech.filehelper.core.usb;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * U盘状态记录类
 */
public class UsbStatus {

    /**
     * 已挂载U盘路径集合
     */
    private static final Map<String, Boolean> USB_MOUNTED = new ConcurrentHashMap<>();
    /**
     * 已扫描完成U盘路径集合
     */
    private static final Map<String, Boolean> USB_SCANNED = new ConcurrentHashMap<>();

    /**
     * 判断U盘是否挂载
     * @param path U盘路径
     * @return U盘是否挂载
     */
    public static boolean hasMounted(String path) {
        return USB_MOUNTED.containsKey(path);
    }

    /**
     * 判断U盘是否扫描完成
     * @param path U盘路径
     * @return U盘是否扫描完成
     */
    public static boolean hasScanned(String path) {
        return USB_SCANNED.containsKey(path);
    }

    /**
     * 获得所有已挂载的U盘路径集合
     * @return 所有已挂载的U盘路径集合
     */
    public static Set<String> getMountedUsbDevices() {
        return USB_MOUNTED.keySet();
    }

    /**
     * 获得所有已扫描的U盘路径集合
     * @return 所有已扫描的U盘路径集合
     */
    public static Set<String> getScannedUsbDevices() {
        return USB_SCANNED.keySet();
    }


    public static void mounted(String path) {
        USB_MOUNTED.put(path, true);
    }

    public static void unmounted(String path) {
        USB_MOUNTED.remove(path);
        USB_SCANNED.remove(path);
    }

    public static void scanned(String path) {
        USB_SCANNED.put(path, true);
    }

}
