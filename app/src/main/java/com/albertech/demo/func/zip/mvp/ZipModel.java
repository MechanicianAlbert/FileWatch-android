package com.albertech.demo.func.zip.mvp;


import com.albertech.demo.func.base.impl.BaseFileModel;
import com.albertech.demo.func.zip.ZipBean;


public class ZipModel extends BaseFileModel<ZipBean> {


    @Override
    public int type() {
        return ZIP;
    }

    @Override
    public boolean recursive() {
        return true;
    }

    @Override
    protected ZipBean createFileBean() {
        return new ZipBean();
    }

}
