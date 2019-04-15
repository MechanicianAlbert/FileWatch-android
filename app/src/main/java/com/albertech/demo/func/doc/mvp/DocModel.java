package com.albertech.demo.func.doc.mvp;


import com.albertech.demo.func.base.impl.BaseFileModel;
import com.albertech.demo.func.doc.DocBean;


public class DocModel extends BaseFileModel<DocBean> {


    @Override
    public int type() {
        return DOC;
    }

    @Override
    public boolean recursive() {
        return true;
    }

    @Override
    protected DocBean createFileBean() {
        return new DocBean();
    }

}
