package com.albertech.filehelper.core.usb;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;

/**
 * U盘管理类
 */
public class USBManager implements IUSB {

    // 日志标签
    private static final String TAG = USBManager.class.getSimpleName();


    /**
     * U盘插拔状态广播接收者
     */
    private final BroadcastReceiver mUsbAttachmentBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "Receive usb broadcast"
                    + "\nAction: " + intent.getAction()
            );
        }
    };

    /**
     * U盘挂载状态广播接收者
     */
    private final BroadcastReceiver mUsbMountBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = null;
            Uri data = null;
            String path = null;

            Log.d(TAG, "Receive usb broadcast");
            if (intent != null
                    && (action = intent.getAction()) != null
                    && (data = intent.getData()) != null
                    && (path = data.getPath()) != null
                    && (path = data.getPath()) != null
                    && isValidUsbPath(path)) {

                handleUsbEvents(action, path);
            } else if (intent == null) {
                Log.e(TAG, "intent is null");
            } else if (action == null) {
                Log.e(TAG, "action is null");
            } else if (data == null) {
                Log.e(TAG, "data is null");
            } else if (path == null) {
                Log.e(TAG, "path is null");
            } else if (!isValidUsbPath(path)) {
                Log.e(TAG, "path is invalid");
            }
        }
    };


    private Context mContext;

    /**
     * U盘监听
     */
    private IUSBListener mListener;


    public USBManager(Context context, IUSBListener listener) {
        mContext = context;
        mListener = listener;
    }


    /**
     * @return U盘插拔广播意图过滤器
     */
    private IntentFilter createUsbAttachmentBroadcastFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        return filter;
    }

    /**
     * @return U盘挂载广播意图过滤器
     */
    private IntentFilter createUsbMountBroadcastFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_MEDIA_MOUNTED);
        filter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
        filter.addAction(Intent.ACTION_MEDIA_REMOVED);
        filter.addAction(Intent.ACTION_MEDIA_BAD_REMOVAL);
        filter.addDataScheme(ContentResolver.SCHEME_FILE);
        return filter;
    }

    /**
     * 注册U盘状态广播
     */
    private void registerUsbBroadcast() {
        mContext.registerReceiver(mUsbMountBroadcastReceiver, createUsbMountBroadcastFilter());
        mContext.registerReceiver(mUsbAttachmentBroadcastReceiver, createUsbAttachmentBroadcastFilter());
    }

    /**
     * 注销U盘状态广播
     */
    private void unregisterUsbBroadcast() {
        try {
            mContext.unregisterReceiver(mUsbMountBroadcastReceiver);
            mContext.unregisterReceiver(mUsbAttachmentBroadcastReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理U盘挂载状态变换事件
     * @param action
     * @param path
     */
    private void handleUsbEvents(@NonNull String action, @NonNull String path) {
        Log.e(TAG, "Handle usb event, action: " + action + ", path: " + path);
        switch (action) {
            case Intent.ACTION_MEDIA_MOUNTED:
                Log.e(TAG, "USB device mounted, path: " + path);
                if (mListener != null) {
                    mListener.onUsbDeviceMount(path);
                }
                break;
            case Intent.ACTION_MEDIA_UNMOUNTED:
                Log.e(TAG, "USB device unmounted, path: " + path);
                if (mListener != null) {
                    mListener.onUsbDeviceUnmount(path);
                }
                break;
        }
    }

    /**
     * 判断U盘路径是否合法
     * @param path
     * @return
     */
    private boolean isValidUsbPath(String path) {
        // valid usb path judgement, temporarily always true
//        return true;
        File f;
        return path != null
                && path.trim().length() != 0
                && (f = new File(path)).exists()
                && f.canExecute();
    }

    @Override
    public void init() {
        registerUsbBroadcast();
    }

    @Override
    public void release() {
        unregisterUsbBroadcast();
    }

}
