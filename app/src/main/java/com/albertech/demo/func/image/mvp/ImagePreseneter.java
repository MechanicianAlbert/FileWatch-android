package com.albertech.demo.func.image.mvp;


import com.albertech.demo.func.base.IFileContract;
import com.albertech.demo.func.base.impl.BaseFilePresenter;
import com.albertech.demo.func.image.ImageBean;


public class ImagePreseneter extends BaseFilePresenter<ImageBean> {


    @Override
    protected IFileContract.IFileModel<ImageBean> createModel() {
        return new ImageModel();
    }
}
