package com.albertech.demo.func.audio.adapter;

import android.support.annotation.NonNull;
import android.view.View;

import com.albertech.demo.R;
import com.albertech.demo.base.recycler.BaseHolder;
import com.albertech.demo.base.recycler.BaseRecyclerAdapter;
import com.albertech.demo.func.audio.AudioBean;
import com.albertech.demo.util.DateUtil;
import com.albertech.demo.util.SizeUtil;


public class AudioHolder extends BaseHolder<BaseRecyclerAdapter<AudioBean>, AudioBean> {

    public AudioHolder(AudioAdapter adapter, @NonNull View itemView) {
        super(adapter, itemView);
    }

    @Override
    protected void onBind(int position, AudioBean audioBean) {
        setText(R.id.tv_item_audio_name, audioBean.name);
        setText(R.id.tv_item_audio_size, SizeUtil.format(audioBean.size));
        setText(R.id.tv_item_audio_date, DateUtil.format(audioBean.date));
        setImage(R.id.iv_item_audio_icon, audioBean.icon);
    }
}
