package com.albertech.demo.func.video.adapter;

import android.view.View;

import com.albertech.demo.R;
import com.albertech.demo.base.recycler.SelectableRecyclerAdapter;
import com.albertech.demo.func.video.VideoBean;


public class VideoAdapter extends SelectableRecyclerAdapter<VideoHolder, VideoBean> {

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_video;
    }

    @Override
    protected VideoHolder getHolderByViewType(View itemView, int viewType) {
        return new VideoHolder(this, itemView);
    }
}
