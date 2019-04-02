package com.albertech.filewatch.core.query;

import android.content.Context;
import android.database.Cursor;

import com.albertech.filewatch.api.IFileQueryMisson;
import com.albertech.filewatch.core.query.cursor.ICursorFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class QueryWork<Bean> implements Runnable {


    private Context mContext;
    private ICursorFactory mCursorFactory;
    private IFileQueryMisson<Bean> mMission;
    private String mParentPath;


    QueryWork(Context context, ICursorFactory factory, IFileQueryMisson<Bean> mission) {
        mContext = context;
        mCursorFactory = factory;
        mMission = mission;
        mParentPath = mMission.parentPath();
    }


    @Override
    public void run() {
        List<Bean> list = new ArrayList<>();
        if (mCursorFactory != null && mMission != null) {
            Cursor cursor = mCursorFactory.createCursor(mContext, mMission.projection(), mParentPath);
            while (cursor != null && cursor.moveToNext()) {
                list.add(mMission.parse(cursor));
            }
            close(cursor);
            mMission.onQueryResult(mParentPath, list);
        }
        mMission = null;
        mContext = null;
        mCursorFactory = null;
    }


    private void close(Closeable... closeables) {
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