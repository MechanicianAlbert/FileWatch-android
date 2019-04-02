package com.albertech.demo.crud.query.audio;


import com.albertech.demo.crud.query.AbsQueryMission;


public class AudioQueryMission extends AbsQueryMission<AudioBean> {

    @Override
    public final int type() {
        return AUDIO;
    }

    @Override
    public final String[] projection() {
        return new String[]{COLUMN_NAME_PATH};
    }


    @Override
    protected AudioBean createFileBean() {
        return new AudioBean();
    }
}
