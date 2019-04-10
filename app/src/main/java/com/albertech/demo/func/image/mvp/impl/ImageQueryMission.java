package com.albertech.demo.func.image.mvp.impl;


import android.content.Context;
import android.database.Cursor;

import com.albertech.demo.R;
import com.albertech.demo.crud.query.AbsQueryMission;
import com.albertech.demo.crud.query.QueryHelper;
import com.albertech.demo.func.image.ImageBean;
import com.albertech.demo.func.image.mvp.IImageContract;

import java.util.List;


public class ImageQueryMission extends AbsQueryMission<ImageBean> implements IImageContract.IImageModel {

    private IImageContract.IImagePresenter mPresenter;


    @Override
    public final int type() {
        return IMAGE;
    }

    @Override
    public boolean recursive() {
        return true;
    }

    @Override
    public final String[] projection() {
        return new String[]{COLUMN_NAME_PATH};
    }

    @Override
    public ImageBean parse(Cursor cursor) {
        ImageBean bean = super.parse(cursor);
        bean.type = IMAGE;
        bean.icon = R.drawable.ic_type_image;
        return bean;
    }

    @Override
    protected ImageBean createFileBean() {
        return new ImageBean();
    }

    @Override
    public void onQueryResult(String path, List list) {
        super.onQueryResult(path, list);
        if (mPresenter != null) {
            mPresenter.onResult(path, list);
        }
    }


    @Override
    public void init(IImageContract.IImagePresenter presenter) {
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
