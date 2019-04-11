package com.albertech.demo.crud.query;

import android.content.Context;
import android.os.Environment;

import com.albertech.filewatch.api.FileHelper;
import com.albertech.filewatch.api.IFileQueryMisson;
import com.albertech.filewatch.core.query.IFileQuery;


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


    public static final String SD_CARD = Environment.getExternalStorageDirectory().getAbsolutePath();


    private IFileQuery mQueryer = FileHelper.createDefaultFileQuery();


    public void query(Context context, IFileQueryMisson misson) {
        if (mQueryer != null) {
            mQueryer.queryFileByTypeAndParentPath(context, misson);
        }
    }

}
