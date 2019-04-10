package com.albertech.demo.func.apk.adapter;

import android.view.View;

import com.albertech.demo.R;
import com.albertech.demo.base.recycler.BaseHolder;
import com.albertech.demo.base.recycler.BaseRecyclerAdapter;
import com.albertech.demo.func.apk.ApkBean;
import com.albertech.demo.func.doc.DocBean;

public class ApkAdapter extends BaseRecyclerAdapter<ApkBean> {

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_file;
    }

    @Override
    protected BaseHolder<BaseRecyclerAdapter<ApkBean>, ApkBean> getHolderByViewType(View itemView, int viewType) {
        return new ApkHolder(this, itemView);
    }
}
