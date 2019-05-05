package com.albertech.filewatch.core.query.cursor.impl.direct;

import android.net.Uri;
import android.provider.MediaStore;

import com.albertech.filewatch.core.query.cursor.impl.abs.DirectCursorFactory;


public class DImageCursorFactory extends DirectCursorFactory {

    @Override
    protected Uri uri() {
        return MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    }
}