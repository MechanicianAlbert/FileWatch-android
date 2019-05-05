package com.albertech.demo.func.doc.adapter;

import android.view.View;

import com.albertech.demo.R;
import com.albertech.demo.func.base.impl.BaseSelectionAdapter;
import com.albertech.demo.func.base.select.ISelectContract;
import com.albertech.demo.func.doc.DocBean;

public class DocAdapter extends BaseSelectionAdapter<DocHolder, DocBean> {


    public DocAdapter(ISelectContract.ISelectView view) {
        super(view);
    }


    @Override
    public int getItemViewType(int position) {
        return R.layout.item_file;
    }

    @Override
    protected DocHolder getHolderByViewType(View itemView, int viewType) {
        return new DocHolder(this, itemView);
    }
}
