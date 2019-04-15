package com.albertech.demo.func.image.adapter;

import android.view.View;

import com.albertech.demo.R;
import com.albertech.demo.base.recycler.selectable.SelectableRecyclerAdapter;
import com.albertech.demo.func.base.impl.BaseSelectionAdapter;
import com.albertech.demo.func.base.select.ISelectContract;
import com.albertech.demo.func.image.ImageBean;



public class ImageAdapter extends BaseSelectionAdapter<ImageHolder, ImageBean> {


    public ImageAdapter(ISelectContract.ISelectView view) {
        super(view);
    }


    @Override
    public int getItemViewType(int position) {
        return R.layout.item_image;
    }

    @Override
    protected ImageHolder getHolderByViewType(View itemView, int viewType) {
        return new ImageHolder(this, itemView);
    }
}
