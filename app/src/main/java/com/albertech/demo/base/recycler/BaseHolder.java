package com.albertech.demo.base.recycler;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

public class BaseHolder<Adapter extends BaseRecyclerAdapter<Bean>, Bean> extends RecyclerView.ViewHolder
        implements View.OnClickListener, View.OnLongClickListener, OnItemClickListener {

    private final SparseArray<View> VIEWS = new SparseArray<>();

    private Adapter mAdapter;
    private View mItemView;


    public BaseHolder(Adapter adapter, @NonNull View itemView) {
        super(itemView);
        mItemView = itemView;
    }


    protected final <V extends View> V $(@IdRes int id) {
        V v;
        if ((v = (V) VIEWS.get(id)) == null) {
            v = mItemView.findViewById(id);
            VIEWS.put(id, v);
        }
        return v;
    }

    protected final Context getContext() {
        return mItemView.getContext();
    }

    protected final View getItemView() {
        return mItemView;
    }

    private final Bean getItem(int position) {
        return mAdapter.getItem(position);
    }


    protected void onCreate() {

    }

    protected void onBind(int position, Bean bean) {

    }

    @Override
    public boolean onItemClick(int position) {
        return false;
    }

    @Override
    public boolean onItemLongClick(int position) {
        return false;
    }

    @Override
    public final void onClick(View view) {
        int position = getAdapterPosition();
        if (onItemClick(position)) {
            mAdapter.onItemClick(position);
        }
    }

    @Override
    public final boolean onLongClick(View view) {
        int position = getAdapterPosition();
        if (onItemLongClick(position)) {
            mAdapter.onItemLongClick(position);
        }
        return false;
    }
}
