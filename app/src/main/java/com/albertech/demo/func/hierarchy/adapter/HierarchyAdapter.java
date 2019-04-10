package com.albertech.demo.func.hierarchy.adapter;

import android.view.View;

import com.albertech.demo.R;
import com.albertech.demo.base.recycler.SelectableRecyclerAdapter;
import com.albertech.demo.func.hierarchy.HierarchyBean;


public class HierarchyAdapter extends SelectableRecyclerAdapter<HierarchyHolder, HierarchyBean> {

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_file;
    }

    @Override
    protected HierarchyHolder getHolderByViewType(View itemView, int viewType) {
        return new HierarchyHolder(this, itemView);
    }
}
