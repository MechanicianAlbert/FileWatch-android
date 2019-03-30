package com.albertech.filewatch.core.query;

import android.database.Cursor;

import java.util.List;

public interface IFileQureyListener<Bean> {

    Bean transfer(Cursor cursor);

    void onQueryResult(String path, List<Bean> list);
}
