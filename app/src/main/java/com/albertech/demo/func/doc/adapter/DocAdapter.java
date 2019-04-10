package com.albertech.demo.func.doc.adapter;

import android.view.View;

import com.albertech.demo.R;
import com.albertech.demo.base.recycler.SelectableRecyclerAdapter;
import com.albertech.demo.func.doc.DocBean;

public class DocAdapter extends SelectableRecyclerAdapter<DocHolder, DocBean> {

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_file;
    }

    @Override
    protected DocHolder getHolderByViewType(View itemView, int viewType) {
        return new DocHolder(this, itemView);
    }
}
