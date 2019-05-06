package com.albertech.filehelper.api;

import android.database.Cursor;

import java.util.List;

/**
 * 文件查询任务接口
 * @param <Bean> 描述文件的Bean类
 */
public interface IFileQueryMisson<Bean> extends IFileConstant {

    /**
     * @return 文件类型
     */
    int type();

    /**
     * @return 查询字段
     */
    String[] projection();

    /**
     * @return 父路径
     */
    String parentPath();

    /**
     * @return 是否递归查询, 递归查询即查询根路径及其所有子路径
     */
    boolean recursive();

    /**
     * 将数据库查询结果解析为Bean
     * @param cursor 游标
     * @return 描述文件的Bean类型实例
     */
    Bean parse(Cursor cursor);

    /**
     * 查询结果回调
     * @param path
     * @param list
     */
    void onQueryResult(String path, List<Bean> list);

}
