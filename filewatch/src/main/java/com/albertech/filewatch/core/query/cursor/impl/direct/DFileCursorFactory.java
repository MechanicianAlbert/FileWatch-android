package com.albertech.filewatch.core.query.cursor.impl.direct;

import android.net.Uri;
import android.provider.MediaStore;

import com.albertech.filewatch.core.query.cursor.impl.abs.DirectCursorFactory;



public class DFileCursorFactory extends DirectCursorFactory {


    public DFileCursorFactory() {

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
