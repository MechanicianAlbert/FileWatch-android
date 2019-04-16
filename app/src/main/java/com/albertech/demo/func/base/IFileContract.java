package com.albertech.demo.func.base;

import android.content.Context;

import com.albertech.demo.func.base.query.IBaseQueryContract;
import com.albertech.demo.func.base.select.ISelectContract;

public interface IFileContract {

    interface IFileModel<Bean> extends IBaseQueryContract.IBaseQueryModel<Bean> {

        void init(IFilePresenter<Bean> presenter);
    }


    interface IFileView<Bean> extends IBaseQueryContract.IBaseQueryView<Bean>, ISelectContract.ISelectView {

    }


    interface IFilePresenter<Bean> extends IBaseQueryContract.IBaseQueryPresenter<Bean> {

        void init(Context context, IFileView<Bean> view);

        void release();
    }
}
