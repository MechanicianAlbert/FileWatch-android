package com.albertech.filewatch.core.query;

import android.content.Context;
import android.database.Cursor;

import com.albertech.filewatch.api.IFileQueryMisson;
import com.albertech.filewatch.core.query.cursor.ICursorFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件查询类
 * @param <Bean>
 */
public class QueryWork<Bean> implements Runnable {


    private Context mContext;
    /**
     * 游标工厂
     */
    private ICursorFactory mCursorFactory;
    /**
     * 查询任务
     */
    private IFileQueryMisson<Bean> mMission;
    /**
     * 查询父路径
     */
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
            // 获取游标
            Cursor cursor = mCursorFactory.createCursor(mContext, mMission.projection(), mParentPath);
            while (cursor != null && cursor.moveToNext()) {
                // 将单行数据转为具体文件描述Bean类型实例
                Bean bean = mMission.parse(cursor);
                // 将文件实例添加到集合
                list.add(bean);
            }
            // 关闭游标
            close(cursor);
            // 回调查询结果
            mMission.onQueryResult(mParentPath, list);
        }
        // 释放引用
        mMission = null;
        mContext = null;
        mCursorFactory = null;
    }

    /**
     * 通用关闭资源
     * @param closeables 可关闭资源
     */
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