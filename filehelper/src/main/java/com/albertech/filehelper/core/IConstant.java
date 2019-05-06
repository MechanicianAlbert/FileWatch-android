package com.albertech.filehelper.core;

import android.os.Environment;

/**
 * 核心功能的公共常量
 */
public interface IConstant {

    /**
     * SD卡路径
     */
    String SD_CARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

}
