package com.albertech.demo.func.apk.mvp;


import com.albertech.demo.func.apk.ApkBean;
import com.albertech.demo.func.base.query.IBaseQueryContract;
import com.albertech.demo.func.base.query.impl.BaseQueryPresenter;


public class ApkPresenter extends BaseQueryPresenter<ApkBean> {

    @Override
    protected IBaseQueryContract.IBaseQueryModel<ApkBean> createModel() {
        return new ApkModel();
    }

}
