package com.albertech.demo.func.doc.mvp;


import com.albertech.demo.func.base.IFileContract;
import com.albertech.demo.func.base.impl.BaseFilePresenter;
import com.albertech.demo.func.doc.DocBean;


public class DocPresenter extends BaseFilePresenter<DocBean> {


    @Override
    protected IFileContract.IFileModel<DocBean> createModel() {
        return new DocModel();
    }

}
