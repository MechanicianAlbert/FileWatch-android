package com.albertech.demo.func.base.impl;


import com.albertech.demo.base.recycler.selectable.SelectableHolder;
import com.albertech.demo.base.recycler.selectable.SelectableRecyclerAdapter;
import com.albertech.demo.func.base.select.ISelectContract;


public abstract class BaseSelectionAdapter<Holder extends SelectableHolder<? extends SelectableRecyclerAdapter, Bean>, Bean>
        extends SelectableRecyclerAdapter<Holder, Bean>
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
