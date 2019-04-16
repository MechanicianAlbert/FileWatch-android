package com.albertech.demo.func.hierarchy.adapter;

import android.view.View;

import com.albertech.demo.R;
import com.albertech.demo.base.recycler.selectable.SelectableRecyclerAdapter;
import com.albertech.demo.func.base.impl.BaseSelectionAdapter;
import com.albertech.demo.func.base.select.ISelectContract;
import com.albertech.demo.func.hierarchy.HierarchyBean;


public class HierarchyAdapter extends BaseSelectionAdapter<HierarchyHolder, HierarchyBean> {


    public HierarchyAdapter(ISelectContract.ISelectView view) {
        super(view);
    }


    @Override
    public int getItemViewType(int position) {
        return R.layout.item_file;
    }

    @Override
    protected HierarchyHolder getHolderByViewType(View itemView, int viewType) {
        return new HierarchyHolder(this, itemView);
    }
}
