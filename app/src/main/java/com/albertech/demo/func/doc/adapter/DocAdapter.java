package com.albertech.demo.func.doc.adapter;

import android.view.View;

import com.albertech.demo.R;
import com.albertech.demo.base.recycler.BaseHolder;
import com.albertech.demo.base.recycler.BaseRecyclerAdapter;
import com.albertech.demo.func.doc.DocBean;

public class DocAdapter extends BaseRecyclerAdapter<DocBean> {

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_file;
    }

    @Override
    protected BaseHolder<BaseRecyclerAdapter<DocBean>, DocBean> getHolderByViewType(View itemView, int viewType) {
        return new DocHolder(this, itemView);
    }
}
