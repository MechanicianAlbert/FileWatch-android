package com.albertech.demo.func.audio.mvp;

import android.content.Context;

import com.albertech.demo.crud.query.QueryCallback;
import com.albertech.demo.func.audio.AudioBean;
import com.albertech.demo.util.SortUtil;

import java.util.List;



public interface IAudioContract {

    interface IAudioModel {

        void init(IAudioPresenter presenter);

        void release();

        void query(Context context);
    }


    interface IAudioView {

        void onResult(String path, List<AudioBean> list);
    }


    interface IAudioPresenter extends QueryCallback<AudioBean>, SortUtil.SortType {

        void init(Context context, IAudioView view);

        void release();

        void load();

        void sortBy(int type);

        void refresh();
    }

}
