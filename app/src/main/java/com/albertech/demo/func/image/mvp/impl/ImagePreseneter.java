package com.albertech.demo.func.image.mvp.impl;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.albertech.demo.func.image.ImageBean;
import com.albertech.demo.func.image.mvp.IImageContract;
import com.albertech.demo.util.SortUtil;

import java.lang.ref.WeakReference;
import java.util.List;



public class ImagePreseneter extends Handler implements IImageContract.IImagePresenter {


    private final Runnable NOTIFIER = new Runnable() {
        @Override
        public void run() {
            final IImageContract.IImageView view;
            if (mViewReference != null
                    && (view = mViewReference.get()) != null) {
                view.onResult("", mList);
            }
        }
    };


    private Context mContext;

    private WeakReference<IImageContract.IImageView> mViewReference;
    private IImageContract.IImageModel mModel;

    private int mSortType = SORT_BY_DATE;

    private List<ImageBean> mList;


    public ImagePreseneter() {
        super(Looper.getMainLooper());
    }


    @Override
    public void init(Context context, IImageContract.IImageView view) {
        if (context == null) {
            throw new NullPointerException("Context cannot be null");
        }
        mContext = context;

        mViewReference = new WeakReference<>(view);

        mModel = new ImageQueryMission();
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
    public void show() {
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

    }

    @Override
    public void onResult(String path, List<ImageBean> list) {
        handleResult(path, list);
    }


    private void handleResult(final String path, final List<ImageBean> list) {
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
