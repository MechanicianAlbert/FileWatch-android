package com.albertech.demo.crud.query;

import android.content.Context;

import com.albertech.filehelper.api.FileHelper;
import com.albertech.filehelper.api.IFileQueryMisson;
import com.albertech.filehelper.core.query.IFileQuery;


public class QueryHelper {

    public static void query(Context context, IFileQueryMisson misson) {
        FileHelper.getDefaultFileQuery().queryByMission(context, misson);
    }
}
