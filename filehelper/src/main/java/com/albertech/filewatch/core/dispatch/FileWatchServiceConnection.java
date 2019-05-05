package com.albertech.filewatch.core.dispatch;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.albertech.filewatch.api.IFileWatchSubscriber;
import com.albertech.filewatch.api.IFileWatchUnsubscribe;

/**
 * 主服务的连接类
 * 用于中介将订阅者注入给主服务, 封装反订阅及释放逻辑, 防止主服务持有用户自定义订阅者实现导致的内存泄漏
 */
public class FileWatchServiceConnection implements ServiceConnection, IFileWatchUnsubscribe {

    private Context mContext;
    private int mType;
    private String mPath;
    private IFileWatchSubscriber mSubscriber;
    private IFileWatchDispatch mBinder;
    private volatile boolean mHadUnboundService;


    public FileWatchServiceConnection(Context context, IFileWatchSubscriber subscriber, int type, String path) {
        mContext = context;
        mType = type;
        mSubscriber = subscriber;
        mPath = path;
        mContext.bindService(createFileWatchServiceIntent(), this, Context.BIND_AUTO_CREATE);
    }


    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        if (service instanceof IFileWatchDispatch) {
            // 主服务返回的Binder实例即为主服务持有的分发器, 全局唯一, 强转该接口即可向其进行订阅/反订阅
            mBinder = (IFileWatchDispatch) service;
            // 服务连接后自动按照配置的文件类型和路径进行订阅
            mBinder.subscribeFileWatch(mSubscriber, mType, mPath);
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        // 服务断开连接时释放
        release();
    }

    @Override
    public synchronized void unsubscribe() {
        // 反订阅时释放
        release();
        try {
            if (!mHadUnboundService) {
                mHadUnboundService = true;
                // 反订阅时断开与服务的绑定, 防止重复解绑, 加异常检查
                mContext.unbindService(this);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return 绑定主服务的意图实例
     */
    private Intent createFileWatchServiceIntent() {
        return new Intent(mContext, FileWatchService.class);
    }

    /**
     * 释放
     */
    private void release() {
        // 释放由服务端获取的Binder
        if (mBinder != null) {
            // 反订阅, 使服务Binder与用户提供的订阅者实现, 相互释放引用
            mBinder.unsubscribeFileWatch(mSubscriber);
            mBinder = null;
        }
        // 释放订阅者引用
        mSubscriber = null;
    }

}
