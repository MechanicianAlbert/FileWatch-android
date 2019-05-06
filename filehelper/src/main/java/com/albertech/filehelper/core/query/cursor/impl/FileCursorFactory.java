package com.albertech.filehelper.core.query.cursor.impl;

import android.net.Uri;
import android.provider.MediaStore;

/**
 * 全部文件查询游标工厂
 */
public class FileCursorFactory extends AbsCursorFactory {

    public FileCursorFactory(boolean recursive) {
        super(recursive);
    }


    @Override
    protected Uri uri() {
        return MediaStore.Files.getContentUri("external");
    }

}
