package com.albertech.demo.func.image.mvp;


import com.albertech.demo.func.base.query.IBaseQueryContract;
import com.albertech.demo.func.base.query.impl.BaseQueryPresenter;
import com.albertech.demo.func.image.ImageBean;


public class ImagePreseneter extends BaseQueryPresenter<ImageBean> {


    @Override
    protected IBaseQueryContract.IBaseQueryModel<ImageBean> createModel() {
        return new ImageModel();
    }
}
