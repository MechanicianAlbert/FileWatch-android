package com.albertech.demo.func.hierarchy.mvp.impl;


import android.content.Context;
import android.text.TextUtils;

import com.albertech.demo.crud.query.AbsQueryMission;
import com.albertech.demo.crud.query.QueryHelper;
import com.albertech.demo.func.hierarchy.HierarchyBean;
import com.albertech.demo.func.hierarchy.mvp.IHierarchyContract;

import java.util.List;


public class HierarchyQueryMission extends AbsQueryMission<HierarchyBean> implements IHierarchyContract.IHierarchyModel {

    private IHierarchyContract.IHierarchyPresenter mPresenter;
    private String mPath;


    @Override
    public int type() {
        return FILE;
    }

    @Override
    public String parentPath() {
        return !TextUtils.isEmpty(mPath) ? mPath : SD_CARD_PATH ;
    }

    @Override
    public boolean recursive() {
        return false;
    }

    @Override
    protected HierarchyBean createFileBean() {
        return new HierarchyBean();
    }

    @Override
    public void onQueryResult(String path, List list) {
        super.onQueryResult(path, list);
        if (mPresenter != null) {
            mPresenter.onResult(path, list);
        }
    }


    @Override
    public void init(IHierarchyContract.IHierarchyPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void release() {
        mPresenter = null;
    }

    @Override
    public void query(Context context, String path) {
        mPath = path;
        QueryHelper.getInstance().query(context, this);
    }

}
