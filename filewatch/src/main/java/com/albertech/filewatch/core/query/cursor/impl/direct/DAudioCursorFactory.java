package com.albertech.filewatch.core.query.cursor.impl.direct;

import android.net.Uri;
import android.provider.MediaStore;

import com.albertech.filewatch.core.query.cursor.impl.abs.DirectCursorFactory;


public class DAudioCursorFactory extends DirectCursorFactory {

    public DAudioCursorFactory() {

    }


    @Override
    public String getPathColumnName() {
        return MediaStore.Audio.Media.DATA;
    }

    @Override
    protected Uri uri() {
        return MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    }
}
