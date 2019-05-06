package com.albertech.filehelper.core.query.cursor.impl;

import android.net.Uri;
import android.provider.MediaStore;

/**
 * 视频文件查询游标工厂
 */
public class VideoCursorFactory extends AbsCursorFactory {

    public VideoCursorFactory(boolean recursive) {
        super(recursive);
    }


    @Override
    protected Uri uri() {
        return MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    }

}
