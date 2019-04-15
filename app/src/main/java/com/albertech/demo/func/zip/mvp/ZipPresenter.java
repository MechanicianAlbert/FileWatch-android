package com.albertech.demo.func.zip.mvp;


import com.albertech.demo.func.base.query.IBaseQueryContract;
import com.albertech.demo.func.base.query.impl.BaseQueryPresenter;
import com.albertech.demo.func.zip.ZipBean;


public class ZipPresenter extends BaseQueryPresenter<ZipBean> {


    @Override
    protected IBaseQueryContract.IBaseQueryModel<ZipBean> createModel() {
        return new ZipModel();
    }

}
