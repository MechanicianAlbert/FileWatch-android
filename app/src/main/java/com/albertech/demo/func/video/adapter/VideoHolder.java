package com.albertech.demo.func.video.adapter;

import android.support.annotation.NonNull;
import android.view.View;

import com.albertech.demo.base.recycler.BaseHolder;
import com.albertech.demo.base.recycler.BaseRecyclerAdapter;
import com.albertech.demo.crud.query.video.VideoBean;


public class VideoHolder extends BaseHolder<BaseRecyclerAdapter<VideoBean>, VideoBean> {

    public VideoHolder(BaseRecyclerAdapter<VideoBean> adapter, @NonNull View itemView) {
        super(adapter, itemView);
    }

    @Override
    protected void onBind(int position, VideoBean videoBean) {

    }
}
