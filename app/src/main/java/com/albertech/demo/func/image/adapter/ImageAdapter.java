package com.albertech.demo.func.image.adapter;

import android.view.View;

import com.albertech.demo.R;
import com.albertech.demo.base.recycler.BaseHolder;
import com.albertech.demo.base.recycler.BaseRecyclerAdapter;
import com.albertech.demo.crud.query.image.ImageBean;



public class ImageAdapter extends BaseRecyclerAdapter<ImageBean> {

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_image_grid;
    }

    @Override
    protected BaseHolder<BaseRecyclerAdapter<ImageBean>, ImageBean> getHolderByViewType(View itemView, int viewType) {
        return new ImageHolder(this, itemView);
    }
}
