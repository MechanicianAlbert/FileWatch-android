package com.albertech.filehelper.core.query.cursor.impl;

import android.net.Uri;
import android.provider.MediaStore;

/**
 * 压缩文件查询游标工厂
 */
public class ZipCursorFactory extends AbsCursorFactory {

    public ZipCursorFactory(boolean recursive) {
        super(recursive);
    }


    @Override
    protected Uri uri() {
        return MediaStore.Files.getContentUri("external");
    }

    @Override
    protected String[] selectionArgs() {
        return ZIP_SUFFIX;
    }

}
