package com.albertech.demo.func.doc.mvp;

import android.content.Context;

import com.albertech.demo.crud.query.QueryCallback;
import com.albertech.demo.func.doc.DocBean;
import com.albertech.demo.util.SortUtil;

import java.util.List;

public interface IDocContract {


    interface IDocModel {

        void init(IDocPresenter presenter);

        void release();

        void query(Context context);
    }


    interface IDocView {

        void onResult(String path, List<DocBean> list);
    }


    interface IDocPresenter extends QueryCallback<DocBean>, SortUtil.SortType {

        void init(Context context, IDocView view);

        void release();

        void load();

        void sortBy(int type);

        void refresh();
    }

}
