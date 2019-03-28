package com.albertech.filewatch.core.query;

import android.content.Context;
import android.database.Cursor;
import android.util.SparseArray;


import com.albertech.filewatch.core.query.cursor.ICursorFactory;
import com.albertech.filewatch.core.query.cursor.impl.ApkCursorFactory;
import com.albertech.filewatch.core.query.cursor.impl.AudioCursorFactory;
import com.albertech.filewatch.core.query.cursor.impl.DocCursorFactory;
import com.albertech.filewatch.core.query.cursor.impl.FileCursorFactory;
import com.albertech.filewatch.core.query.cursor.impl.ImageCursorFactory;
import com.albertech.filewatch.core.query.cursor.impl.VideoCursorFactory;
import com.albertech.filewatch.core.query.cursor.impl.ZipCursorFactory;

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

        QueryTask(Context context, int type, String path, IFileQureyListener listener) {
            mContext = context;
            mType = type;
            mPath = path;
            mListener = listener;
        }

        @Override
        public void run() {
            List<String> list= new ArrayList<>();
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
        CURSOR_FACTORIES.put(FILE, new FileCursorFactory());
        CURSOR_FACTORIES.put(IMAGE, new ImageCursorFactory());
        CURSOR_FACTORIES.put(AUDIO, new AudioCursorFactory());
        CURSOR_FACTORIES.put(VIDEO, new VideoCursorFactory());
        CURSOR_FACTORIES.put(DOC, new DocCursorFactory());
        CURSOR_FACTORIES.put(ZIP, new ZipCursorFactory());
        CURSOR_FACTORIES.put(APK, new ApkCursorFactory());
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
        EXECUTOR.execute(new QueryTask(mContext, type, path, listener));
    }

}
