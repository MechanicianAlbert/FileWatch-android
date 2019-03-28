package com.albertech.demo.base.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerAdapter<Bean> extends RecyclerView.Adapter<BaseHolder<BaseRecyclerAdapter<Bean>, Bean>>
        implements OnItemClickListener {

    private final List<Bean> DATA = new ArrayList<>();


    public void updateData(List<Bean> data) {
        DATA.clear();
        DATA.addAll(data);
        notifyDataSetChanged();
    }

    Bean getItem(int position) {
        return DATA.get(position);
    }


    @Override
    public final int getItemCount() {
        return DATA.size();
    }

    @NonNull
    @Override
    public final BaseHolder<BaseRecyclerAdapter<Bean>, Bean> onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(viewType, viewGroup, false);
        return getHolderByViewType(itemView, viewType);
    }

    @Override
    public final void onBindViewHolder(@NonNull BaseHolder<BaseRecyclerAdapter<Bean>, Bean> viewHolder, int position) {
        viewHolder.onBind(position, DATA.get(position));
    }

    @Override
    public boolean onItemClick(int position) {
        return false;
    }

    @Override
    public boolean onItemLongClick(int position) {
        return false;
    }


    protected abstract BaseHolder<BaseRecyclerAdapter<Bean>, Bean> getHolderByViewType(View itemView, int viewType);
}
