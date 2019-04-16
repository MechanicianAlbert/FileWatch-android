package com.albertech.demo.func.video.mvp;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.albertech.demo.R;
import com.albertech.demo.func.base.IFileContract;
import com.albertech.demo.func.base.impl.BaseFileViewFragment;
import com.albertech.demo.func.video.VideoBean;
import com.albertech.demo.func.video.adapter.VideoAdapter;
import com.albertech.demo.util.Res;


public class VideoViewFragment extends BaseFileViewFragment<VideoAdapter, VideoBean> {


    @Override
    public String getTitle() {
        return Res.string(R.string.str_category_video);
    }

    @Override
    protected IFileContract.IFilePresenter<VideoBean> createPresenter() {
        return new VideoPresenter();
    }

    @Override
    protected VideoAdapter createAdapter() {
        return new VideoAdapter(this) {
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
