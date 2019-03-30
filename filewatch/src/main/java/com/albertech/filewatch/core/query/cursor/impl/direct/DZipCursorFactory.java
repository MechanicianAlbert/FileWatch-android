package com.albertech.filewatch.core.query.cursor.impl.direct;

import android.net.Uri;
import android.provider.MediaStore;

import com.albertech.filewatch.core.query.cursor.impl.abs.DirectCursorFactory;


public class DZipCursorFactory extends DirectCursorFactory {

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
