package com.albertech.demo.func.apk.mvp;


import com.albertech.demo.func.apk.ApkBean;
import com.albertech.demo.func.base.query.impl.BaseQueryModel;


public class ApkModel extends BaseQueryModel<ApkBean> {


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

}
