package com.albertech.demo.func.zip.mvp;


import com.albertech.demo.func.base.IFileContract;
import com.albertech.demo.func.base.impl.BaseFileViewFragment;
import com.albertech.demo.func.zip.ZipBean;
import com.albertech.demo.func.zip.adapter.ZipAdapter;


public class ZipViewFragment extends BaseFileViewFragment<ZipAdapter, ZipBean> {


    @Override
    protected IFileContract.IFilePresenter<ZipBean> createPresenter() {
        return new ZipPresenter();
    }

    @Override
    protected ZipAdapter createAdapter() {
        return new ZipAdapter(this);
    }

}
