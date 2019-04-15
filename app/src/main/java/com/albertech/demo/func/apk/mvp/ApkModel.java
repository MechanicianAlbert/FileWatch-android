package com.albertech.demo.func.apk.mvp;


import com.albertech.demo.func.apk.ApkBean;
import com.albertech.demo.func.base.impl.BaseFileModel;


public class ApkModel extends BaseFileModel<ApkBean> {


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
