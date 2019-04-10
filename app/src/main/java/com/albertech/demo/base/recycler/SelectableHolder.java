package com.albertech.demo.base.recycler;

import android.support.annotation.NonNull;
import android.view.View;

public class SelectableHolder<Adapter extends SelectableRecyclerAdapter<? extends SelectableHolder, Bean>, Bean> extends BaseHolder<Adapter, Bean> {


    public SelectableHolder(Adapter adapter, @NonNull View itemView) {
        super(adapter, itemView);
    }


    protected boolean isSelecting() {
        return getAdapter().isSelecting();
    }

    protected boolean isSelected(int position) {
        return getAdapter().isSelected(position);
    }

}
