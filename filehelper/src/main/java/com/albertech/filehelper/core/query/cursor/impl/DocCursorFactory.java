package com.albertech.filehelper.core.query.cursor.impl;

import android.net.Uri;
import android.provider.MediaStore;

/**
 * 文档文件查询游标工厂
 */
public class DocCursorFactory extends AbsCursorFactory {

    public DocCursorFactory(boolean recursive) {
        super(recursive);
    }


    @Override
    protected Uri uri() {
        return MediaStore.Files.getContentUri("external");
    }

    @Override
    protected String[] selectionArgs() {
        return DOC_SUFFIX;
    }

}
