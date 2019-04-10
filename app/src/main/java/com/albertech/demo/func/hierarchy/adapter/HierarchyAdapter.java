package com.albertech.demo.func.hierarchy.adapter;

import android.view.View;

import com.albertech.demo.R;
import com.albertech.demo.base.recycler.BaseHolder;
import com.albertech.demo.base.recycler.BaseRecyclerAdapter;
import com.albertech.demo.func.hierarchy.HierarchyBean;


public class HierarchyAdapter extends BaseRecyclerAdapter<HierarchyBean> {

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_file;
    }

    @Override
    protected BaseHolder<BaseRecyclerAdapter<HierarchyBean>, HierarchyBean> getHolderByViewType(View itemView, int viewType) {
        return new HierarchyHolder(this, itemView);
    }
}
