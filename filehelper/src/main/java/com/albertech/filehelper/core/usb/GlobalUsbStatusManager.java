package com.albertech.filehelper.core.usb;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * U盘状态记录类
 */
public class GlobalUsbStatusManager {

    private static boolean sIsUsbScannedFinished;

    /**
     * 已挂载路径集合
     */
    private static final Map<String, Boolean> USB_MOUNT_STATUS_MAP = new ConcurrentHashMap<>();
    /**
     * 已扫描完成路径集合
     */
//    private static final Map<String, Boolean> USB_SCAN_STATUS_MAP = new ConcurrentHashMap<>();

    private static boolean sIsScanning;


    public static boolean hasMounted(String path) {
        if (path != null) {
            Boolean b = USB_MOUNT_STATUS_MAP.get(path);
            if (b != null) {
                return b;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean hasUsbScannedFinished(String path) {
        return !sIsScanning;

        // because of the unsupported of scan given path from system, codes below does not work
//        if (path != null) {
//            Boolean b = USB_SCAN_STATUS_MAP.get(path);
//            if (b != null) {
//                return b;
//            } else {
//                return true;
//            }
//        } else {
//            return true;
//        }
    }

    public static void updateMounted() {
        for(String path : USB_MOUNT_STATUS_MAP.keySet()) {
            USB_MOUNT_STATUS_MAP.put(path, checkValidUsbPath(path));
        }
    }

    private static boolean checkValidUsbPath(String path) {
        File f;
        return path != null
                && path.trim().length() != 0
                && (f = new File(path)).exists()
                && f.canExecute();
    }


    public static void mounted(String path) {
        USB_MOUNT_STATUS_MAP.put(path, true);
    }

    public static void unmounted(String path) {
        USB_MOUNT_STATUS_MAP.put(path, false);
    }

    public static void scanStarted(String path) {
//        USB_SCAN_STATUS_MAP.put(path, false);
        sIsScanning = true;
    }

    public static void scanFinished(String path) {
//        USB_SCAN_STATUS_MAP.put(path, true);
        sIsScanning = false;
    }

}
