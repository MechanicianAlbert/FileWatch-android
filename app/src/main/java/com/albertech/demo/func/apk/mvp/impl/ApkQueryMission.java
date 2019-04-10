package com.albertech.demo.func.apk.mvp.impl;


import android.content.Context;

import com.albertech.demo.crud.query.AbsQueryMission;
import com.albertech.demo.crud.query.QueryHelper;
import com.albertech.demo.func.apk.ApkBean;
import com.albertech.demo.func.apk.mvp.IApkContract;

import java.util.List;


public class ApkQueryMission extends AbsQueryMission<ApkBean> implements IApkContract.IApkModel {

    private IApkContract.IApkPresenter mPresenter;


    @Override
    public int type() {
        return APK;
    }

    @Override
    public boolean recursive() {
        return true;
    }

    @Override
    protected ApkBean createFileBean() {
        return new ApkBean();
    }

    @Override
    public void onQueryResult(String path, List list) {
        super.onQueryResult(path, list);

        if (mPresenter != null) {
            mPresenter.onResult(path, list);
        }
    }


    @Override
    public void init(IApkContract.IApkPresenter presenter) {
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
