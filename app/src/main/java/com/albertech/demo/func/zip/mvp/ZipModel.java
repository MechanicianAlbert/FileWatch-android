package com.albertech.demo.func.zip.mvp;


import com.albertech.demo.func.base.query.impl.BaseQueryModel;
import com.albertech.demo.func.zip.ZipBean;


public class ZipModel extends BaseQueryModel<ZipBean> {


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
