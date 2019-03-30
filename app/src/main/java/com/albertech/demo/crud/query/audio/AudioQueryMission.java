package com.albertech.demo.crud.query.audio;

import android.database.Cursor;
import android.util.Log;

import com.albertech.demo.crud.query.image.ImageBean;
import com.albertech.demo.util.ForEachUtil;
import com.albertech.filewatch.core.query.IFileQueryMisson;

import java.util.List;


public class AudioQueryMission implements IFileQueryMisson<AudioBean> {

    @Override
    public final int type() {
        return IMAGE;
    }

    @Override
    public final String[] projection() {
        return new String[]{PATH_COLUMN_NAME};
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
    public final AudioBean parse(Cursor cursor) {
        AudioBean bean = new AudioBean();
        bean.path = cursor.getString(cursor.getColumnIndex(PATH_COLUMN_NAME));
        return bean;
    }

    @Override
    public void onQueryResult(String path, List<AudioBean> list) {
        ForEachUtil.forEach(list, new ForEachUtil.ItemHandler<AudioBean>() {
            @Override
            public void handle(AudioBean bean) {
                Log.e("AAA", "Path: " + bean.path);
            }
        });
    }

}
