package com.albertech.filehelper.core.usb;

import android.content.Context;
import android.os.storage.StorageManager;
import android.text.TextUtils;

import com.albertech.filehelper.log.LogUtil;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * U盘状态查询类
 */
public class UsbStatus {

    /**
     * 记录已完成媒体库扫描的U盘的集合
     */
    private static final Set<String> SCANNED_USB_DEVICES = new CopyOnWriteArraySet<>();

    /**
     * 添加扫描完成的U盘
     * @param path U盘路径
     */
    public static void addDeviceToScanned(Context context, String path) {
        // U盘在移除后, 为了清理媒体库的无效数据, 也会触发U盘目录扫描
        // 所以添加U盘的扫描完成状态前, 需要判断设备是否已挂载, 保障添加正确的状态
        if (hasMounted(context, path)) {
            SCANNED_USB_DEVICES.add(path);
        }
    }

    /**
     * 移除扫描完成的U盘
     * @param path U盘路径
     */
    public static void removeDevicesFromScanned(String path) {
        SCANNED_USB_DEVICES.remove(path);
    }

    /**
     * 判断U盘是否挂载
     * @param path U盘路径
     * @return U盘是否挂载
     */
    public static boolean hasMounted(Context context, String path) {
        boolean hasMounted = false;
        Set<UsbDeviceInfo> devices = getMountedDevicesInfo(context);
        if (devices != null && devices.size() > 0) {
            for (UsbDeviceInfo info : devices) {
                if (TextUtils.equals(path, info.PATH)) {
                    hasMounted = true;
                }
            }
        }
        return hasMounted;
    }

    /**
     * 判断U盘是否被媒体库扫描完成
     * @param path U盘路径
     * @return U盘是否被媒体库扫描完成
     */
    public static boolean hasBeenScanned(Context context, String path) {
        return hasMounted(context, path) && SCANNED_USB_DEVICES.contains(path);
    }

    /**
     * 根据路径获得U盘盘符名称
     * @param path U盘路径
     * @return U盘盘符名称
     */
    public static String getUsbDeviceNameByPath(Context context, String path) {
        Set<UsbDeviceInfo> devices = getMountedDevicesInfo(context);
        for (UsbDeviceInfo i : devices) {
            if (TextUtils.equals(path, i.PATH)) {
                return i.toString();
            }
        }
        return "";
    }

    /**
     * 获得所有已挂载的U盘路径集合
     * @return 所有已挂载的U盘路径集合
     */
    public static Set<UsbDeviceInfo> getMountedDevicesInfo(Context context) {
        Set<UsbDeviceInfo> data = new HashSet<>();
        try {
            StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);

            Class<?> c = Class.forName("android.os.storage.VolumeInfo");

            List<?> volumes = (List<?>) StorageManager.class
                    .getMethod("getVolumes")
                    .invoke(storageManager);
            for (Object v : volumes) {
                if (v != null
                        && (boolean) c.getMethod("isMountedReadable").invoke(v)
                        && (int) c.getMethod("getType").invoke(v) == 0) {
                    String name = (String) StorageManager.class
                            .getMethod("getBestVolumeDescription", c)
                            .invoke(storageManager, v);
                    String path = ((File) c.getMethod("getPath").invoke(v))
                            .getAbsolutePath();
                    data.add(new UsbDeviceInfo(name, path));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

}
