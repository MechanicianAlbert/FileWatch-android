package com.albertech.demo.crud.query;

import android.content.Context;

import com.albertech.filehelper.api.FileHelper;
import com.albertech.filehelper.api.IFileQueryMisson;
import com.albertech.filehelper.core.query.IFileQuery;


public class QueryHelper {

    private static QueryHelper INSTANCE = new QueryHelper();

    private QueryHelper() {
        if (INSTANCE != null) {
            throw new RuntimeException("This class cannot be instantiated more than once");
        }
    }

    public static QueryHelper getInstance() {
        return INSTANCE;
    }


    private IFileQuery mQueryer = FileHelper.createDefaultFileQuery();


    public void query(Context context, IFileQueryMisson misson) {
        if (mQueryer != null) {
            mQueryer.queryByMission(context, misson);
        }
    }

}
