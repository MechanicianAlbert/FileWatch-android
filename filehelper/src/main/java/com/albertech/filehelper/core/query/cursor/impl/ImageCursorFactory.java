package com.albertech.filehelper.core.query.cursor.impl;

import android.net.Uri;
import android.provider.MediaStore;

/**
 * 图片文件查询游标工厂
 */
public class ImageCursorFactory extends AbsCursorFactory {

    public ImageCursorFactory(boolean recursive) {
        super(recursive);
    }


    @Override
    protected Uri uri() {
        return MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    }

}
