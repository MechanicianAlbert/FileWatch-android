package com.albertech.demo.func.audio.mvp;


import android.database.Cursor;

import com.albertech.demo.R;
import com.albertech.demo.func.audio.AudioBean;
import com.albertech.demo.func.base.impl.BaseFileModel;



public class AudioModel extends BaseFileModel<AudioBean> {


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
    protected AudioBean createFileBean() {
        return new AudioBean();
    }

    @Override
    public AudioBean parse(Cursor cursor) {
        AudioBean bean = super.parse(cursor);
        bean.type = AUDIO;
        bean.icon = R.drawable.ic_type_audio;
        return bean;
    }

}
