package com.albertech.filewatch.core.query.cursor.impl;

import android.net.Uri;
import android.provider.MediaStore;

import com.albertech.filewatch.core.query.cursor.AbsCursorFactory;


public class VideoCursorFactory extends AbsCursorFactory {

    public VideoCursorFactory() {

    }

    public VideoCursorFactory(String[] projection) {
        super(projection);
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
