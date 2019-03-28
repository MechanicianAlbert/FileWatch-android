package com.albertech.filewatch.content.query;

import android.content.Context;
import android.database.Cursor;
import android.util.SparseArray;


import com.albertech.filewatch.api.IFileQuery;
import com.albertech.filewatch.content.query.cursor.ICursorFactory;
import com.albertech.filewatch.content.query.cursor.impl.ApkCursorFactory;
import com.albertech.filewatch.content.query.cursor.impl.AudioCursorFactory;
import com.albertech.filewatch.content.query.cursor.impl.DocCursorFactory;
import com.albertech.filewatch.content.query.cursor.impl.FileCursorFactory;
import com.albertech.filewatch.content.query.cursor.impl.ImageCursorFactory;
import com.albertech.filewatch.content.query.cursor.impl.VideoCursorFactory;
import com.albertech.filewatch.content.query.cursor.impl.ZipCursorFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FileQueryer implements IFileQuery {

    private static final String TAG = FileQueryer.class.getSimpleName();


    private final SparseArray<ICursorFactory> CURSOR_FACTORIES = new SparseArray<>();


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
    public List<String> queryFileByTypeAndPath(int type, String path) {
        List<String> list = new ArrayList<>();
        ICursorFactory factory = CURSOR_FACTORIES.get(type);
        if (factory != null) {
            Cursor cursor = factory.createCursor(mContext, path);
            while (cursor != null && cursor.moveToNext()) {
                list.add(cursor.getString(cursor.getColumnIndex(factory.getPathColumnName())));
            }
            close(cursor);
        }
        return list;
    }

}
