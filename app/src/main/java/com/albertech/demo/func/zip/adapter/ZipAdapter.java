package com.albertech.demo.func.zip.adapter;

import android.view.View;

import com.albertech.demo.R;
import com.albertech.demo.base.recycler.selectable.SelectableRecyclerAdapter;
import com.albertech.demo.func.base.impl.BaseSelectionAdapter;
import com.albertech.demo.func.base.select.ISelectContract;
import com.albertech.demo.func.zip.ZipBean;

public class ZipAdapter extends BaseSelectionAdapter<ZipHolder, ZipBean> {


    public ZipAdapter(ISelectContract.ISelectView view) {
        super(view);
    }


    @Override
    public int getItemViewType(int position) {
        return R.layout.item_file;
    }

    @Override
    protected ZipHolder getHolderByViewType(View itemView, int viewType) {
        return new ZipHolder(this, itemView);
    }

}
