package com.albertech.demo.func.base.query;

import android.content.Context;

import com.albertech.demo.base.bean.BaseFileBean;
import com.albertech.demo.util.SortUtil;

import java.util.List;

public interface IBaseQueryContract<Bean extends BaseFileBean> {


    interface IBaseQueryModel<Bean> {



        void release();

        void query(Context context);
    }


    interface IBaseQueryView<Bean> {

        void onResult(String path, List<Bean> list);
    }


    interface IBaseQueryPresenter<Bean> extends SortUtil.SortType {

        void load();

        void onResult(String path, List<Bean> list);

        void sortBy(int type);

        void refresh();
    }

}
