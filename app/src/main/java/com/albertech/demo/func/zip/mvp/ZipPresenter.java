package com.albertech.demo.func.zip.mvp;


import com.albertech.demo.func.base.IFileContract;
import com.albertech.demo.func.base.impl.BaseFilePresenter;
import com.albertech.demo.func.zip.ZipBean;


public class ZipPresenter extends BaseFilePresenter<ZipBean> {


    @Override
    protected IFileContract.IFileModel<ZipBean> createModel() {
        return new ZipModel();
    }

}
