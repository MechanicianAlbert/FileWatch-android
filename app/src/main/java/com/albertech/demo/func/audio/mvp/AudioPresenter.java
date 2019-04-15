package com.albertech.demo.func.audio.mvp;


import com.albertech.demo.func.audio.AudioBean;
import com.albertech.demo.func.base.IFileContract;
import com.albertech.demo.func.base.impl.BaseFilePresenter;


public class AudioPresenter extends BaseFilePresenter<AudioBean> {


    @Override
    protected IFileContract.IFileModel<AudioBean> createModel() {
        return new AudioModel();
    }
}
