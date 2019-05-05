package com.albertech.demo.func.base.impl;

import com.albertech.common.base.recycler.select.SelectHolder;
import com.albertech.common.base.recycler.select.SelectRecyclerAdapter;
import com.albertech.demo.func.base.select.ISelectContract;


public abstract class BaseSelectionAdapter<Holder extends SelectHolder<? extends SelectRecyclerAdapter, Bean>, Bean>
        extends SelectRecyclerAdapter<Holder, Bean>
        implements ISelectContract.ISelectModel<Bean> {

    private ISelectContract.ISelectView mView;

    public BaseSelectionAdapter(ISelectContract.ISelectView view) {
        mView = view;
    }


    @Override
    public void onSelectingStatusChange(boolean isSelecting) {
        if (mView != null) {
            mView.onSelectingStatusChange(isSelecting);
        }
    }

    @Override
    public void onSelectionCountChange(int count, boolean hasSelectedAll) {
        if (mView != null) {
            mView.onSelectionCountChange(count, hasSelectedAll);
        }
    }

}
