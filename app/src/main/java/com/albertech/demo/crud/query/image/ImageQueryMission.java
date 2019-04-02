package com.albertech.demo.crud.query.image;


import com.albertech.demo.crud.query.AbsQueryMission;


public class ImageQueryMission extends AbsQueryMission<ImageBean> {

    @Override
    public final int type() {
        return IMAGE;
    }

    @Override
    public final String[] projection() {
        return new String[]{COLUMN_NAME_PATH};
    }

    @Override
    protected ImageBean createFileBean() {
        return new ImageBean();
    }

}
