package com.albertech.filehelper.core.query.cursor.impl;

import android.net.Uri;
import android.provider.MediaStore;

/**
 * 安装文件查询游标工厂
 */
public class ApkCursorFactory extends AbsCursorFactory {

    public ApkCursorFactory(boolean recursive) {
        super(recursive);
    }


    @Override
    protected Uri uri() {
        return MediaStore.Files.getContentUri("external");
    }

    @Override
    protected String[] selectionArgs() {
        return APK_SUFFIX;
    }

}
