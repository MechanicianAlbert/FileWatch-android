package com.albertech.demo.func.zip.mvp;

import android.content.Context;

import com.albertech.demo.crud.query.QueryCallback;
import com.albertech.demo.func.zip.ZipBean;
import com.albertech.demo.util.SortUtil;

import java.util.List;



public interface IZipContract {


    interface IZipModel {

        void init(IZipPresenter presenter);

        void release();

        void query(Context context);
    }


    interface IZipView {

        void onResult(String path, List<ZipBean> list);
    }


    interface IZipPresenter extends QueryCallback<ZipBean>, SortUtil.SortType {

        void init(Context context, IZipView view);

        void release();

        void load();

        void sortBy(int type);

        void refresh();
    }

}
