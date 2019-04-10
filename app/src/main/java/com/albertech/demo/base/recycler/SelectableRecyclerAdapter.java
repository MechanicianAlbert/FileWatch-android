package com.albertech.demo.base.recycler;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



public abstract class SelectableRecyclerAdapter<Holder extends SelectableHolder<? extends SelectableRecyclerAdapter, Bean>, Bean> extends BaseRecyclerAdapter<Holder, Bean> {

    private final Set<Integer> SELECTED_POSITIONS = new HashSet<>();
    private final List<Integer> ALL_POSITIONS = new ArrayList<>();


    private boolean mIsSelecting;


    public SelectableRecyclerAdapter() {
        for (int i = 0; i < getItemCount(); i++) {
            ALL_POSITIONS.add(i);
        }
    }


    private void startSelecting() {
        mIsSelecting = true;
    }

    private void stopSelecting() {
        mIsSelecting = false;
        notifyDataSetChanged();
    }

    private void updateSelection(int position, boolean selected) {
        if (mIsSelecting) {
            if (selected) {
                SELECTED_POSITIONS.add(position);
            } else {
                SELECTED_POSITIONS.remove(position);
            }
            notifyDataSetChanged();
        }
    }


    protected boolean isSelecting() {
        return mIsSelecting;
    }

    protected boolean isSelected(int position) {
        return SELECTED_POSITIONS.contains(position);
    }

    protected void onItemClickNotSelecting(int position, Bean bean) {

    }


    public void selectAll() {
        if (mIsSelecting) {
            SELECTED_POSITIONS.addAll(ALL_POSITIONS);
            notifyDataSetChanged();
        }
    }

    public void clearSelect() {
        if (mIsSelecting) {
            SELECTED_POSITIONS.clear();
            notifyDataSetChanged();
        }
    }

    public int getSelectionCount() {
        return SELECTED_POSITIONS.size();
    }

    public List<Bean> getSelections() {
        List<Bean> selections = new ArrayList<>();
        for (int selectedPosition : SELECTED_POSITIONS) {
            selections.add(getItem(selectedPosition));
        }
        return selections;
    }

    public void endSelecting() {
        stopSelecting();
    }


    @Override
    public final boolean onItemClick(int position, Bean bean) {
        if (mIsSelecting) {
            updateSelection(position, !isSelected(position));
        } else {
            onItemClickNotSelecting(position, bean);
        }
        return super.onItemClick(position, bean);
    }

    @Override
    public final boolean onItemLongClick(int position, Bean bean) {
        if (!mIsSelecting) {
            startSelecting();
            updateSelection(position, true);
        }
        return super.onItemLongClick(position, bean);
    }

}
