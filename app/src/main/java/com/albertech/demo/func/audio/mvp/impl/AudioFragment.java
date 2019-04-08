package com.albertech.demo.func.audio.mvp.impl;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.albertech.demo.R;
import com.albertech.demo.base.fragment.TitleFragment;
import com.albertech.demo.func.audio.AudioBean;
import com.albertech.demo.func.audio.adapter.AudioAdapter;
import com.albertech.demo.func.audio.mvp.IAudioContract;
import com.albertech.demo.util.Res;

import java.util.List;

public class AudioFragment extends TitleFragment implements IAudioContract.IAudioView {

    private final AudioAdapter ADAPTER = new AudioAdapter();


    private RecyclerView mRvHierarchy;

    private IAudioContract.IAudioPresenter mPresenter;


    @Override
    public String getTitle() {
        return Res.string(R.string.str_title_hierarchy);
    }

    @Override
    protected int layoutRese() {
        return R.layout.fragment_audio;
    }

    @Override
    protected void initView(View root) {
        mRvHierarchy = root.findViewById(R.id.rv_audio);
        mRvHierarchy.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void initData() {
        mRvHierarchy.setAdapter(ADAPTER);

        mPresenter = new AudioPresenter();
        mPresenter.init(getContext(), this);
        mPresenter.load();
    }

    @Override
    protected void release() {
        if (mPresenter != null) {
            mPresenter.release();
            mPresenter = null;
        }
    }

    @Override
    public void onResult(String path, List<AudioBean> list) {
        ADAPTER.updateData(list);
    }

}
