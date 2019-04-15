package com.albertech.demo.func.doc.mvp;


import com.albertech.demo.func.base.IFileContract;
import com.albertech.demo.func.base.impl.BaseFileViewFragment;
import com.albertech.demo.func.doc.DocBean;
import com.albertech.demo.func.doc.adapter.DocAdapter;


public class DocViewFragment extends BaseFileViewFragment<DocAdapter, DocBean> {


    @Override
    protected IFileContract.IFilePresenter<DocBean> createPresenter() {
        return new DocPresenter();
    }

    @Override
    protected DocAdapter createAdapter() {
        return new DocAdapter(this);
    }

}
