package com.albertech.filewatch.content.query.cursor.impl;

import android.net.Uri;
import android.provider.MediaStore;

import com.albertech.filewatch.content.query.cursor.AbsCursorFactory;


public class ImageCursorFactory extends AbsCursorFactory {

    public ImageCursorFactory() {

    }

    public ImageCursorFactory(String[] projection) {
        super(projection);
    }


    @Override
    public String getPathColumnName() {
        return MediaStore.Images.Media.DATA;
    }

    @Override
    protected Uri uri() {
        return MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    }
}
