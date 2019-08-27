package com.albertech.filehelper.core.delete;

import android.content.Context;

import com.albertech.filehelper.core.query.cursor.IFileParams;


public class FileDelete implements IFileParams {

    public static void delete(Context context, String path) {
        if (context == null) {
            return;
        }
        context.getContentResolver().delete(
                URI_FILES,
                PATH_COLUMN_NAME + " = ?",
                new String[]{path}
        );
    }

}
