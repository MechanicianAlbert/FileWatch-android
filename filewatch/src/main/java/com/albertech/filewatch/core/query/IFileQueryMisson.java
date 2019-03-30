package com.albertech.filewatch.core.query;

import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore;

import java.util.List;

public interface IFileQueryMisson<Bean> extends IFileType {

    String PATH_COLUMN_NAME = MediaStore.Files.FileColumns.DATA;

    String DEFAULT_PARENT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();


    int type();

    String[] projection();

    String parentPath();

    boolean recursive();

    Bean parse(Cursor cursor);

    void onQueryResult(String path, List<Bean> list);
}
