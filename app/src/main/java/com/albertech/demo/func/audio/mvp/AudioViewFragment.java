package com.albertech.demo.func.audio.mvp;


import com.albertech.demo.func.audio.AudioBean;
import com.albertech.demo.func.audio.adapter.AudioAdapter;
import com.albertech.demo.func.base.IFileContract;
import com.albertech.demo.func.base.impl.BaseFileViewFragment;


public class AudioViewFragment extends BaseFileViewFragment<AudioAdapter, AudioBean> {


    @Override
    protected IFileContract.IFilePresenter<AudioBean> createPresenter() {
        return new AudioPresenter();
    }

    @Override
    protected AudioAdapter createAdapter() {
        return new AudioAdapter(this);
    }

}
