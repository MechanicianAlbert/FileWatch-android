package com.albertech.demo.func.apk.mvp.impl;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.albertech.demo.func.apk.ApkBean;
import com.albertech.demo.func.apk.mvp.IApkContract;
import com.albertech.demo.util.SortUtil;

import java.lang.ref.WeakReference;
import java.util.List;


public class ApkPresenter extends Handler implements IApkContract.IApkPresenter {


    private final Runnable NOTIFIER = new Runnable() {
        @Override
        public void run() {
            final IApkContract.IApkView view;
            if (mViewReference != null
                    && (view = mViewReference.get()) != null) {
                view.onResult("", mList);
            }
        }
    };


    private Context mContext;

    private WeakReference<IApkContract.IApkView> mViewReference;
    private IApkContract.IApkModel mModel;

    private List<ApkBean> mList;

    private int mSortType = SORT_BY_ALPHABET;


    public ApkPresenter() {
        super(Looper.getMainLooper());
    }


    @Override
    public void init(Context context, IApkContract.IApkView view) {
        if (context == null) {
            throw new NullPointerException("Context cannot be null");
        }
        mContext = context;
        mViewReference = new WeakReference<>(view);

        mModel = new ApkQueryMission();
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
    public void onResult(String path, List<ApkBean> list) {
        handleResult(path, list);
    }


    private void handleResult(final String path, final List<ApkBean> list) {
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
