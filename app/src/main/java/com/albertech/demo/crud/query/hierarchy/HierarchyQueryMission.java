package com.albertech.demo.crud.query.hierarchy;

import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.albertech.demo.util.ForEachUtil;
import com.albertech.filewatch.api.FileHelper;
import com.albertech.filewatch.core.query.IFileQueryMisson;

import java.io.File;
import java.util.List;



public class HierarchyQueryMission implements IFileQueryMisson<HierarchyBean> {

    @Override
    public int type() {
        return FILE;
    }

    @Override
    public String[] projection() {
        return new String[]{
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.DISPLAY_NAME,
                MediaStore.Files.FileColumns.SIZE,
                MediaStore.Files.FileColumns.DATE_MODIFIED,
                MediaStore.Files.FileColumns.PARENT,
        };
    }

    @Override
    public String parentPath() {
        return null;
    }

    @Override
    public boolean recursive() {
        return false;
    }

    @Override
    public HierarchyBean parse(Cursor cursor) {
        HierarchyBean bean = new HierarchyBean();
        bean.path = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
        bean.name = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME));
        bean.size = cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE));
        bean.date = cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATE_MODIFIED));
        bean.parent = cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns.PARENT));
        bean.type = new File(bean.path).isDirectory() ? 0 : 1;
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
                        + "Size: " + bean.path + "\n"
                        + "Date: " + bean.path + "\n"
                        + "Type: " + FileHelper.fileTypeName(bean.type) + "\n"
                        + "Parent: " + bean.parent + "\n";
                Log.e("AAA", msg);
            }
        });
    }
}
