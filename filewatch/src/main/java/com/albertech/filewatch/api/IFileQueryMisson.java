package com.albertech.filewatch.api;

import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore;

import com.albertech.filewatch.api.IFileConstant;

import java.util.List;

public interface IFileQueryMisson<Bean> extends IFileConstant {


    int type();

    String[] projection();

    String parentPath();

    boolean recursive();

    Bean parse(Cursor cursor);

    void onQueryResult(String path, List<Bean> list);
}
