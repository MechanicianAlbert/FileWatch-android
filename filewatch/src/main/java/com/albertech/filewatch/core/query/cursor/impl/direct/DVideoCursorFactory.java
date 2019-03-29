package com.albertech.filewatch.core.query.cursor.impl.direct;

import android.net.Uri;
import android.provider.MediaStore;

import com.albertech.filewatch.core.query.cursor.impl.abs.DirectCursorFactory;


public class DVideoCursorFactory extends DirectCursorFactory {

    public DVideoCursorFactory() {

    }


    @Override
    public String getPathColumnName() {
        return MediaStore.Video.Media.DATA;
    }

    @Override
    protected Uri uri() {
        return MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    }
}
