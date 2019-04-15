package com.albertech.demo.func.audio.mvp;


import com.albertech.demo.func.audio.AudioBean;
import com.albertech.demo.func.audio.adapter.AudioAdapter;
import com.albertech.demo.func.base.query.IBaseQueryContract;
import com.albertech.demo.func.base.query.impl.BaseQueryViewFragment;


public class AudioViewFragment extends BaseQueryViewFragment<AudioAdapter, AudioBean> {


    @Override
    protected IBaseQueryContract.IBaseQueryPresenter<AudioBean> createPresenter() {
        return new AudioPresenter();
    }

    @Override
    protected AudioAdapter createAdapter() {
        return new AudioAdapter();
    }

}
