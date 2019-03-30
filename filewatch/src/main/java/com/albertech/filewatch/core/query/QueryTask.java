package com.albertech.filewatch.core.query;

import android.content.Context;
import android.database.Cursor;

import com.albertech.filewatch.core.query.cursor.ICursorFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class QueryTask<Bean> implements Runnable {


    private Context mContext;
    private ICursorFactory mFactory;
    private String mPath;
    private IFileQureyListener<Bean> mListener;


    QueryTask(Context context, ICursorFactory factory, String path, boolean recursive, IFileQureyListener<Bean> listener) {
        mContext = context;
        mFactory = factory;
        mPath = path;
        mListener = listener;
    }


    @Override
    public void run() {
        List<Bean> list = new ArrayList<>();
        if (mFactory != null && mListener != null) {
            Cursor cursor = mFactory.createCursor(mContext, mPath);
            while (cursor != null && cursor.moveToNext()) {
                list.add(mListener.transfer(cursor));
            }
            close(cursor);
            mListener.onQueryResult(mPath, list);
        }
        mListener = null;
        mContext = null;
        mFactory = null;
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
}