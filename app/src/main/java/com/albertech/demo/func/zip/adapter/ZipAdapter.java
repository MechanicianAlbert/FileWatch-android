package com.albertech.demo.func.zip.adapter;

import android.view.View;

import com.albertech.demo.R;
import com.albertech.demo.base.recycler.SelectableRecyclerAdapter;
import com.albertech.demo.func.zip.ZipBean;

public class ZipAdapter extends SelectableRecyclerAdapter<ZipHolder, ZipBean> {

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_file;
    }

    @Override
    protected ZipHolder getHolderByViewType(View itemView, int viewType) {
        return new ZipHolder(this, itemView);
    }

}
