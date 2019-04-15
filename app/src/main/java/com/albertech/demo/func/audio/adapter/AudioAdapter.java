package com.albertech.demo.func.audio.adapter;

import android.view.View;

import com.albertech.demo.R;
import com.albertech.demo.base.recycler.selectable.SelectableRecyclerAdapter;
import com.albertech.demo.func.audio.AudioBean;
import com.albertech.demo.func.base.impl.BaseSelectionAdapter;
import com.albertech.demo.func.base.select.ISelectContract;


public class AudioAdapter extends BaseSelectionAdapter<AudioHolder, AudioBean> {


    public AudioAdapter(ISelectContract.ISelectView view) {
        super(view);
    }


    @Override
    public int getItemViewType(int position) {
        return R.layout.item_file;
    }

    @Override
    protected AudioHolder getHolderByViewType(View itemView, int viewType) {
        return new AudioHolder(this, itemView);
    }
}
