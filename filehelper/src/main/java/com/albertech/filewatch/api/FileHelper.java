package com.albertech.filewatch.api;

import android.content.Context;
import android.os.FileObserver;

import com.albertech.filewatch.core.query.FileQueryer;
import com.albertech.filewatch.core.query.IFileQuery;
import com.albertech.filewatch.core.dispatch.FileWatchServiceConnection;


public class FileHelper implements IFileConstant {

    private FileHelper() {
        throw new RuntimeException("This class cannot be instantiated");
    }


    public static IFileWatchUnsubscribe subscribeFileWatch(Context context, IFileWatchSubscriber subscriber, String path) {
        return subscribeFileWatch(context, subscriber, IFileQuery.FILE, path);
    }

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
