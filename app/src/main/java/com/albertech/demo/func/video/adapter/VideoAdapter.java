package com.albertech.demo.func.video.adapter;

import android.view.View;

import com.albertech.demo.R;
import com.albertech.demo.base.recycler.BaseHolder;
import com.albertech.demo.base.recycler.BaseRecyclerAdapter;
import com.albertech.demo.crud.query.video.VideoBean;


public class VideoAdapter extends BaseRecyclerAdapter<VideoBean> {

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_video;
    }

    @Override
    protected BaseHolder<BaseRecyclerAdapter<VideoBean>, VideoBean> getHolderByViewType(View itemView, int viewType) {
        return new VideoHolder(this, itemView);
    }
}
