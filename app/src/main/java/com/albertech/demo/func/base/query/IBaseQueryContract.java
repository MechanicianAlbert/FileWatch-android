package com.albertech.demo.func.base.query;

import android.content.Context;

import com.albertech.demo.base.bean.BaseFileBean;
import com.albertech.demo.crud.query.QueryCallback;
import com.albertech.demo.util.SortUtil;

import java.util.List;

public interface IBaseQueryContract<Bean extends BaseFileBean> {


    interface IBaseQueryModel<Bean> {

        void init(IBaseQueryPresenter<Bean> presenter);

        void release();

        void query(Context context);
    }


    interface IBaseQueryView<Bean> {

        void onResult(String path, List<Bean> list);
    }


    interface IBaseQueryPresenter<Bean> extends QueryCallback<Bean>, SortUtil.SortType {

        void init(Context context, IBaseQueryView view);

        void release();

        void load();

        void sortBy(int type);

        void refresh();
    }

}
