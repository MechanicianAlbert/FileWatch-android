package com.albertech.filewatch.core.query.cursor.impl.recursive;

import android.net.Uri;
import android.provider.MediaStore;

import com.albertech.filewatch.core.query.cursor.impl.abs.RecursiveCursurFactory;


public class RDocCursorFactory extends RecursiveCursurFactory {

    @Override
    protected Uri uri() {
        return MediaStore.Files.getContentUri("external");
    }

    @Override
    protected String[] selectionArgs() {
        return new String[]{
                ".pdf",
                ".doc",
                ".docx",
                ".xls",
                ".xlsx",
                ".ppt",
                ".pptx",
                ".pps",
                ".pages",
                ".txt",
                ".log",
                ".xml",
                ".html"
        };
    }

}
