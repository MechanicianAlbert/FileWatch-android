package com.albertech.filehelper.core.usb;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.net.Uri;
import android.text.TextUtils;

import com.albertech.filehelper.log.LogUtil;
import com.albertech.filehelper.core.scan.IFileScan;
import com.albertech.filehelper.log.ITAG;

import java.util.Set;

/**
 * U盘管理类
 */
public class USBManager implements IUSB {

    // 日志标签
    private static final String TAG = USBManager.class.getSimpleName();


    /**
     * U盘插拔状态广播接收者
     */
    private final BroadcastReceiver USB_ATTACHMENT = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = "";
            UsbDevice device = null;
            if (intent != null) {
                action = intent.getAction();
                device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
            }
            action = action != null ? action : "";
            LogUtil.d(ITAG.USB,
                    "Receive usb event broadcast",
                    "Action: " + action,
                    "UsbDevice: " + (device != null ? device.toString() : "")
            );
            handleUsbAttach(action, device);
        }
    };

    /**
     * U盘挂载移除解挂状态广播接收者
     */
    private final BroadcastReceiver USB_EVENT_RECEIVER = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = "";
            Uri data = null;
            String path = "";
            boolean isValidEvent = intent != null
                    && (action = intent.getAction()) != null
                    && (data = intent.getData()) != null
                    && (path = data.getPath()) != null
                    && (path = data.getPath()) != null
                    && !TextUtils.isEmpty(path);
            action = action != null ? action : "";
            LogUtil.d(ITAG.USB,
                    "Receive usb event broadcast",
                    "Action: " + action,
                    "Data: " + (data != null ? data.toString() : ""),
                    "Path: " + path
            );
            if (isValidEvent) {
                handleUsbEvent(action, path);
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
        filter.addAction(Intent.ACTION_MEDIA_EJECT);
        filter.addAction(Intent.ACTION_MEDIA_REMOVED);
        filter.addAction(Intent.ACTION_MEDIA_BAD_REMOVAL);
        filter.addDataScheme(ContentResolver.SCHEME_FILE);
        return filter;
    }

    /**
     * 注册U盘状态广播
     */
    private void registerUsbBroadcast() {
        mContext.registerReceiver(USB_EVENT_RECEIVER, createUsbMountBroadcastFilter());
        mContext.registerReceiver(USB_ATTACHMENT, createUsbAttachmentBroadcastFilter());
    }

    /**
     * 注销U盘状态广播
     */
    private void unregisterUsbBroadcast() {
        mContext.unregisterReceiver(USB_EVENT_RECEIVER);
        mContext.unregisterReceiver(USB_ATTACHMENT);
    }

    /**
     * 通知系统对已挂载U盘进行媒体库扫描
     */
    private void scanMountedUsbPath(IFileScan scanner) {
        if (scanner != null) {
            Set<UsbDeviceInfo> devices = UsbStatus.getMountedDevicesInfo(mContext);
            if (devices != null && devices.size() > 0) {
                for (UsbDeviceInfo device : devices) {
                    scanner.scan(device.PATH);
                }
            }
        }
    }


    private void handleUsbAttach(String action, UsbDevice device) {
        if (!TextUtils.equals(action, UsbManager.ACTION_USB_DEVICE_ATTACHED)) {
            return;
        }
        if (device == null) {
            return;
        }
        int usbType = -1;
        for (int i = 0; i < device.getInterfaceCount(); i++) {
            usbType = device.getInterface(i).getInterfaceClass();
            if (usbType == UsbConstants.USB_CLASS_VENDOR_SPEC) {
                break;
            }
        }
        boolean shouldNotify = (usbType == UsbConstants.USB_CLASS_MASS_STORAGE
                || (usbType == UsbConstants.USB_CLASS_VENDOR_SPEC && device.getSerialNumber() != null));
        LogUtil.d(ITAG.USB,
                "Handle usb event, action: " + action,
                "Device: " + device,
                "Type: " + usbType,
                "Should notify: " + shouldNotify
        );
        if (shouldNotify && mListener != null) {
            LogUtil.d(ITAG.USB, "USB device attached");
            mListener.onUsbDeviceAttach();
        }
    }
    /**
     * 处理U盘状态变换事件
     *
     * @param action
     * @param path
     */
    private void handleUsbEvent(String action, String path) {
        LogUtil.d(ITAG.USB,
                "Handle usb event",
                "action: " + action,
                "path: " + path
        );
        switch (action) {
            case Intent.ACTION_MEDIA_MOUNTED:
                LogUtil.d(ITAG.USB,
                        "USB device mounted",
                        "path: " + path
                );
                if (mListener != null) {
                    mListener.onUsbDeviceMount(path);
                }
                break;
            case Intent.ACTION_MEDIA_EJECT:
                LogUtil.d(ITAG.USB,
                        "USB device ejected",
                        "path: " + path
                );
                if (mListener != null) {
                    mListener.onUsbDeviceEject(path);
                }
                break;
            case Intent.ACTION_MEDIA_UNMOUNTED:
                LogUtil.d(ITAG.USB,
                        "USB device unmounted",
                        "path: " + path
                );
                if (mListener != null) {
                    mListener.onUsbDeviceUnmount(path);
                }
                break;
        }
    }


    @Override
    public void init(IFileScan scanner) {
        try {
            LogUtil.d(ITAG.USB, "USBManager init started");
            registerUsbBroadcast();
            // 在初始化时进触发已行挂载U盘的媒体库扫描
            // 以便向UsbStatus中的已扫描集合同步最新的状态, 使已扫描判断方法返回准确的结果
            scanMountedUsbPath(scanner);
            LogUtil.d(ITAG.USB, "USBManager init success");
        } catch (Exception e) {
            LogUtil.e(ITAG.USB, "USBManager init exception: ", e);
        }
    }

    @Override
    public void release() {
        try {
            LogUtil.d(ITAG.USB, "USBManager release started");
            unregisterUsbBroadcast();
            LogUtil.d(ITAG.USB, "USBManager release success");
        } catch (Exception e) {
            LogUtil.e(ITAG.USB, "USBManager release exception: ", e);
        }
    }

}
