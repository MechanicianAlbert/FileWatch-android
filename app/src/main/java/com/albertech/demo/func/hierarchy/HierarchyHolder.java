package com.albertech.demo.func.hierarchy;

import android.support.annotation.NonNull;
import android.view.View;

import com.albertech.demo.base.recycler.BaseHolder;
import com.albertech.demo.base.recycler.BaseRecyclerAdapter;


public class HierarchyHolder extends BaseHolder<BaseRecyclerAdapter<HierarchyBean>, HierarchyBean> {


    public HierarchyHolder(BaseRecyclerAdapter<HierarchyBean> adapter, @NonNull View itemView) {
        super(adapter, itemView);
    }

    @Override
    protected void onBind(int position, HierarchyBean hierarchyBean) {

    }
}
