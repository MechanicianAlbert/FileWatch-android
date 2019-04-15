package com.albertech.demo.base.recycler.selectable;


import java.util.List;


public interface ISelectableAdapter<Bean> {

    boolean isSelecting();

    void startSelecting();

    void stopSelecting();

    void selectAll();

    void clearSelection();

    void updateItemSelection(int position, boolean selected);

    boolean isItemSelected(int position);

    int getSelectionCount();

    List<Bean> getSelections();

}
