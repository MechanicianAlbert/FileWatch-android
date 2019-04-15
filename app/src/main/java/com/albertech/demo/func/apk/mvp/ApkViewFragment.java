package com.albertech.demo.func.apk.mvp;

import com.albertech.demo.func.apk.ApkBean;
import com.albertech.demo.func.apk.adapter.ApkAdapter;
import com.albertech.demo.func.base.query.IBaseQueryContract;
import com.albertech.demo.func.base.query.impl.BaseQueryViewFragment;


public class ApkViewFragment extends BaseQueryViewFragment<ApkAdapter, ApkBean> {


    @Override
    protected IBaseQueryContract.IBaseQueryPresenter<ApkBean> createPresenter() {
        return new ApkPresenter();
    }

    @Override
    protected ApkAdapter createAdapter() {
        return new ApkAdapter();
    }
}
