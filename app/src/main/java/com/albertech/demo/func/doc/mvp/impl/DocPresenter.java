package com.albertech.demo.func.doc.mvp.impl;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.albertech.demo.func.doc.DocBean;
import com.albertech.demo.func.doc.mvp.IDocContract;
import com.albertech.demo.util.SortUtil;

import java.lang.ref.WeakReference;
import java.util.List;


public class DocPresenter extends Handler implements IDocContract.IDocPresenter {


    private final Runnable NOTIFIER = new Runnable() {
        @Override
        public void run() {
            final IDocContract.IDocView view;
            if (mViewReference != null
                    && (view = mViewReference.get()) != null) {
                view.onResult("", mList);
            }
        }
    };


    private Context mContext;

    private WeakReference<IDocContract.IDocView> mViewReference;
    private IDocContract.IDocModel mModel;

    private List<DocBean> mList;

    private int mSortType = SORT_BY_ALPHABET;


    public DocPresenter() {
        super(Looper.getMainLooper());
    }


    @Override
    public void init(Context context, IDocContract.IDocView view) {
        if (context == null) {
            throw new NullPointerException("Context cannot be null");
        }
        mContext = context;
        mViewReference = new WeakReference<>(view);

        mModel = new DocQueryMission();
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
    public void onResult(String path, List<DocBean> list) {
        handleResult(path, list);
    }


    private void handleResult(final String path, final List<DocBean> list) {
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
