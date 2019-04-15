package com.albertech.demo.base.recycler.selectable;

import android.support.annotation.NonNull;
import android.view.View;

import com.albertech.demo.base.recycler.normal.BaseHolder;

public class SelectableHolder<Adapter extends SelectableRecyclerAdapter<? extends SelectableHolder, Bean>, Bean> extends BaseHolder<Adapter, Bean> {


    public SelectableHolder(Adapter adapter, @NonNull View itemView) {
        super(adapter, itemView);
    }


    protected boolean isSelecting() {
        return getAdapter().isSelecting();
    }

    protected boolean isSelected(int position) {
        return getAdapter().isItemSelected(position);
    }

}
