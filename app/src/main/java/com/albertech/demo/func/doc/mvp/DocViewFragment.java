package com.albertech.demo.func.doc.mvp;


import com.albertech.demo.func.base.query.IBaseQueryContract;
import com.albertech.demo.func.base.query.impl.BaseQueryViewFragment;
import com.albertech.demo.func.doc.DocBean;
import com.albertech.demo.func.doc.adapter.DocAdapter;


public class DocViewFragment extends BaseQueryViewFragment<DocAdapter, DocBean> {


    @Override
    protected IBaseQueryContract.IBaseQueryPresenter<DocBean> createPresenter() {
        return new DocPresenter();
    }

    @Override
    protected DocAdapter createAdapter() {
        return new DocAdapter();
    }

}
