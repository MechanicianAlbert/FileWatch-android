package com.albertech.demo.func.base.select;

import com.albertech.common.base.recycler.select.ISelect;
import com.albertech.common.base.recycler.select.ISelectListener;


public interface ISelectContract {

    interface ISelectModel<Bean> extends ISelect<Bean> {

    }


    interface ISelectView extends ISelectListener {

        void bindModel(ISelectModel model);
    }


    interface ISelectPresenter {

    }

}
