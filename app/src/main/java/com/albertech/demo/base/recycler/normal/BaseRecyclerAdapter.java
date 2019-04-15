package com.albertech.demo.base.recycler.normal;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;



public abstract class BaseRecyclerAdapter<Holder extends BaseHolder<? extends BaseRecyclerAdapter, Bean>, Bean> extends RecyclerView.Adapter<Holder>
        implements OnItemClickListener<Bean> {

    private final List<Bean> DATA = new ArrayList<>();


    public void updateData(List<Bean> data) {
        DATA.clear();
        DATA.addAll(data);
        notifyDataSetChanged();
    }

    protected Bean getItem(int position) {
        return DATA.get(position);
    }


    @Override
    public final int getItemCount() {
        return DATA.size();
    }

    @NonNull
    @Override
    public final Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(viewType, viewGroup, false);
        return getHolderByViewType(itemView, viewType);
    }

    @Override
    public final void onBindViewHolder(@NonNull Holder viewHolder, int position) {
        viewHolder.onBind(position, DATA.get(position));
    }

    @Override
    public boolean onItemClick(int position, Bean bean) {
        return false;
    }

    @Override
    public boolean onItemLongClick(int position, Bean bean) {
        return false;
    }


    protected abstract Holder getHolderByViewType(View itemView, int viewType);
}
