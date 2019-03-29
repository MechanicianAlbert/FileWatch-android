package com.albertech.filewatch.core.query.cursor.impl.recursive;

import android.net.Uri;
import android.provider.MediaStore;

import com.albertech.filewatch.core.query.cursor.impl.abs.RecursiveCursurFactory;


public class RZipCursorFactory extends RecursiveCursurFactory {


    public RZipCursorFactory() {

    }


    @Override
    public String getPathColumnName() {
        return MediaStore.Files.FileColumns.DATA;
    }


    @Override
    protected Uri uri() {
        return MediaStore.Files.getContentUri("external");
    }

    @Override
    protected String[] selectionArgs() {
        return new String[]{
                ".zip",
                ".rar",
                ".tar",
                ".7z"
        };
    }

}
