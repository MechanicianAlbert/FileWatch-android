package com.albertech.demo.func.audio.adapter;

import android.view.View;

import com.albertech.demo.R;
import com.albertech.demo.base.recycler.BaseHolder;
import com.albertech.demo.base.recycler.BaseRecyclerAdapter;
import com.albertech.demo.func.audio.AudioBean;



public class AudioAdapter extends BaseRecyclerAdapter<AudioBean> {

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_audio;
    }

    @Override
    protected BaseHolder<BaseRecyclerAdapter<AudioBean>, AudioBean> getHolderByViewType(View itemView, int viewType) {
        return new AudioHolder(this, itemView);
    }
}
