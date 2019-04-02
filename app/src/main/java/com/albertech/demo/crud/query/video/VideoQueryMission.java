package com.albertech.demo.crud.query.video;

import android.database.Cursor;
import android.util.Log;

import com.albertech.demo.util.ForEachUtil;
import com.albertech.filewatch.api.IFileQueryMisson;

import java.util.List;


public class VideoQueryMission implements IFileQueryMisson<VideoBean> {

    @Override
    public final int type() {
        return VIDEO;
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
    public final VideoBean parse(Cursor cursor) {
        VideoBean bean = new VideoBean();
        bean.path = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PATH));
        return bean;
    }

    @Override
    public void onQueryResult(String path, List<VideoBean> list) {
        ForEachUtil.forEach(list, new ForEachUtil.ItemHandler<VideoBean>() {
            @Override
            public void handle(VideoBean bean) {
                Log.e("AAA", "Path: " + bean.path);
            }
        });
    }

}
