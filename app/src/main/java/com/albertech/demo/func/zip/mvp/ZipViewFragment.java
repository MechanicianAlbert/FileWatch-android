package com.albertech.demo.func.zip.mvp;


import com.albertech.demo.R;
import com.albertech.demo.func.base.IFileContract;
import com.albertech.demo.func.base.impl.BaseFileViewFragment;
import com.albertech.demo.func.zip.ZipBean;
import com.albertech.demo.func.zip.adapter.ZipAdapter;
import com.albertech.demo.util.Res;


public class ZipViewFragment extends BaseFileViewFragment<ZipAdapter, ZipBean> {


    @Override
    public String getTitle() {
        return Res.string(R.string.str_category_zip);
    }

    @Override
    protected IFileContract.IFilePresenter<ZipBean> createPresenter() {
        return new ZipPresenter();
    }

    @Override
    protected ZipAdapter createAdapter() {
        return new ZipAdapter(this);
    }

}
