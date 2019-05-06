package com.albertech.filehelper.core.query.cursor.impl;

import android.net.Uri;
import android.provider.MediaStore;

/**
 * 音频文件查询游标工厂
 */
public class AudioCursorFactory extends AbsCursorFactory {

    public AudioCursorFactory(boolean recursive) {
        super(recursive);
    }


    @Override
    protected Uri uri() {
        return MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    }

}
