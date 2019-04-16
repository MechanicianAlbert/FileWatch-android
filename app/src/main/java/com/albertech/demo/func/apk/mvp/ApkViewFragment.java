package com.albertech.demo.func.apk.mvp;

import com.albertech.demo.R;
import com.albertech.demo.func.apk.ApkBean;
import com.albertech.demo.func.apk.adapter.ApkAdapter;
import com.albertech.demo.func.base.IFileContract;
import com.albertech.demo.func.base.impl.BaseFileViewFragment;
import com.albertech.demo.util.Res;


public class ApkViewFragment extends BaseFileViewFragment<ApkAdapter, ApkBean> {


    @Override
    public String getTitle() {
        return Res.string(R.string.str_category_apk);
    }

    @Override
    protected IFileContract.IFilePresenter<ApkBean> createPresenter() {
        return new ApkPresenter();
    }

    @Override
    protected ApkAdapter createAdapter() {
        return new ApkAdapter(this);
    }
}
