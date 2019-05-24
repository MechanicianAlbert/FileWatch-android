package com.albertech.demo.func.base.impl;


import android.content.Context;

import com.albertech.demo.base.bean.BaseFileBean;
import com.albertech.demo.crud.query.AbsQueryMission;
import com.albertech.demo.crud.query.QueryHelper;
import com.albertech.demo.func.base.IFileContract;
import com.albertech.demo.func.base.query.IBaseQueryContract;

import java.util.List;


public abstract class BaseFileModel<Bean extends BaseFileBean>
        extends AbsQueryMission<Bean>
        implements IFileContract.IFileModel<Bean> {

    private IFileContract.IFilePresenter<Bean> mPresenter;

    @Override
    public void onQueryResult(String path, List<Bean> list) {
        super.onQueryResult(path, list);

        if (mPresenter != null) {
            mPresenter.onResult(path, list);
        }
    }


    @Override
    public void init(IFileContract.IFilePresenter<Bean> presenter) {
        mPresenter = presenter;
    }

    @Override
    public void release() {
        mPresenter = null;
    }

    @Override
    public void query(Context context) {
        QueryHelper.query(context, this);
    }

}
