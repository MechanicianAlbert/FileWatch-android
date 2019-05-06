package com.albertech.filehelper.core.watch;

import android.os.FileObserver;
import android.text.TextUtils;

import com.albertech.filehelper.core.CommonExecutor;
import com.albertech.filehelper.core.IConstant;

import java.io.File;
import java.io.FileFilter;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 可递归文件观察者, 递归观察逻辑实现类
 *
 * 组织管理对观察根路径及其子路径进行观察的所有非递归观察者,
 * 收集其上报信息, 并传递给事件监听者
 */
public class FileWatcher implements IFileWatch, IFileWatchListener {

    // 日志标签
    private static final String TAG = FileWatcher.class.getSimpleName();

    /**
     * 非递归观察者集合, 键为观察路径, 值为观察者
     */
    private final Map<String, SingleDirectoryObserver> OBSERVERS = new ConcurrentHashMap<>();

    /**
     * 合法目录路径过滤器, 用于判断文件是否为一个合法目录
     */
    private final FileFilter VALID_READABLE_DIRECTORY_FILTER = new FileFilter() {

        @Override
        public boolean accept(File f) {
            return isValidReadableDirectory(f);
        }
    };

    /**
     * 创建观察者的任务
     */
    private final Runnable OBSERVER_CREATER = new Runnable() {

        @Override
        public void run() {
            // 使用栈容器递归创建观察者
            Stack<String> s = new Stack<>();
            // 在栈中首先压入观察根路径
            s.push(WATCH_PATH);
            // 栈非空时
            while (!s.isEmpty()) {
                // 获得栈顶路径
                String path = s.pop();

                // 从集合中查询是否有已经创建好的观察者
                FileObserver o = OBSERVERS.get(path);
                if (o != null) {
                    // 如果观察者可用则开启观察
                    o.startWatching();
                } else {
                    // 对此路径创建观察者
                    createSingleDirectoryObserver(path);
                }

                // 如果此路径是合法目录
                File f = new File(path);
                File[] children = f.listFiles(VALID_READABLE_DIRECTORY_FILTER);
                if (children != null) {
                    // 遍历子目录, 将所有子目录路径压入栈
                    for (File child : children) {
                        s.push(child.getAbsolutePath());
                    }
                }
            }
        }
    };

    /**
     * 恢复观察的任务
     */
    private final Runnable RESUME_WATCHING = new Runnable() {

        @Override
        public void run() {
            for (FileObserver o : OBSERVERS.values()) {
                o.startWatching();
            }
        }
    };

    /**
     * 暂停观察的任务
     */
    private final Runnable PAUSE_WATCHING = new Runnable() {

        @Override
        public void run() {
            for (FileObserver o : OBSERVERS.values()) {
                o.stopWatching();
            }
        }
    };

    /**
     * 销毁观察者的任务
     */
    private final Runnable OBSERVER_DESTROYER = new Runnable() {

        @Override
        public void run() {
            mListener = null;
            Iterator<String> i = OBSERVERS.keySet().iterator();
            while (i.hasNext()) {
                releaseInvalidPathObserver(i.next());
            }
        }
    };

    /**
     * 观察根路径
     */
    private final String WATCH_PATH;
    /**
     * 文件操作事件掩码
     */
    private final int EVENT_MASK;

    /**
     * 文件操作事件监听
     */
    private IFileWatchListener mListener;


    private FileWatcher(String path, int eventMask, IFileWatchListener listener) {
        WATCH_PATH = path;
        EVENT_MASK = eventMask & FileObserver.ALL_EVENTS;
        mListener = listener;
    }

    /**
     * 对单一路径创建观察者
     * @param path 路径
     */
    private void createSingleDirectoryObserver(String path) {
        // 创建单路径观察者, 并存入集合 (创建时自动开启观察)
        OBSERVERS.put(path, new SingleDirectoryObserver(path, EVENT_MASK, this));
    }

    /**
     * 释放对已失效的路径进行观察的观察者
     * @param path 路径
     */
    private void releaseInvalidPathObserver(String path) {
        // 移除观察者
        SingleDirectoryObserver o = OBSERVERS.remove(path);
        if (o != null) {
            // 释放观察者
            o.release();
        }
    }

    /**
     * 判断路径是否为合法的目录
     * @param path 路径
     * @return
     */
    private boolean isValidReadableDirectoryPath(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        } else {
            File f = new File(path);
            return isValidReadableDirectory(f);
        }
    }

    /**
     * 判断文件是否为合法的目录
     * @param f 文件
     * @return
     */
    private boolean isValidReadableDirectory(File f) {
        return f != null
                && f.canRead()
                && f.isDirectory()
                && !".".equals(f.getName())
                && !"..".equals(f.getName());
    }


    @Override
    public final void startWatching() {
        CommonExecutor.get().execute(OBSERVER_CREATER);
    }

    @Override
    public void resumeWatching() {
        CommonExecutor.get().execute(RESUME_WATCHING);
    }

    @Override
    public final void pauseWatching() {
        CommonExecutor.get().execute(PAUSE_WATCHING);
    }

    @Override
    public final void stopWatching() {
        CommonExecutor.get().execute(OBSERVER_DESTROYER);
    }

    @Override
    public final void onEvent(int event, String parentPath, String fullPath) {
        switch (event) {
            // 创建
            case FileObserver.CREATE:
            // 移至
            case FileObserver.MOVED_TO:
                // 创建和移动会产生原本不存在的新路径, 如果新路径是合法目录, 需要为新路径创建新的观察者
                if (isValidReadableDirectoryPath(fullPath)) {
                    createSingleDirectoryObserver(fullPath);
                }
                break;
            // 删除
            case FileObserver.DELETE:
            // 移走
            case FileObserver.MOVED_FROM:
                // 删除和移走会使路径失效, 从观察者集合中移除该路径的观察者并停止观察该路径
                releaseInvalidPathObserver(fullPath);
                break;
        }
        if (mListener != null && event > 0) {
            // 传递事件给监听者
            mListener.onEvent(event, parentPath, fullPath);
        }
    }

    /**
     * 递归观察者的建造器
     */
    public static final class Builder implements IConstant {

        /**
         * 默认事件掩码
         */
        private static final int DEFAULT_WATCH_EVENT = FileObserver.ALL_EVENTS;

        /**
         * 观察根路径默认为SD卡
         */
        private String mWatchPath = SD_CARD_PATH;
        /**
         * 事件掩码默认为所有事件
         */
        private int mEventMask = DEFAULT_WATCH_EVENT;
        /**
         * 文件操作事件监听
         */
        private IFileWatchListener mListener;

        /**
         * 设置观察根路径
         * @param path 路径
         * @return
         */
        public Builder path(String path) {
            mWatchPath = path;
            return this;
        }

        /**
         * 设置事件掩码
         * @param eventMask
         * @return
         */
        public Builder eventMask(int eventMask) {
            mEventMask = eventMask;
            return this;
        }

        /**
         * 设置事件监听
         * @param listener
         * @return
         */
        public Builder listener(IFileWatchListener listener) {
            mListener = listener;
            return this;
        }

        /**
         * 建造
         * @return 可递归文件观察者
         */
        public FileWatcher build() {
            try {
                return new FileWatcher(mWatchPath, mEventMask, mListener);
            } finally {
                mListener = null;
            }
        }

    }

}
