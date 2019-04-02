package com.albertech.filewatch.core.query.cursor.impl.abs;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.albertech.filewatch.core.query.cursor.ICursorFactory;


public abstract class AbsCursorFactory implements ICursorFactory {


    @Override
    public Cursor createCursor(Context context, String[] projection, String parentPath) {
        return context.getContentResolver().query(
                uri(),
                createProjection(projection),
                createSelection(),
                createSelectionArgsByParentPath(parentPath),
                null
        );
    }


    protected String[] createProjection(String[] projection) {
        return projection != null ? projection : new String[]{PATH_COLUMN_NAME};
    }

    protected String createSelection() {
        return createSelection(recursive());
    }

    protected String[] createSelectionArgsByParentPath(String parentPath) {
        return createSelectionArgsByParentPath(parentPath, recursive());
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
                b.append(PATH_COLUMN_NAME).append(" LIKE ? AND (");
            } else {
                b.append(PATH_COLUMN_NAME).append(" LIKE ? AND ");
                b.append(PATH_COLUMN_NAME).append(" NOT LIKE ? AND (");
            }
        } else {
            if (recursive) {
                b.append(PATH_COLUMN_NAME).append(" LIKE ? ");
            } else {
                b.append(PATH_COLUMN_NAME).append(" LIKE ? AND ");
                b.append(PATH_COLUMN_NAME).append(" NOT LIKE ? ");
            }
        }

        if (templateAvailable) {
            for (int i = 0; i < template.length - 1; i++) {
                b.append(PATH_COLUMN_NAME).append(" LIKE ? OR ");
            }
            b.append(PATH_COLUMN_NAME).append(" LIKE ? )");
        }

        String selection = b.toString();
        return selection;
    }

    private String[] createSelectionArgsByParentPath(String parentPath, boolean recursive) {
        String[] templateArgs = selectionArgs();
        boolean templateAvailable = templateArgs != null && templateArgs.length > 0;
        int templateLength = templateAvailable ? templateArgs.length : 0;

        int additionalArgCount = recursive ? 1 : 2;
        String[] selectionArgs = new String[templateLength + additionalArgCount];

        selectionArgs[0] = createParentPathConditionArg(parentPath);
        if (!recursive) {
            selectionArgs[1] = createDisableRecursiveConditionArg(parentPath);
        }
        if (templateAvailable) {
            for (int i = 0; i < templateLength; i++) {
                selectionArgs[i + additionalArgCount] = createParentPathConditionArg(parentPath) + templateArgs[i];
            }
        }
        return selectionArgs;
    }

    private String createParentPathConditionArg(String path) {
        return path + "/%";
    }

    private String createDisableRecursiveConditionArg(String path) {
        return path + "/%/%";
    }


    protected abstract Uri uri();
}
