package com.albertech.demo.crud.query;

import android.database.Cursor;
import android.util.Log;

import com.albertech.demo.base.bean.BaseFileBean;
import com.albertech.demo.util.ForEachUtil;
import com.albertech.demo.util.SizeUtil;
import com.albertech.filewatch.api.FileHelper;
import com.albertech.filewatch.api.IFileQueryMisson;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public abstract class AbsQueryMission<Bean extends BaseFileBean> implements IFileQueryMisson<Bean> {

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.CHINA);

    private static final String[] BASE_PROJECTION = new String[]{
            COLUMN_NAME_PATH,
            COLUMN_NAME_MEDIA_TYPE
    };


    @Override
    public String[] projection() {
        String[] added = addProjection();
        if (added != null && added.length > 0) {
            int length = added.length;
            String[] projection = new String[BASE_PROJECTION.length + length];
            System.arraycopy(BASE_PROJECTION, 0, projection, 0, BASE_PROJECTION.length);
            System.arraycopy(added, 0, projection, BASE_PROJECTION.length - 1, length);
            return projection;
        } else {
            return BASE_PROJECTION;
        }
    }

    @Override
    public String parentPath() {
        return DEFAULT_PARENT_PATH;
    }

    @Override
    public boolean recursive() {
        return false;
    }

    @Override
    public Bean parse(Cursor cursor) {
        return QueryUtil.parseCursorToBaseFileBean(cursor, createFileBean());
    }

    @Override
    public void onQueryResult(String path, List list) {
        ForEachUtil.forEach(list, new ForEachUtil.ItemHandler<BaseFileBean>() {
            @Override
            public void handle(BaseFileBean bean) {
                String msg = "File details:\n"
                        + "Path: " + bean.path + "\n"
                        + "Name: " + bean.name + "\n"
                        + "Size: " + SizeUtil.format(bean.size) + "\n"
                        + "Date: " + FORMAT.format(bean.date) + "\n"
                        + "Parent: " + bean.parent + "\n"
                        + "Suffix: " + bean.suffix + "\n"
                        + "Type: " + FileHelper.fileTypeName(bean.type) + "\n";
                Log.i("AAA", msg);
            }
        });
    }


    protected String[] addProjection() {
        return null;
    }


    protected abstract Bean createFileBean();
}
