package com.albertech.demo.func.doc.mvp.impl;


import android.content.Context;

import com.albertech.demo.crud.query.AbsQueryMission;
import com.albertech.demo.crud.query.QueryHelper;
import com.albertech.demo.func.doc.DocBean;
import com.albertech.demo.func.doc.mvp.IDocContract;

import java.util.List;


public class DocQueryMission extends AbsQueryMission<DocBean> implements IDocContract.IDocModel {

    private IDocContract.IDocPresenter mPresenter;


    @Override
    public int type() {
        return DOC;
    }

    @Override
    public boolean recursive() {
        return true;
    }

    @Override
    protected DocBean createFileBean() {
        return new DocBean();
    }

    @Override
    public void onQueryResult(String path, List list) {
        super.onQueryResult(path, list);

        if (mPresenter != null) {
            mPresenter.onResult(path, list);
        }
    }


    @Override
    public void init(IDocContract.IDocPresenter presenter) {
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
