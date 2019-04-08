package com.albertech.demo.func.video.mvp.impl;

import android.content.Context;
import android.os.Handler;

import com.albertech.demo.func.video.VideoBean;
import com.albertech.demo.func.video.mvp.IVideoContract;
import com.albertech.demo.util.SortUtil;

import java.lang.ref.WeakReference;
import java.util.List;



public class VideoPresenter extends Handler implements IVideoContract.IVideoPresenter {

    private final Runnable NOTIFIER = new Runnable() {
        @Override
        public void run() {
            final IVideoContract.IVideoView view;
            if (mViewReference != null
                    && (view = mViewReference.get()) != null) {
                view.onResult("", mList);
            }
        }
    };


    private Context mContext;

    private WeakReference<IVideoContract.IVideoView> mViewReference;
    private IVideoContract.IVideoModel mModel;

    private List<VideoBean> mList;

    private int mSortType = SORT_BY_DATE;


    @Override
    public void init(Context context, IVideoContract.IVideoView view) {
        if (context == null) {
            throw new NullPointerException("Context cannot be null");
        }
        mContext = context;
        mViewReference = new WeakReference<>(view);

        mModel = new VideoQueryMission();
        mModel.init(this);
    }

    @Override
    public void release() {
        mContext = null;

        if (mViewReference != null) {
            mViewReference.clear();
            mViewReference = null;
        }

        if (mModel != null) {
            mModel.release();
            mModel = null;
        }

        if (mList != null) {
            mList.clear();
            mList = null;
        }
    }

    @Override
    public void load() {
        if (mModel != null) {
            mModel.query(mContext);
        }
    }

    @Override
    public void sortBy(int type) {
        if (type < 0 || type > 4) {
            type = SORT_BY_ALPHABET;
        }
        mSortType = type;
        sort();
        notifyResult();
    }

    @Override
    public void refresh() {
        load();
    }

    @Override
    public void onResult(String path, List<VideoBean> list) {
        handleResult(path, list);
    }


    private void handleResult(final String path, final List<VideoBean> list) {
        mList = list;
        sort();
        notifyResult();
    }

    private void sort() {
        SortUtil.sortBy(mList, mSortType);
    }

    private void notifyResult() {
        post(NOTIFIER);
    }
}
