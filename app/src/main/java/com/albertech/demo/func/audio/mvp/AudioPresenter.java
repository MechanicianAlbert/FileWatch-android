package com.albertech.demo.func.audio.mvp;


import com.albertech.demo.func.audio.AudioBean;
import com.albertech.demo.func.base.query.IBaseQueryContract;
import com.albertech.demo.func.base.query.impl.BaseQueryPresenter;


public class AudioPresenter extends BaseQueryPresenter<AudioBean> {


    @Override
    protected IBaseQueryContract.IBaseQueryModel<AudioBean> createModel() {
        return new AudioModel();
    }
}
