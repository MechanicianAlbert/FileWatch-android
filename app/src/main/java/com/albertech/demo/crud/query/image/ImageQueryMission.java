package com.albertech.demo.crud.query.image;

import android.database.Cursor;
import android.util.Log;

import com.albertech.demo.util.ForEachUtil;
import com.albertech.filewatch.api.IFileQueryMisson;

import java.util.List;


public class ImageQueryMission implements IFileQueryMisson<ImageBean> {

    @Override
    public final int type() {
        return IMAGE;
    }

    @Override
    public final String[] projection() {
        return new String[]{COLUMN_NAME_PATH};
    }

    @Override
    public String parentPath() {
        return DEFAULT_PARENT_PATH;
    }

    @Override
    public boolean recursive() {
        return true;
    }

    @Override
    public final ImageBean parse(Cursor cursor) {
        ImageBean bean = new ImageBean();
        bean.path = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PATH));
        return bean;
    }

    @Override
    public void onQueryResult(String path, List<ImageBean> list) {
        ForEachUtil.forEach(list, new ForEachUtil.ItemHandler<ImageBean>() {
            @Override
            public void handle(ImageBean bean) {
                Log.e("AAA", "Path: " + bean.path);
            }
        });
    }

}
