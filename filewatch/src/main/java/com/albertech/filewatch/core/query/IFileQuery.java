package com.albertech.filewatch.core.query;

import android.content.Context;

import com.albertech.filewatch.api.IFileQueryMisson;


public interface IFileQuery extends IFileType {

    void queryFileByTypeAndParentPath(Context context, IFileQueryMisson mission);
}
