package com.albertech.demo.func.hierarchy;

import android.view.View;

import com.albertech.demo.R;
import com.albertech.demo.base.recycler.BaseHolder;
import com.albertech.demo.base.recycler.BaseRecyclerAdapter;


public class HierarchyAdapter extends BaseRecyclerAdapter<HierarchyBean> {

    HierarchyAdapter() {

    }


    @Override
    public int getItemViewType(int position) {
        return R.layout.item_category;
    }

    @Override
    protected BaseHolder<BaseRecyclerAdapter<HierarchyBean>, HierarchyBean> getHolderByViewType(View itemView, int viewType) {
        return new HierarchyHolder(this, itemView);
    }
}
