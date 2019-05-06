package com.albertech.filehelper.core.query;

import android.content.Context;

import com.albertech.filehelper.api.IFileQueryMisson;

/**
 * 文件查询功能接口
 */
public interface IFileQuery extends IFileType {

    /**
     * 根据查询任务查询文件
     * @param context 上下文
     * @param mission 查询任务
     */
    void queryByMission(Context context, IFileQueryMisson mission);

}
