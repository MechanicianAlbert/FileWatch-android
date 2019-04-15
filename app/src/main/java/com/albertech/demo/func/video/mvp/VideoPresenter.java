package com.albertech.demo.func.video.mvp;

import com.albertech.demo.func.base.IFileContract;
import com.albertech.demo.func.base.impl.BaseFilePresenter;
import com.albertech.demo.func.video.VideoBean;


public class VideoPresenter extends BaseFilePresenter<VideoBean> {


    @Override
    protected IFileContract.IFileModel<VideoBean> createModel() {
        return new VideoModel();
    }
}
