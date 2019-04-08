package com.albertech.demo.func.audio.mvp.impl;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.albertech.demo.func.audio.AudioBean;
import com.albertech.demo.func.audio.mvp.IAudioContract;
import com.albertech.demo.util.SortUtil;

import java.lang.ref.WeakReference;
import java.util.List;



public class AudioPresenter extends Handler implements IAudioContract.IAudioPresenter {


    private final Runnable NOTIFIER = new Runnable() {
        @Override
        public void run() {
            final IAudioContract.IAudioView view;
            if (mViewReference != null
                    && (view = mViewReference.get()) != null) {
                view.onResult("", mList);
            }
        }
    };


    private Context mContext;

    private WeakReference<IAudioContract.IAudioView> mViewReference;
    private IAudioContract.IAudioModel mModel;

    private List<AudioBean> mList;

    private int mSortType = SORT_BY_ALPHABET;


    public AudioPresenter() {
        super(Looper.getMainLooper());
    }


    @Override
    public void init(Context context, IAudioContract.IAudioView view) {
        if (context == null) {
            throw new NullPointerException("Context cannot be null");
        }
        mContext = context;
        mViewReference = new WeakReference<>(view);

        mModel = new AudioQueryMission();
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
    public void onResult(String path, List<AudioBean> list) {
        handleResult(path, list);
    }


    private void handleResult(final String path, final List<AudioBean> list) {
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
