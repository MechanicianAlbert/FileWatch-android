package com.albertech.demo.func.video.mvp;

import com.albertech.demo.func.base.query.IBaseQueryContract;
import com.albertech.demo.func.base.query.impl.BaseQueryPresenter;
import com.albertech.demo.func.video.VideoBean;


public class VideoPresenter extends BaseQueryPresenter<VideoBean> {


    @Override
    protected IBaseQueryContract.IBaseQueryModel<VideoBean> createModel() {
        return new VideoModel();
    }
}
