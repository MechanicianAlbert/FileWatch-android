package com.albertech.demo.func.audio.adapter;

import android.view.View;

import com.albertech.demo.R;
import com.albertech.demo.base.recycler.SelectableRecyclerAdapter;
import com.albertech.demo.func.audio.AudioBean;



public class AudioAdapter extends SelectableRecyclerAdapter<AudioHolder, AudioBean> {

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_file;
    }

    @Override
    protected AudioHolder getHolderByViewType(View itemView, int viewType) {
        return new AudioHolder(this, itemView);
    }
}
