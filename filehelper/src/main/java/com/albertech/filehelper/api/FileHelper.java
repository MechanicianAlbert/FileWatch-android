package com.albertech.filehelper.api;

import android.content.Context;
import android.content.Intent;
import android.os.FileObserver;

import com.albertech.filehelper.core.dispatch.FileWatchService;
import com.albertech.filehelper.core.query.FileQueryer;
import com.albertech.filehelper.core.query.IFileQuery;
import com.albertech.filehelper.core.dispatch.FileWatchServiceConnection;


public class FileHelper implements IFileConstant {

    private FileHelper() {
        // 不支持反射创建实例
        throw new RuntimeException("This class cannot be instantiated");
    }


    /**
     * 初始化方法, 仅启动文件监听主服务
     *
     * 需要长期在后台监视文件变化, 则调用此方法
     * 如使用继承默认主服务的自定义的主服务扩展其它功能, 则用户不需要调用此方法, 自行启动服务即可
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        if (context == null) {
            throw new NullPointerException("Context should not be null");
        }
        context.startService(new Intent(context, FileWatchService.class));
    }

    /**
     * 订阅文件操作监听
     * @param context 上下文
     * @param subscriber 订阅者
     * @param path 文件路径
     * @return 反订阅器
     */
    public static IFileWatchUnsubscribe subscribeFileWatch(Context context, IFileWatchSubscriber subscriber, String path) {
        return subscribeFileWatch(context, subscriber, IFileQuery.FILE, path);
    }

    /**
     * 订阅文件操作监听
     * @param context 上下文
     * @param subscriber 订阅者
     * @param type 文件类型
     * @param path 文件路径
     * @return 反订阅器
     */
    public static IFileWatchUnsubscribe subscribeFileWatch(Context context, IFileWatchSubscriber subscriber, int type, String path) {
        return new FileWatchServiceConnection(context, subscriber, type, path);
    }

    public static IFileQuery createDefaultFileQuery() {
        return new FileQueryer();
    }

    /**
     * 获取文件操作事件名称, 用于日志
     * @param event 文件操作事件
     * @return 文件操作名称
     */
    public static String fileOperationName(int event) {
        String name = "其他";
        event &= FileObserver.ALL_EVENTS;
        switch (event) {
            case FileObserver.ACCESS:
                name = "访问文件";
                break;
            case FileObserver.MODIFY:
                name = "修改文件";
                break;
            case FileObserver.ATTRIB:
                name = "修改文件属性";
                break;
            case FileObserver.CLOSE_WRITE:
                name = "可写文件关闭";
                break;
            case FileObserver.CLOSE_NOWRITE:
                name = "不可写文件关闭";
                break;
            case FileObserver.OPEN:
                name = "文件被打开";
                break;
            case FileObserver.MOVED_FROM:
                name = "文件被移走";
                break;
            case FileObserver.MOVED_TO:
                name = "移入新文件";
                break;
            case FileObserver.CREATE:
                name = "创建新文件";
                break;
            case FileObserver.DELETE:
                name = "删除文件";
                break;
            case FileObserver.DELETE_SELF:
                name = "自删除";
                break;
            case FileObserver.MOVE_SELF:
                name = "自移动";
                break;
        }
        return name;
    }

    /**
     * 获取文件类型名称, 用于日志
     * @param type 文件类型
     * @return 文件类型名称
     */
    public static String fileTypeName(int type) {
        String name = "未知";
        switch (type) {
            case DIRECTORY:
                name = "目录";
                break;
            case IMAGE:
                name = "图片";
                break;
            case AUDIO:
                name = "音频";
                break;
            case VIDEO:
                name = "视频";
                break;
            case DOC:
                name = "文档";
                break;
            case ZIP:
                name = "压缩";
                break;
            case APK:
                name = "安装";
                break;
            case FILE:
                name = "文件";
                break;
        }
        return name;
    }

}
