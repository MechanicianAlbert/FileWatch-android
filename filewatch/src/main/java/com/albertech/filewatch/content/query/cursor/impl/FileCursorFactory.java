package com.albertech.filewatch.content.query.cursor.impl;

import android.net.Uri;
import android.provider.MediaStore;

import com.albertech.filewatch.content.query.cursor.AbsCursorFactory;


public class FileCursorFactory extends AbsCursorFactory {

    public FileCursorFactory() {

    }

    public FileCursorFactory(String[] projection) {
        super(projection);
    }


    @Override
    public String getPathColumnName() {
        return MediaStore.Files.FileColumns.DATA;
    }

    @Override
    protected Uri uri() {
        return MediaStore.Files.getContentUri("external");
    }
}
