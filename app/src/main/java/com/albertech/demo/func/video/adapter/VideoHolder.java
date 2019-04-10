package com.albertech.demo.func.video.adapter;

import android.support.annotation.NonNull;
import android.view.View;

import com.albertech.demo.R;
import com.albertech.demo.base.recycler.SelectableHolder;
import com.albertech.demo.base.recycler.SelectableRecyclerAdapter;
import com.albertech.demo.func.video.VideoBean;
import com.albertech.demo.util.DurationUtil;
import com.albertech.demo.util.SizeUtil;


public class VideoHolder extends SelectableHolder<SelectableRecyclerAdapter<VideoHolder, VideoBean>, VideoBean> {

    public VideoHolder(SelectableRecyclerAdapter<VideoHolder, VideoBean> adapter, @NonNull View itemView) {
        super(adapter, itemView);
    }

    @Override
    protected void onBind(int position, VideoBean videoBean) {
        setText(R.id.tv_item_video_name, videoBean.name);
        setText(R.id.tv_item_video_size, SizeUtil.format(videoBean.size));
        setText(R.id.tv_item_video_duration, DurationUtil.format(videoBean.duration));
    }
}
