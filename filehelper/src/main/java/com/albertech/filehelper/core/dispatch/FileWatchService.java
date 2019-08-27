package com.albertech.filehelper.core.dispatch;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 文件事件分发主服务
 */
public class FileWatchService extends Service {

    private static final String WATCH_PATHS_KEY = "WATCH_PATHS";

    private final String NOTIFICATION_ID = "CHANNEL_ID";
    private final String NOTIFICATION_NAME = "CHANNEL_NAME";

    private final String[] TYPE = new String[0];

    /**
     * 分发器
     */
    private FileWatchDispatcher mDispatcher;


    public static void start(Context context, String... watchPath) {
        ArrayList<String> paths = new ArrayList<>(Arrays.asList(watchPath));
        Intent intent = new Intent(context, FileWatchService.class);
        intent.putStringArrayListExtra(WATCH_PATHS_KEY, paths);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        startSelfAsForeground();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initDispatcher(getWatchPaths(intent));
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mDispatcher;
    }

    @Override
    public void onDestroy() {
        releaseDispatcher();
    }

    /**
     * 前台启动
     */
    private void startSelfAsForeground() {
        createNotificationChannelIfNecessary();
        startForeground(1, createNotification());
    }

    /**
     * 创建通知渠道
     */
    private void createNotificationChannelIfNecessary() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //创建 NotificationChannel
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE))
                    .createNotificationChannel(new NotificationChannel(
                            NOTIFICATION_ID,
                            NOTIFICATION_NAME,
                            NotificationManager.IMPORTANCE_HIGH
                    ));
        }
    }

    /**
     * 创建通知
     * @return 通知
     */
    private Notification createNotification() {
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //设置Notification的ChannelID,否则不能正常显示
            builder.setChannelId(NOTIFICATION_ID);
        }
        return builder.build();
    }

    /**
     * 从意图中读取监听文件路径列表
     * @param intent 意图
     * @return 监听文件路径列表
     */
    private String[] getWatchPaths(Intent intent) {
        ArrayList<String> paths = null;
        if (intent != null) {
            paths = intent.getStringArrayListExtra(WATCH_PATHS_KEY);
        }
        if (paths == null) {
            paths = new ArrayList<>();
        }
        return paths.toArray(TYPE);
    }

    /**
     * 初始化分发器
     * @param watchPaths 监听文件路径列表
     */
    private void initDispatcher(String... watchPaths) {
        mDispatcher = new FileWatchDispatcher();
        mDispatcher.init(getApplicationContext(), watchPaths);
    }

    /**
     * 释放分发器
     */
    private void releaseDispatcher() {
        if (mDispatcher != null) {
            mDispatcher.release();
            mDispatcher = null;
        }
    }

}
