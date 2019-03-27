package com.albertech.filewatch.api;

import android.content.Context;
import android.os.FileObserver;

import com.albertech.filewatch.service.FileWatchServiceConnection;




public class FileWatch {


    private FileWatch() {
        throw new RuntimeException("This class cannot be instantiated");
    }


    public static IFileWatchUnsubscribe subscribeFileWatch(Context context, IFileWatchSubscriber subscriber, String path) {
        return new FileWatchServiceConnection(context, subscriber, path);
    }

    public static String name(int event) {
        String name;
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
            default:
                name = "其它";
                break;
        }
        return name;
    }
}
