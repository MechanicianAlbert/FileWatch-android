package com.albertech.filewatch.content.query.cursor;

import android.content.Context;
import android.database.Cursor;


public interface ICursorFactory {

    Cursor createCursor(Context context, String path);

    String getPathColumnName();
}
