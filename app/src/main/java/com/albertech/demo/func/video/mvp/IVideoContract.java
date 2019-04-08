package com.albertech.demo.func.video.mvp;

import android.content.Context;

import com.albertech.demo.crud.query.QueryCallback;
import com.albertech.demo.func.video.VideoBean;
import com.albertech.demo.util.SortUtil;

import java.util.List;



public interface IVideoContract {

    interface IVideoModel {

        void init(IVideoPresenter presenter);

        void release();

        void query(Context context);
    }


    interface IVideoView {

        void onResult(String path, List<VideoBean> list);
    }


    interface IVideoPresenter extends QueryCallback<VideoBean>, SortUtil.SortType {

        void init(Context context, IVideoView view);

        void release();

        void load();

        void sortBy(int type);

        void refresh();
    }
}
