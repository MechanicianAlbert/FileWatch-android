package com.albertech.filehelper.core.usb;

import java.io.Serializable;


public class UsbDeviceInfo implements Serializable {

    public final String NAME;
    public final String PATH;


    UsbDeviceInfo(String NAME, String PATH) {
        this.NAME = NAME;
        this.PATH = PATH;
    }

    @Override
    public String toString() {
        // 直接使用U盘名称, 同名U盘不容易区分, 待产品交互定义
        return NAME;
//        return NAME + "_" + PATH.replace("/storage/", "");
    }

}
