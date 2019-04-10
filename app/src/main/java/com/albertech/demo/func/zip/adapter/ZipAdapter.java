package com.albertech.demo.func.zip.adapter;

import android.view.View;

import com.albertech.demo.R;
import com.albertech.demo.base.recycler.BaseHolder;
import com.albertech.demo.base.recycler.BaseRecyclerAdapter;
import com.albertech.demo.func.zip.ZipBean;

public class ZipAdapter extends BaseRecyclerAdapter<ZipBean> {

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_file;
    }

    @Override
    protected BaseHolder<BaseRecyclerAdapter<ZipBean>, ZipBean> getHolderByViewType(View itemView, int viewType) {
        return new ZipHolder(this, itemView);
    }

}
