package com.albertech.filehelper.core.dispatch;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * 文件事件分发主服务
 */
public class FileWatchService extends Service {

    /**
     * 分发器
     */
    private FileWatchDispatcher mDispatcher;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mDispatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 启动时新建分发器
        mDispatcher = new FileWatchDispatcher();
        // 分发器初始化
        mDispatcher.init(getApplicationContext());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 关闭时释放分发器
        if (mDispatcher != null) {
            mDispatcher.release();
            mDispatcher = null;
        }
    }

}
