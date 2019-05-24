package com.albertech.filehelper.core.usb;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;

import java.util.HashSet;
import java.util.Set;

/**
 * U盘状态查询类
 */
public class UsbStatus {

    /**
     * 判断U盘是否挂载
     * @param path U盘路径
     * @return U盘是否挂载
     */
    public static boolean hasMounted(Context context, String path) {
        return getMountedDevices(context).contains(path);
    }

    /**
     * 获得所有已挂载的U盘路径集合
     * @return 所有已挂载的U盘路径集合
     */
    public static Set<String> getMountedDevices(Context context) {
        Set<String> data = new HashSet<>();
        try {
            StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
            String[] paths = (String[]) StorageManager.class.getMethod("getVolumePaths", null).invoke(storageManager, null);
            if (paths != null && paths.length > 0) {
                for (String path : paths) {
                    String state = (String) StorageManager.class.getMethod("getVolumeState", String.class).invoke(storageManager, path);
                    if (state.equals(Environment.MEDIA_MOUNTED) && !path.contains("emulated")) {
                        data.add(path);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

}
