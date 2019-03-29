package com.albertech.filewatch.core.query;

import android.content.Context;
import android.database.Cursor;
import android.util.SparseArray;


import com.albertech.filewatch.core.query.cursor.ICursorFactory;
import com.albertech.filewatch.core.query.cursor.impl.direct.DApkCursorFactory;
import com.albertech.filewatch.core.query.cursor.impl.direct.DAudioCursorFactory;
import com.albertech.filewatch.core.query.cursor.impl.direct.DDocCursorFactory;
import com.albertech.filewatch.core.query.cursor.impl.direct.DFileCursorFactory;
import com.albertech.filewatch.core.query.cursor.impl.direct.DImageCursorFactory;
import com.albertech.filewatch.core.query.cursor.impl.direct.DVideoCursorFactory;
import com.albertech.filewatch.core.query.cursor.impl.direct.DZipCursorFactory;
import com.albertech.filewatch.core.query.cursor.impl.recursive.RApkCursorFactory;
import com.albertech.filewatch.core.query.cursor.impl.recursive.RAudioCursorFactory;
import com.albertech.filewatch.core.query.cursor.impl.recursive.RDocCursorFactory;
import com.albertech.filewatch.core.query.cursor.impl.recursive.RFileCursorFactory;
import com.albertech.filewatch.core.query.cursor.impl.recursive.RImageCursorFactory;
import com.albertech.filewatch.core.query.cursor.impl.recursive.RVideoCursorFactory;
import com.albertech.filewatch.core.query.cursor.impl.recursive.RZipCursorFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class FileQueryer implements IFileQuery {

    private static class QueryTask implements Runnable {

        private Context mContext;
        private int mType;
        private String mPath;
        private IFileQureyListener mListener;

        QueryTask(Context context, int type, String path, boolean recursive, IFileQureyListener listener) {
            mContext = context;
            mType = recursive ? -type : type;
            mPath = path;
            mListener = listener;
        }

        @Override
        public void run() {
            List<String> list = new ArrayList<>();
            ICursorFactory factory = CURSOR_FACTORIES.get(mType);
            if (factory != null) {
                Cursor cursor = factory.createCursor(mContext, mPath);
                while (cursor != null && cursor.moveToNext()) {
                    list.add(cursor.getString(cursor.getColumnIndex(factory.getPathColumnName())));
                }
                close(cursor);
            }
            if (mListener != null) {
                mListener.onQueryResult(mPath, list);
            }
            mListener = null;
            mContext = null;
        }
    }


    private static final SparseArray<ICursorFactory> CURSOR_FACTORIES = new SparseArray<>();
    private final ExecutorService EXECUTOR = Executors.newCachedThreadPool();


    private final Context mContext;


    public FileQueryer(Context context) {
        if (context == null) {
            throw new NullPointerException("Context is null");
        }
        mContext = context;
        initCursorFactories();
    }


    private void initCursorFactories() {
        CURSOR_FACTORIES.put(IMAGE, new DImageCursorFactory());
        CURSOR_FACTORIES.put(AUDIO, new DAudioCursorFactory());
        CURSOR_FACTORIES.put(VIDEO, new DVideoCursorFactory());
        CURSOR_FACTORIES.put(DOC, new DDocCursorFactory());
        CURSOR_FACTORIES.put(ZIP, new DZipCursorFactory());
        CURSOR_FACTORIES.put(APK, new DApkCursorFactory());
        CURSOR_FACTORIES.put(FILE, new DFileCursorFactory());

        CURSOR_FACTORIES.put(-IMAGE, new RImageCursorFactory());
        CURSOR_FACTORIES.put(-AUDIO, new RAudioCursorFactory());
        CURSOR_FACTORIES.put(-VIDEO, new RVideoCursorFactory());
        CURSOR_FACTORIES.put(-DOC, new RDocCursorFactory());
        CURSOR_FACTORIES.put(-ZIP, new RZipCursorFactory());
        CURSOR_FACTORIES.put(-APK, new RApkCursorFactory());
        CURSOR_FACTORIES.put(-FILE, new RFileCursorFactory());
    }

    private static void close(Closeable... closeables) {
        if (closeables != null && closeables.length > 0) {
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    try {
                        closeable.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    @Override
    public void queryFileByTypeAndPath(int type, String path, IFileQureyListener listener) {
        queryFileByTypeAndPath(type, path, false, listener);
    }

    @Override
    public void queryFileByTypeAndPath(int type, String path, boolean recursive, IFileQureyListener listener) {
        EXECUTOR.execute(new QueryTask(mContext, type, path, recursive, listener));
    }

}
