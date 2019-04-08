package com.albertech.demo.func.video.mvp.impl;


import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.albertech.demo.crud.query.AbsQueryMission;
import com.albertech.demo.crud.query.QueryHelper;
import com.albertech.demo.func.video.VideoBean;
import com.albertech.demo.func.video.mvp.IVideoContract;

import java.util.List;


public class VideoQueryMission extends AbsQueryMission<VideoBean> implements IVideoContract.IVideoModel {

    private IVideoContract.IVideoPresenter mPresenter;


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

    @Override
    public void onQueryResult(String path, List list) {
        super.onQueryResult(path, list);
        if (mPresenter != null) {
            mPresenter.onResult(path, list);
        }
    }


    @Override
    public void init(IVideoContract.IVideoPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void release() {
        mPresenter = null;
    }

    @Override
    public void query(Context context) {
        QueryHelper.getInstance().query(context, this);
    }
}
