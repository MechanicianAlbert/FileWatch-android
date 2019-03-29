package com.albertech.filewatch.core.query.cursor.impl.recursive;

import android.net.Uri;
import android.provider.MediaStore;

import com.albertech.filewatch.core.query.cursor.impl.abs.RecursiveCursurFactory;


public class RVideoCursorFactory extends RecursiveCursurFactory {

    public RVideoCursorFactory() {

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
