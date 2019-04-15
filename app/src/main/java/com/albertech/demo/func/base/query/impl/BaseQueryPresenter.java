package com.albertech.demo.func.base.query.impl;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.albertech.demo.base.bean.BaseFileBean;
import com.albertech.demo.func.base.query.IBaseQueryContract;
import com.albertech.demo.util.SortUtil;

import java.lang.ref.WeakReference;
import java.util.List;


public abstract class BaseQueryPresenter<Bean extends BaseFileBean> extends Handler implements IBaseQueryContract.IBaseQueryPresenter<Bean> {


    private final Runnable NOTIFIER = new Runnable() {
        @Override
        public void run() {
            final IBaseQueryContract.IBaseQueryView view;
            if (mViewReference != null
                    && (view = mViewReference.get()) != null) {
                view.onResult("", mList);
            }
        }
    };


    private Context mContext;

    private WeakReference<IBaseQueryContract.IBaseQueryView> mViewReference;
    private IBaseQueryContract.IBaseQueryModel<Bean> mModel;

    private List<Bean> mList;

    private int mSortType = SORT_BY_ALPHABET;


    public BaseQueryPresenter() {
        super(Looper.getMainLooper());
    }


    @Override
    public void init(Context context, IBaseQueryContract.IBaseQueryView view) {
        if (context == null) {
            throw new NullPointerException("Context cannot be null");
        }
        mContext = context;
        mViewReference = new WeakReference<>(view);

        mModel = createModel();
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
    public void onResult(String path, List<Bean> list) {
        handleResult(path, list);
    }


    private void handleResult(final String path, final List<Bean> list) {
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


    protected abstract IBaseQueryContract.IBaseQueryModel<Bean> createModel();
}
