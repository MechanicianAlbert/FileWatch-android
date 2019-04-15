package com.albertech.demo.func.zip.mvp;


import com.albertech.demo.func.base.query.IBaseQueryContract;
import com.albertech.demo.func.base.query.impl.BaseQueryViewFragment;
import com.albertech.demo.func.zip.ZipBean;
import com.albertech.demo.func.zip.adapter.ZipAdapter;


public class ZipViewFragment extends BaseQueryViewFragment<ZipAdapter, ZipBean> {


    @Override
    protected IBaseQueryContract.IBaseQueryPresenter<ZipBean> createPresenter() {
        return new ZipPresenter();
    }

    @Override
    protected ZipAdapter createAdapter() {
        return new ZipAdapter();
    }

}
