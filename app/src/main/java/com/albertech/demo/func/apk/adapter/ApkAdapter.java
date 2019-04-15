package com.albertech.demo.func.apk.adapter;

import android.view.View;

import com.albertech.demo.R;
import com.albertech.demo.func.apk.ApkBean;
import com.albertech.demo.func.base.impl.BaseSelectionAdapter;
import com.albertech.demo.func.base.select.ISelectContract;


public class ApkAdapter extends BaseSelectionAdapter<ApkHolder, ApkBean> {


    public ApkAdapter(ISelectContract.ISelectView presenter) {
        super(presenter);
    }


    @Override
    public int getItemViewType(int position) {
        return R.layout.item_file;
    }

    @Override
    protected ApkHolder getHolderByViewType(View itemView, int viewType) {
        return new ApkHolder(this, itemView);
    }
}
