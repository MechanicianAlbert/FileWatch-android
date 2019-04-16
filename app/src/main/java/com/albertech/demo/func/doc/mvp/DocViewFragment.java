package com.albertech.demo.func.doc.mvp;


import com.albertech.demo.R;
import com.albertech.demo.func.base.IFileContract;
import com.albertech.demo.func.base.impl.BaseFileViewFragment;
import com.albertech.demo.func.doc.DocBean;
import com.albertech.demo.func.doc.adapter.DocAdapter;
import com.albertech.demo.util.Res;


public class DocViewFragment extends BaseFileViewFragment<DocAdapter, DocBean> {


    @Override
    public String getTitle() {
        return Res.string(R.string.str_category_doc);
    }

    @Override
    protected IFileContract.IFilePresenter<DocBean> createPresenter() {
        return new DocPresenter();
    }

    @Override
    protected DocAdapter createAdapter() {
        return new DocAdapter(this);
    }

}
