package com.albertech.filewatch.core.query.cursor.impl.abs;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.albertech.filewatch.core.query.cursor.ICursorFactory;


public abstract class AbsCursorFactory implements ICursorFactory {


    @Override
    public Cursor createCursor(Context context, String path) {
        return context.getContentResolver().query(
                uri(),
                createProjection(),
                createSelection(),
                createSelectionArgsByPath(path),
                null
        );
    }


    protected String[] createProjection() {
        return new String[]{MediaStore.Files.FileColumns.DATA};
    }

    protected String createSelection() {
        return createSelection(recursive());
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


    private String createSelection(boolean recursive) {
        StringBuffer b = new StringBuffer("");
        String[] template = selectionArgs();
        boolean templateAvailable = template != null && template.length > 0;

        if (templateAvailable) {
            if (recursive) {
                b.append(MediaStore.Files.FileColumns.DATA).append(" LIKE ? AND (");
            } else {
                b.append(MediaStore.Files.FileColumns.DATA).append(" LIKE ? AND ");
                b.append(MediaStore.Files.FileColumns.DATA).append(" NOT LIKE ? AND (");
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
            b.append(MediaStore.Files.FileColumns.DATA).append(" LIKE ? )");
        }

        String selection = b.toString();
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
