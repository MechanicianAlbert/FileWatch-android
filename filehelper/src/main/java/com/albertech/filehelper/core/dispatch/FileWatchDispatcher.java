package com.albertech.filehelper.core.dispatch;

import android.content.Context;
import android.os.Binder;
import android.os.FileObserver;
import android.text.TextUtils;

import com.albertech.filehelper.log.LogUtil;
import com.albertech.filehelper.api.FileHelper;
import com.albertech.filehelper.api.IFileWatchSubscriber;
import com.albertech.filehelper.core.IConstant;
import com.albertech.filehelper.log.ITAG;
import com.albertech.filehelper.core.delete.FileDelete;
import com.albertech.filehelper.core.mkdir.MkdirUtil;
import com.albertech.filehelper.core.scan.FileScanScheduler;
import com.albertech.filehelper.core.scan.IFileScan;
import com.albertech.filehelper.core.scan.IFileScanListener;
import com.albertech.filehelper.core.scan.commit.IScanCommit;
import com.albertech.filehelper.core.usb.IUSBListener;
import com.albertech.filehelper.core.usb.USBManager;
import com.albertech.filehelper.core.usb.UsbStatus;
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
        IUSBListener,
        IScanCommit,
        IConstant {

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
     * USB订阅者集合, 包含所有仅订阅USB事件的订阅者, 即仅会被回调IUSBListener的方法
     */
    private final Map<IFileWatchSubscriber, Integer> SUBSCRIBED_USB = new ConcurrentHashMap<>();

    /**
     * 默认事件掩码
     */
    private final int WATCH_EVENT_MASK =
            FileObserver.CREATE
                    | FileObserver.DELETE
                    | FileObserver.MOVED_TO
                    | FileObserver.MOVED_FROM;


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
     * @param context 上下文
     */
    void init(Context context, String... watchPath) {
        if (context == null) {
            throw new NullPointerException("Context cannot be null");
        }
        mContext = context;

        // 确保要观察的文件路径被创建好
        MkdirUtil.mds(watchPath);

        // 创建递归观察者
        mFileWatcher = new FileWatcher.Builder()
                .path(watchPath)
                .eventMask(WATCH_EVENT_MASK)
                .listener(this)
                .build();
        // 开启观察
        mFileWatcher.startWatching();

        // 创建扫描器
        mScanner = new FileScanScheduler(mContext, this);
        // 初始化扫描器
        mScanner.init();

        // 创建USB管理器
        mManager = new USBManager(mContext, this);
        // 初始化USB管理器
        mManager.init(mScanner);

        // 初始化时, 现对需要监听的路径作扫描, 同步文件与媒体库,
        // 因为监听只能同步开启之后的变化, 开启之前的差异不能通过监听变化同步
        commitScanMission(watchPath);
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
        // 无订阅路径, 默认订阅USB事件
        path = !TextUtils.isEmpty(path) ? path : IFileWatchSubscriber.PATH_USB_ONLY;
        if (TextUtils.equals(path, IFileWatchSubscriber.PATH_USB_ONLY)) {
            // 仅向USB集合添加订阅者
            SUBSCRIBED_USB.put(subscriber, 0);
        } else {
            // 记录订阅路径
            SUBSCRIBED_PATH.put(subscriber, path);
            // 记录订阅类型
            SUBSCRIBED_TYPE.put(subscriber, type);
        }
    }

    @Override
    public void unsubscribeFileWatch(IFileWatchSubscriber subscriber) {
        // 从路径集合移除订阅者
        SUBSCRIBED_PATH.remove(subscriber);
        // 从类型集合移除订阅者
        SUBSCRIBED_TYPE.remove(subscriber);
        // 从USB集合移除订阅者
        SUBSCRIBED_USB.remove(subscriber);
    }

    @Override
    public void onEvent(int originalEvent, String parentPath, String childPath) {
        // 若事件为默认监听事件(新建/删除/移动), 则文件目录树有变化, 需要通知系统重新扫描, 更新文件数据库
        int event = originalEvent & WATCH_EVENT_MASK;
        if (event > 0
                && !TextUtils.isEmpty(childPath)) {
            LogUtil.d(ITAG.OBSERVE,
                    "File event: " + FileHelper.fileOperationName(event),
                    "ParentPath: " + parentPath,
                    "ChildPath: " + childPath
            );
            switch (event) {
                case FileObserver.CREATE:
                case FileObserver.MOVED_TO:
                    if (mScanner != null) {
                        mScanner.scan(childPath);
                    }
                    break;
                case FileObserver.DELETE:
                case FileObserver.MOVED_FROM:
                    FileDelete.delete(mContext, childPath);
                    break;
            }
            // 分发文件操作事件
            dispatchFileEvent(event, parentPath, childPath);
        }
    }

    @Override
    public void onScanResult(String path) {
        LogUtil.d(ITAG.SCAN, "onScanResult",
                "path: " + path);
        if (path.startsWith(SD_CARD_PATH)) {
            // SD卡或其子路径
            dispatchScanResult(path);
        } else {
            // U盘路径
            onUsbDeviceScanned(path);
        }
    }

    @Override
    public void onUsbDeviceAttach() {
        dispatchUsbDeviceAttach();
    }

    @Override
    public void onUsbDeviceMount(String path) {
        if (mScanner != null) {
            // 扫描U盘路径, 更新数据库
            mScanner.scan(path);
        }
        dispatchUsbDeviceMount(path);
    }

    @Override
    public void onUsbDeviceEject(String path) {
        dispatchUsbDeviceEject(path);
    }

    @Override
    public void onUsbDeviceUnmount(String path) {
        // U盘解除挂载后, 移除已扫描U盘集合中的对应记录
        UsbStatus.removeDevicesFromScanned(path);
        if (mScanner != null) {
            // 扫描U盘路径, 更新数据库
            mScanner.scan(path);
        }
        dispatchUsbDeviceUnmount(path);
    }

    @Override
    public void onUsbDeviceScanned(String path) {
        LogUtil.d(ITAG.USB, "USB device scanned, path: " + path);
        // 扫描完成后, 向已扫描U盘集合中添加对应记录
        UsbStatus.addDeviceToScanned(mContext, path);
        dispatchUsbDeviceScanned(path);
    }

    /**
     * 分发文件操作事件
     *
     * @param event      文件操作事件
     * @param parentPath 父路径
     * @param childPath  事件路径
     */
    private void dispatchFileEvent(int event, String parentPath, String childPath) {
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

    /**
     * 分发USB设备插入事件
     */
    private void dispatchUsbDeviceAttach() {
        // 通知上层U盘插入事件
        for (IFileWatchSubscriber subscriber : SUBSCRIBED_USB.keySet()) {
            if (subscriber != null) {
                subscriber.onUsbDeviceAttach();
            }
        }
        // 普通订阅者也需要被通知U盘事件
        for (IFileWatchSubscriber subscriber : SUBSCRIBED_PATH.keySet()) {
            if (subscriber != null) {
                subscriber.onUsbDeviceAttach();
            }
        }
    }

    /**
     * 分发USB设备挂载事件
     *
     * @param path 路径
     */
    private void dispatchUsbDeviceMount(String path) {
        // 通知上层U盘挂载事件
        for (IFileWatchSubscriber subscriber : SUBSCRIBED_USB.keySet()) {
            if (subscriber != null) {
                subscriber.onUsbDeviceMount(path);
            }
        }
        // 普通订阅者也需要被通知U盘事件
        for (IFileWatchSubscriber subscriber : SUBSCRIBED_PATH.keySet()) {
            if (subscriber != null) {
                subscriber.onUsbDeviceMount(path);
            }
        }
    }

    /**
     * 分发USB设备扫描事件
     *
     * @param path 路径
     */
    private void dispatchUsbDeviceScanned(String path) {
        // 通知上层U盘扫描事件
        for (IFileWatchSubscriber subscriber : SUBSCRIBED_USB.keySet()) {
            if (subscriber != null) {
                subscriber.onUsbDeviceScanned(path);
            }
        }
        // 普通订阅者也需要被通知U盘事件
        for (IFileWatchSubscriber subscriber : SUBSCRIBED_PATH.keySet()) {
            if (subscriber != null) {
                subscriber.onUsbDeviceScanned(path);
            }
        }
    }

    /**
     * 分发USB设备移除事件
     *
     * @param path 路径
     */
    private void dispatchUsbDeviceEject(String path) {
        // 通知上层U盘移除事件
        for (IFileWatchSubscriber subscriber : SUBSCRIBED_USB.keySet()) {
            if (subscriber != null) {
                subscriber.onUsbDeviceEject(path);
            }
        }
        // 普通订阅者也需要被通知U盘事件
        for (IFileWatchSubscriber subscriber : SUBSCRIBED_PATH.keySet()) {
            if (subscriber != null) {
                subscriber.onUsbDeviceEject(path);
            }
        }
    }

    /**
     * 分发USB设备解除挂载事件
     *
     * @param path 路径
     */
    private void dispatchUsbDeviceUnmount(String path) {
        // 通知上层U盘解除挂载事件
        for (IFileWatchSubscriber subscriber : SUBSCRIBED_USB.keySet()) {
            if (subscriber != null) {
                subscriber.onUsbDeviceUnmount(path);
            }
        }
        // 普通订阅者也需要被通知U盘事件
        for (IFileWatchSubscriber subscriber : SUBSCRIBED_PATH.keySet()) {
            if (subscriber != null) {
                subscriber.onUsbDeviceUnmount(path);
            }
        }
    }


    @Override
    public void commitScanMission(String... paths) {
        if (mScanner != null
                && paths != null
                && paths.length > 0) {
            for (String path : paths) {
                if (!TextUtils.isEmpty(path)) {
                    mScanner.scan(path);
                }
            }
        }
    }

}
