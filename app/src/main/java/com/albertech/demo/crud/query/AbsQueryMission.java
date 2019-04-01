package com.albertech.demo.crud.query;

import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.albertech.demo.base.bean.BaseFileBean;
import com.albertech.demo.crud.query.hierarchy.HierarchyBean;
import com.albertech.demo.util.ForEachUtil;
import com.albertech.demo.util.SizeUtil;
import com.albertech.filewatch.core.query.IFileQueryMisson;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;



public abstract class AbsQueryMission<Bean extends BaseFileBean> implements IFileQueryMisson<Bean> {

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.CHINA);

    private static final String[] BASE_PROJECTION = new String[]{
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.TITLE,
            MediaStore.Files.FileColumns.SIZE,
            MediaStore.Files.FileColumns.DATE_MODIFIED,
            MediaStore.Files.FileColumns.PARENT,
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
        return null;
    }

    @Override
    public Bean parse(Cursor cursor) {
        Bean bean = createFileBean();
        bean.path = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
        bean.name = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME));
        bean.size = cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE));
        bean.date = cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATE_MODIFIED)) * 1000;
        bean.parent = cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns.PARENT));
        return bean;
    }

    @Override
    public void onQueryResult(String path, List list) {
        ForEachUtil.forEach(list, new ForEachUtil.ItemHandler<HierarchyBean>() {
            @Override
            public void handle(HierarchyBean bean) {
                String msg = "File details:\n"
                        + "Path: " + bean.path + "\n"
                        + "Name: " + bean.name + "\n"
                        + "Size: " + SizeUtil.format(bean.size) + "\n"
                        + "Date: " + FORMAT.format(bean.date) + "\n"
                        + "Parent: " + bean.parent + "\n";
                Log.i("AAA", msg);
            }
        });
    }


    protected String[] addProjection() {
        return null;
    }

    protected abstract Bean createFileBean();
}
