package com.albertech.filewatch.core.query.cursor;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;


public interface ICursorFactory extends IFileParams {

    String PATH_COLUMN_NAME = MediaStore.Files.FileColumns.DATA;


    Cursor createCursor(Context context, String[] projection, String parentPath);
}
