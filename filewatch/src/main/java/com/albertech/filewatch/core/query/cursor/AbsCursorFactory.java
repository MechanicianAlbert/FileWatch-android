package com.albertech.filewatch.core.query.cursor;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;


public abstract class AbsCursorFactory implements ICursorFactory {

    protected static final String[] DEFAULT_PROJECTION = new String[] {MediaStore.Files.FileColumns.DATA};


    private String[] mProjection = DEFAULT_PROJECTION;


    public AbsCursorFactory() {

    }

    public AbsCursorFactory(String[] projection) {
        if (projection != null && projection.length > 0) {
            mProjection = projection;
        }
    }


    @Override
    public Cursor createCursor(Context context, String path) {
        return context.getContentResolver().query(
                uri(),
                new String[]{MediaStore.Files.FileColumns.DATA},
                createSelectionByPath(path),
                createSelectionArgsByPath(path),
                null
        );
    }


    protected String createSelectionByPath(String path) {
        return MediaStore.Files.FileColumns.DATA + " LIKE ? ";
    }

    protected String[] createSelectionArgsByPath(String path) {
        return new String[]{path + "%"};
    }

    protected abstract Uri uri();

}
