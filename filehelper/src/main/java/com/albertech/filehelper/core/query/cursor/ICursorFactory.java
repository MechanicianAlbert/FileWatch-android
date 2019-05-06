package com.albertech.filehelper.core.query.cursor;

import android.content.Context;
import android.database.Cursor;

/**
 * 游标抽象工厂接口
 */
public interface ICursorFactory extends IFileParams {

    /**
     * 获取游标
     * @param context 上下文
     * @param projection 查询字段
     * @param parentPath 查询路径
     * @return 游标
     */
    Cursor createCursor(Context context, String[] projection, String parentPath);

}
