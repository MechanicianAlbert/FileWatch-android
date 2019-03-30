package com.albertech.filewatch.core.query;

import android.content.Context;



public interface IFileQuery extends IFileType {

    void queryFileByTypeAndParentPath(Context context, IFileQueryMisson mission);
}
