package com.albertech.demo.func.video.adapter;

import android.view.View;

import com.albertech.demo.R;
import com.albertech.demo.func.base.impl.BaseSelectionAdapter;
import com.albertech.demo.func.base.select.ISelectContract;
import com.albertech.demo.func.video.VideoBean;


public class VideoAdapter extends BaseSelectionAdapter<VideoHolder, VideoBean> {


    public VideoAdapter(ISelectContract.ISelectView view) {
        super(view);
    }


    @Override
    public int getItemViewType(int position) {
        return R.layout.item_video;
    }

    @Override
    protected VideoHolder getHolderByViewType(View itemView, int viewType) {
        return new VideoHolder(this, itemView);
    }
}
