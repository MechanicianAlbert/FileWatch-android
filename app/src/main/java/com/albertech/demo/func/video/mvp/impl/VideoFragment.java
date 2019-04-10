package com.albertech.demo.func.video.mvp.impl;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.albertech.demo.R;
import com.albertech.demo.base.fragment.TitleFragment;
import com.albertech.demo.func.video.VideoBean;
import com.albertech.demo.func.video.adapter.VideoAdapter;
import com.albertech.demo.func.video.mvp.IVideoContract;
import com.albertech.demo.util.Res;

import java.util.List;


public class VideoFragment extends TitleFragment implements IVideoContract.IVideoView {

    private final VideoAdapter ADAPTER = new VideoAdapter() {
        @Override
        public void onItemClickNotSelecting(int position, VideoBean imageBean) {

        }
    };


    private RecyclerView mRvVideo;

    private IVideoContract.IVideoPresenter mPresenter;


    @Override
    public String getTitle() {
        return Res.string(R.string.str_category_video);
    }

    @Override
    protected int layoutRese() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initView(View root) {
        mRvVideo = root.findViewById(R.id.rv_video);
        mRvVideo.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void initData() {
        mRvVideo.setAdapter(ADAPTER);

        mPresenter = new VideoPresenter();
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
    public void onResult(String path, List<VideoBean> list) {
        ADAPTER.updateData(list);
    }

}
