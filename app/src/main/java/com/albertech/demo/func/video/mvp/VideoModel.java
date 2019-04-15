package com.albertech.demo.func.video.mvp;


import android.database.Cursor;
import android.provider.MediaStore;

import com.albertech.demo.func.base.impl.BaseFileModel;
import com.albertech.demo.func.video.VideoBean;


public class VideoModel extends BaseFileModel<VideoBean> {


    @Override
    public final int type() {
        return VIDEO;
    }

    @Override
    public boolean recursive() {
        return true;
    }

    @Override
    public final String[] addProjection() {
        return new String[]{
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
