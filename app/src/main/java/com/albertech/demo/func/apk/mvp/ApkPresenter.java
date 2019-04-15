package com.albertech.demo.func.apk.mvp;


import com.albertech.demo.func.apk.ApkBean;
import com.albertech.demo.func.base.IFileContract;
import com.albertech.demo.func.base.impl.BaseFilePresenter;


public class ApkPresenter extends BaseFilePresenter<ApkBean> {

    @Override
    protected IFileContract.IFileModel<ApkBean> createModel() {
        return new ApkModel();
    }

}
