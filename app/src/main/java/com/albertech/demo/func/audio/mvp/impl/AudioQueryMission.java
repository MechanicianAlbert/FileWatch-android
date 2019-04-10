package com.albertech.demo.func.audio.mvp.impl;


import android.content.Context;
import android.database.Cursor;

import com.albertech.demo.R;
import com.albertech.demo.crud.query.AbsQueryMission;
import com.albertech.demo.crud.query.QueryHelper;
import com.albertech.demo.func.audio.AudioBean;
import com.albertech.demo.func.audio.mvp.IAudioContract;

import java.util.List;


public class AudioQueryMission extends AbsQueryMission<AudioBean> implements IAudioContract.IAudioModel {

    private IAudioContract.IAudioPresenter mPresenter;


    @Override
    public final int type() {
        return AUDIO;
    }

    @Override
    public final String[] projection() {
        return new String[]{COLUMN_NAME_PATH};
    }

    @Override
    public boolean recursive() {
        return true;
    }

    @Override
    public AudioBean parse(Cursor cursor) {
        AudioBean bean = super.parse(cursor);
        bean.type = AUDIO;
        bean.icon = R.drawable.ic_type_audio;
        return bean;
    }

    @Override
    protected AudioBean createFileBean() {
        return new AudioBean();
    }

    @Override
    public void onQueryResult(String path, List list) {
        super.onQueryResult(path, list);
        if (mPresenter != null) {
            mPresenter.onResult(path, list);
        }
    }


    @Override
    public void init(IAudioContract.IAudioPresenter presenter) {
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
