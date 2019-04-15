package com.albertech.demo.func.video.mvp;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.albertech.demo.func.base.query.IBaseQueryContract;
import com.albertech.demo.func.base.query.impl.BaseQueryViewFragment;
import com.albertech.demo.func.video.VideoBean;
import com.albertech.demo.func.video.adapter.VideoAdapter;


public class VideoViewFragment extends BaseQueryViewFragment<VideoAdapter, VideoBean> {


    @Override
    protected IBaseQueryContract.IBaseQueryPresenter<VideoBean> createPresenter() {
        return new VideoPresenter();
    }

    @Override
    protected VideoAdapter createAdapter() {
        return new VideoAdapter() {
            @Override
            public void onItemClickNotSelecting(int position, VideoBean imageBean) {

            }
        };
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
    }

}
