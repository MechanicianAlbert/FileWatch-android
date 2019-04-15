package com.albertech.demo.func.apk.mvp;

import com.albertech.demo.func.apk.ApkBean;
import com.albertech.demo.func.apk.adapter.ApkAdapter;
import com.albertech.demo.func.base.IFileContract;
import com.albertech.demo.func.base.impl.BaseFileViewFragment;


public class ApkViewFragment extends BaseFileViewFragment<ApkAdapter, ApkBean> {


    @Override
    protected IFileContract.IFilePresenter<ApkBean> createPresenter() {
        return new ApkPresenter();
    }

    @Override
    protected ApkAdapter createAdapter() {
        return new ApkAdapter(this);
    }
}
