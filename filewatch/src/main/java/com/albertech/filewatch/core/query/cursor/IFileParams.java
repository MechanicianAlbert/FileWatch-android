package com.albertech.filewatch.core.query.cursor;

import android.os.Environment;
import android.provider.MediaStore;

public interface IFileParams {

    String DEFAULT_PARENT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    String COLUMN_NAME_PATH = MediaStore.Files.FileColumns.DATA;
    String COLUMN_NAME_MEDIA_TYPE = MediaStore.Files.FileColumns.MEDIA_TYPE;


    String[] DOC_SUFFIX = new String[]{
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

    String[] APK_SUFFIX = new String[]{
            ".apk"
    };

    String[] ZIP_SUFFIX = new String[]{
            ".zip",
            ".rar",
            ".tar",
            ".7z"
    };

}
