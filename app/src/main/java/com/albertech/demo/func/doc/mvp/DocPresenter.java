package com.albertech.demo.func.doc.mvp;


import com.albertech.demo.func.base.query.IBaseQueryContract;
import com.albertech.demo.func.base.query.impl.BaseQueryPresenter;
import com.albertech.demo.func.doc.DocBean;


public class DocPresenter extends BaseQueryPresenter<DocBean> {


    @Override
    protected IBaseQueryContract.IBaseQueryModel<DocBean> createModel() {
        return new DocModel();
    }

}
