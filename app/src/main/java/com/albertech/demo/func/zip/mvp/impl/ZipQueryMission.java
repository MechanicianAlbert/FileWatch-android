package com.albertech.demo.func.zip.mvp.impl;


import android.content.Context;

import com.albertech.demo.crud.query.AbsQueryMission;
import com.albertech.demo.crud.query.QueryHelper;
import com.albertech.demo.func.zip.ZipBean;
import com.albertech.demo.func.zip.mvp.IZipContract;

import java.util.List;


public class ZipQueryMission extends AbsQueryMission<ZipBean> implements IZipContract.IZipModel {

    private IZipContract.IZipPresenter mPresenter;


    @Override
    public int type() {
        return ZIP;
    }

    @Override
    public boolean recursive() {
        return true;
    }

    @Override
    protected ZipBean createFileBean() {
        return new ZipBean();
    }

    @Override
    public void onQueryResult(String path, List list) {
        super.onQueryResult(path, list);

        if (mPresenter != null) {
            mPresenter.onResult(path, list);
        }
    }


    @Override
    public void init(IZipContract.IZipPresenter presenter) {
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
