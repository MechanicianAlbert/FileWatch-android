package com.albertech.demo.func.video.adapter;

import android.support.annotation.NonNull;
import android.view.View;

import com.albertech.common.base.recycler.select.SelectHolder;
import com.albertech.common.base.recycler.select.SelectRecyclerAdapter;
import com.albertech.demo.R;
import com.albertech.demo.func.video.VideoBean;
import com.albertech.demo.util.DurationUtil;
import com.albertech.demo.util.SizeUtil;


public class VideoHolder extends SelectHolder<SelectRecyclerAdapter<VideoHolder, VideoBean>, VideoBean> {

    public VideoHolder(SelectRecyclerAdapter<VideoHolder, VideoBean> adapter, @NonNull View itemView) {
        super(adapter, itemView);
    }

    @Override
    protected void onBind(int position, VideoBean videoBean) {
        setText(R.id.tv_item_video_name, videoBean.name);
        setText(R.id.tv_item_video_size, SizeUtil.format(videoBean.size));
        setText(R.id.tv_item_video_duration, DurationUtil.format(videoBean.duration));
    }

}
