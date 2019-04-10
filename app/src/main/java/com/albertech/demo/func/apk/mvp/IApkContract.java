package com.albertech.demo.func.apk.mvp;

import android.content.Context;

import com.albertech.demo.crud.query.QueryCallback;
import com.albertech.demo.func.apk.ApkBean;
import com.albertech.demo.func.doc.DocBean;
import com.albertech.demo.util.SortUtil;

import java.util.List;

public interface IApkContract {


    interface IApkModel {

        void init(IApkPresenter presenter);

        void release();

        void query(Context context);
    }


    interface IApkView {

        void onResult(String path, List<ApkBean> list);
    }


    interface IApkPresenter extends QueryCallback<ApkBean>, SortUtil.SortType {

        void init(Context context, IApkView view);

        void release();

        void load();

        void sortBy(int type);

        void refresh();
    }

}
