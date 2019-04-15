package com.albertech.demo.func.base.query.impl;


import android.content.Context;

import com.albertech.demo.base.bean.BaseFileBean;
import com.albertech.demo.crud.query.AbsQueryMission;
import com.albertech.demo.crud.query.QueryHelper;
import com.albertech.demo.func.base.query.IBaseQueryContract;

import java.util.List;


public abstract class BaseQueryModel<Bean extends BaseFileBean> extends AbsQueryMission<Bean> implements IBaseQueryContract.IBaseQueryModel<Bean> {

    private IBaseQueryContract.IBaseQueryPresenter<Bean> mPresenter;

    @Override
    public void onQueryResult(String path, List<Bean> list) {
        super.onQueryResult(path, list);

        if (mPresenter != null) {
            mPresenter.onResult(path, list);
        }
    }


    @Override
    public void init(IBaseQueryContract.IBaseQueryPresenter<Bean> presenter) {
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
