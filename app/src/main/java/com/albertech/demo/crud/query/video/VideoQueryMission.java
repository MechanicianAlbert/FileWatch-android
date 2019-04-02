package com.albertech.demo.crud.query.video;


import android.database.Cursor;
import android.provider.MediaStore;

import com.albertech.demo.crud.query.AbsQueryMission;


public class VideoQueryMission extends AbsQueryMission<VideoBean> {

    @Override
    public final int type() {
        return VIDEO;
    }

    @Override
    public final String[] addProjection() {
        return new String[]{
//                COLUMN_NAME_PATH,
                MediaStore.Video.Media.DURATION
        };
    }

    @Override
    public VideoBean parse(Cursor cursor) {
        VideoBean b = super.parse(cursor);
        b.duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));

        return b;
    }

    @Override
    protected VideoBean createFileBean() {
        return new VideoBean();
    }

}
