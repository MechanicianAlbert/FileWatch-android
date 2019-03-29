package com.albertech.filewatch.core.query.cursor.impl.abs;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.albertech.filewatch.core.query.cursor.ICursorFactory;


public abstract class AbsCursorFactory implements ICursorFactory {

    protected static final String[] DEFAULT_PROJECTION = new String[]{MediaStore.Files.FileColumns.DATA};


    public AbsCursorFactory() {

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
        return createSelectionByPath(recursive());
    }

    protected String[] createSelectionArgsByPath(String path) {
        return createSelectionArgsByPath(path, recursive());
    }

    protected boolean recursive() {
        return false;
    }

    protected String[] selectionArgs() {
        return null;
    }

    private String createSelectionByPath(boolean recursive) {
        StringBuffer b = new StringBuffer("");
        String[] template = selectionArgs();
        boolean templateAvailable = template != null && template.length > 0;

        if (templateAvailable) {
            if (recursive) {
                b.append(MediaStore.Files.FileColumns.DATA).append(" LIKE ? AND ");
            } else {
                b.append(MediaStore.Files.FileColumns.DATA).append(" LIKE ? AND ");
                b.append(MediaStore.Files.FileColumns.DATA).append(" NOT LIKE ? AND ");
            }
        } else {
            if (recursive) {
                b.append(MediaStore.Files.FileColumns.DATA).append(" LIKE ? ");
            } else {
                b.append(MediaStore.Files.FileColumns.DATA).append(" LIKE ? AND ");
                b.append(MediaStore.Files.FileColumns.DATA).append(" NOT LIKE ? ");
            }
        }

        if (templateAvailable) {
            for (int i = 0; i < template.length - 1; i++) {
                b.append(MediaStore.Files.FileColumns.DATA).append(" LIKE ? OR ");
            }
            b.append(MediaStore.Files.FileColumns.DATA).append(" LIKE ? ");
        }

        String selection = b.toString();
        Log.e("AAA", selection);
        return selection;
    }

    private String[] createSelectionArgsByPath(String path, boolean recursive) {
        String[] templateArgs = selectionArgs();
        boolean templateAvailable = templateArgs != null && templateArgs.length > 0;
        int templateLength = templateAvailable ? templateArgs.length : 0;

        int additionalArgCount = recursive ? 1 : 2;
        String[] selectionArgs = new String[templateLength + additionalArgCount];

        selectionArgs[0] = path + "%";
        if (!recursive) {
            selectionArgs[1] = createDisableRecursiveConditionArg(path);
        }
        if (templateAvailable) {
            for (int i = 0; i < templateLength; i++) {
                selectionArgs[i + additionalArgCount] = createParentPathConditionArg(path) + templateArgs[i];
            }
        }
        for (int i = 0; i < selectionArgs.length; i++) {
            Log.e("AAA", selectionArgs[i]);
        }
        return selectionArgs;
    }

    private String createParentPathConditionArg(String path) {
        return path + "%";
    }

    private String createDisableRecursiveConditionArg(String path) {
        return path + "/%/%";
    }

    protected abstract Uri uri();


}
