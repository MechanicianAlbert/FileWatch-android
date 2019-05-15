package com.albertech.filehelper.core.dispatch;

import android.content.Context;
import android.os.Binder;
import android.os.FileObserver;
import android.text.TextUtils;

import com.albertech.filehelper.api.IFileWatchSubscriber;
import com.albertech.filehelper.core.IConstant;
import com.albertech.filehelper.core.scan.FileScanner;
import com.albertech.filehelper.core.scan.IFileScan;
import com.albertech.filehelper.core.scan.IFileScanListener;
import com.albertech.filehelper.core.usb.IUSBListener;
import com.albertech.filehelper.core.usb.USBManager;
import com.albertech.filehelper.core.watch.FileWatcher;
import com.albertech.filehelper.core.watch.IFileWatch;
import com.albertech.filehelper.core.watch.IFileWatchListener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 文件事件分发逻辑实现类
 * 运行在主服务中, 主服务持有其实例引用, 全局唯一
 * 汇总文件递归观察者, 文件扫描器的上报事件, 并按订阅主题分发至订阅者
 */
public class FileWatchDispatcher extends Binder implements IFileWatchDispatch,
        IFileWatchListener,
        IFileScanListener,
        IConstant, IUSBListener {

    // 日志标签
    private static final String TAG = FileWatchDispatcher.class.getSimpleName();

    /**
     * 订阅路径集合, 键为订阅者, 值为文件路径
     * 可以有多个订阅者对相同路径订阅
     */
    private final Map<IFileWatchSubscriber, String> SUBSCRIBED_PATH = new ConcurrentHashMap<>();
    /**
     * 订阅类型集合, 键为订阅者, 值为文件类型
     * 可以有多个订阅者对相同类型订阅
     */
    private final Map<IFileWatchSubscriber, Integer> SUBSCRIBED_TYPE = new ConcurrentHashMap<>();

    /**
     * 默认事件掩码
     */
    private final int WATCH_EVENT_MASK = FileObserver.CREATE | FileObserver.DELETE;


    private Context mContext;
    /**
     * 递归观察者
     */
    private IFileWatch mFileWatcher;
    /**
     * 文件扫描器
     */
    private IFileScan mScanner;

    /**
     * USB设备管理器
     */
    private USBManager mManager;

    /**
     * 初始化, 包内可见, 主服务调用
     *
     * @param context
     */
    void init(Context context) {
        if (context == null) {
            throw new NullPointerException("Context cannot be null");
        }
        mContext = context;
        // 创建递归观察者
        mFileWatcher = new FileWatcher.Builder()
                .path(SD_CARD_PATH)
                .eventMask(WATCH_EVENT_MASK)
                .listener(this)
                .build();
        // 开启观察
        mFileWatcher.startWatching();
        // 创建扫描器
        mScanner = new FileScanner(mContext, this);
        // 初始化扫描器
        mScanner.init();

        // 创建USB管理器
        mManager = new USBManager(mContext, this);
        // 初始化USB管理器
        mManager.init();
    }

    /**
     * 释放
     */
    void release() {
        SUBSCRIBED_PATH.clear();
        SUBSCRIBED_TYPE.clear();

        // 释放递归观察者
        if (mFileWatcher != null) {
            mFileWatcher.stopWatching();
            mFileWatcher = null;
        }
        // 释放扫描器
        if (mScanner != null) {
            mScanner.release();
            mScanner = null;
        }

        // 释放USB管理器
        if (mManager != null) {
            mManager.release();
            mManager = null;
        }
    }

    @Override
    public void subscribeFileWatch(IFileWatchSubscriber subscriber, int type, String path) {
        // 无订阅路径, 默认订阅SD卡路径
        path = !TextUtils.isEmpty(path) ? path : SD_CARD_PATH;
        // 记录订阅路径
        SUBSCRIBED_PATH.put(subscriber, path);
        // 记录订阅类型
        SUBSCRIBED_TYPE.put(subscriber, type);
    }

    @Override
    public void unsubscribeFileWatch(IFileWatchSubscriber subscriber) {
        // 从路径集合移除订阅者
        SUBSCRIBED_PATH.remove(subscriber);
        // 从类型集合移除订阅者
        SUBSCRIBED_TYPE.remove(subscriber);
    }

    @Override
    public void onEvent(int event, String parentPath, String childPath) {
        // 分发文件操作事件
        dispatchFileEvents(event, parentPath, childPath);
        // 若事件为默认监听事件(新建文件或删除文件), 则文件有增删, 需要通知系统重新扫描, 更新文件数据库
        if ((event & WATCH_EVENT_MASK) > 0
                && mScanner != null
                && !TextUtils.isEmpty(childPath)) {
            // TODO: 2019/5/15 文件系统扫描, 路径需要指定MimeType, 否则无法更新内容, 需要调整方案
            // API-19以后, 文件系统扫描仅支持文件, 不支持目录扫描, 需要传输子路径, 对具体文件扫描
//            mScanner.scan(childPath);
            mScanner.scan(parentPath);
        }
    }

    @Override
    public void onScanResult(String path) {
        if (path.startsWith(SD_CARD_PATH)) {
            // SD卡或其路径
            dispatchScanResult(path);
        } else {
            // U盘路径
            // TODO: 2019/5/15 通知上层U盘扫描完成
        }
    }

    @Override
    public void onUsbDeviceMount(String path) {
        if (mScanner != null) {
            mScanner.scan(path);
        }
        // TODO: 2019/5/15 通知上层U盘挂载事件
    }

    @Override
    public void onUsbDeviceUnmount(String path) {
        // TODO: 2019/5/15 通知上层U盘接触挂载事件
    }

    /**
     * 分发文件操作事件
     *
     * @param event      文件操作事件
     * @param parentPath 父路径
     * @param childPath  事件路径
     */
    private void dispatchFileEvents(int event, String parentPath, String childPath) {
        // 遍历订阅者
        for (IFileWatchSubscriber subscriber : SUBSCRIBED_PATH.keySet()) {
            if (subscriber != null) {
                // 获取订阅者订阅的路径
                String subscribePath = SUBSCRIBED_PATH.get(subscriber);
                // 如事件父路径以订阅路径开头, 则事件父路径为订阅路径的子路径, 应当通知此订阅者
                if (subscribePath != null && parentPath.startsWith(subscribePath)) {
                    subscriber.onEvent(event, parentPath, childPath);
                }
            }
        }
    }

    /**
     * 分发扫描结果
     *
     * @param path 路径
     */
    private void dispatchScanResult(String path) {
        // 遍历订阅者
        for (IFileWatchSubscriber subscriber : SUBSCRIBED_PATH.keySet()) {
            if (subscriber != null) {
                // 获取订阅者订阅的路径
                String subscribePath = SUBSCRIBED_PATH.get(subscriber);
                // 如扫描路径以订阅路径开头, 则扫描路径为订阅路径的子路径, 应当通知此订阅者
                if (subscribePath != null && path.startsWith(subscribePath)) {
                    subscriber.onScanResult(path);
                }
            }
        }
    }

}
