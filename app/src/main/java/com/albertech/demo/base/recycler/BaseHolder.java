package com.albertech.demo.base.recycler;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class BaseHolder<Adapter extends BaseRecyclerAdapter<? extends BaseHolder, Bean>, Bean> extends RecyclerView.ViewHolder
        implements View.OnClickListener, View.OnLongClickListener, OnItemClickListener<Bean> {

    private final SparseArray<View> VIEWS = new SparseArray<>();

    private Adapter mAdapter;
    private View mItemView;


    public BaseHolder(Adapter adapter, @NonNull View itemView) {
        super(itemView);
        mAdapter = adapter;
        mItemView = itemView;
        mItemView.setOnClickListener(this);
        mItemView.setOnLongClickListener(this);
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

    protected final Adapter getAdapter() {
        return mAdapter;
    }

    protected final void setText(int id, String text) {
        TextView tv = $(id);
        if (tv != null) {
            tv.setText(text);
        }
    }

    protected final void setImage(int id, int drawableRes) {
        ImageView iv = $(id);
        if (iv != null) {
            iv.setImageResource(drawableRes);
        }
    }

    protected final Bean getItem(int position) {
        return mAdapter.getItem(position);
    }


    protected void onCreate() {

    }

    protected void onBind(int position, Bean bean) {

    }

    @Override
    public boolean onItemClick(int position, Bean bean) {
        return false;
    }

    @Override
    public boolean onItemLongClick(int position, Bean bean) {
        return false;
    }

    @Override
    public final void onClick(View view) {
        int position = getAdapterPosition();
        Bean bean = getItem(position);
        if (!onItemClick(position, bean)) {
            mAdapter.onItemClick(position, bean);
        }
    }

    @Override
    public final boolean onLongClick(View view) {
        int position = getAdapterPosition();
        Bean bean = getItem(position);
        if (!onItemLongClick(position, bean)) {
            mAdapter.onItemLongClick(position, bean);
        }
        return false;
    }

}
