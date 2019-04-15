package com.albertech.demo.base.recycler.selectable;

public interface ISelectionListener {

    void onSelectingStatusChange(boolean isSelecting);

    void onSelectionCountChange(int count, boolean hasSelectedAll);
}
