package com.albertech.demo.func.image.mvp;

import android.content.Context;

import com.albertech.demo.crud.query.QueryCallback;
import com.albertech.demo.func.image.ImageBean;
import com.albertech.demo.util.SortUtil;

import java.util.List;



public interface IImageContract {

    interface IImageModel {

        void init(IImagePresenter presenter);

        void release();

        void query(Context context);

    }


    interface IImageView {

        void onResult(String path, List<ImageBean> list);

    }


    interface IImagePresenter extends QueryCallback<ImageBean>, SortUtil.SortType {

        void init(Context context, IImageView view);

        void release();

        void load();

        void sortBy(int type);

        void refresh();
    }
}
