package com.albertech.filehelper.core.mkdir;

import com.albertech.filehelper.log.LogUtil;
import com.albertech.filehelper.log.ITAG;

import java.io.File;

/**
 * 文件夹创建工具, 要使文件监听生效, 需要保障在设置监听之前, 目标路径存在
 */
public class MkdirUtil {

    /**
     * 一次性为多个目标路径分别创建目录
     * 使用异常捕获, 确保当出现某路径的目录创建失败时, 不影响其他目录的创建
     * 通常这类异常的产生, 是由于用户未授权一些文件操作权限导致的
     *
     * @param paths 目标路径数组
     */
    public static void mds(String... paths) {
        if (paths != null && paths.length > 0) {
            for (String path : paths) {
                try {
                    md(path);
                } catch (Exception e) {
                    LogUtil.e(ITAG.OBSERVE, "Exception in mkdir, e: " + e.toString());
                }
            }
        }
    }

    /**
     * 创建单个路径的文件夹目录
     *
     * @param path 路径
     */
    static void md(String path) {
        File f = new File(path);
        if (!f.exists()) {
            if (f.mkdirs()) {
                LogUtil.d(ITAG.OBSERVE, "Success mkdir", "path: " + path);
            } else {
                LogUtil.d(ITAG.OBSERVE, "Fail mkdir", "path: " + path);
            }
        } else {
            LogUtil.d(ITAG.OBSERVE, "Directory is existed", "path: " + path);
        }
    }

}
