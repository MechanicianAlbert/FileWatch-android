package com.albertech.demo.func.hierarchy.mvp.impl;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.albertech.demo.func.hierarchy.HierarchyBean;
import com.albertech.demo.func.hierarchy.mvp.IHierarchyContract;
import com.albertech.demo.util.SortUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;



public class HierarchyPresenter extends Handler implements IHierarchyContract.IHierarchyPresenter {


    private final Stack<String> BACK_STACK = new Stack<>();
    private final Runnable NOTIFIER = new Runnable() {
        @Override
        public void run() {
            IHierarchyContract.IHierarchyView view;
            if (mViewReference != null
                    && (view = mViewReference.get()) != null) {
                view.onResult(BACK_STACK.peek(), mNeedShowHidden ? mAllFileList : mUnHiddenFileList);
            }
        }
    };


    private Context mContext;

    private WeakReference<IHierarchyContract.IHierarchyView> mViewReference;
    private IHierarchyContract.IHierarchyModel mModel;

    private List<HierarchyBean> mAllFileList;
    private List<HierarchyBean> mUnHiddenFileList;

    private boolean mNeedShowHidden;
    private int mSortType = SORT_BY_ALPHABET;



    public HierarchyPresenter() {
        super(Looper.getMainLooper());
    }


    @Override
    public void init(Context context, IHierarchyContract.IHierarchyView view) {
        if (context == null) {
            throw new NullPointerException("Context cannot be null");
        }
        mContext = context;

        mViewReference = new WeakReference<>(view);

        mModel = new HierarchyQueryMission();
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

        if (mAllFileList != null) {
            mAllFileList.clear();
            mAllFileList = null;
        }

        if (mUnHiddenFileList != null) {
            mUnHiddenFileList.clear();
            mUnHiddenFileList = null;
        }

        BACK_STACK.clear();
    }

    @Override
    public void loadPath(String path) {
        BACK_STACK.push(path);
        if (mModel != null) {
            mModel.query(mContext, path);
        }
    }

    @Override
    public void showHidden(boolean show) {
        mNeedShowHidden = show;
        notifyResult();
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
        loadPath(BACK_STACK.peek());
    }

    @Override
    public boolean backToParent() {
        if (BACK_STACK.size() > 1) {
            BACK_STACK.pop();
            String parentPath = BACK_STACK.pop();
            loadPath(parentPath);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onResult(String path, List<HierarchyBean> list) {
        handleQueryResult(path, list);
    }


    private void handleQueryResult(String path, List<HierarchyBean> list) {
        mUnHiddenFileList = new ArrayList<>();
        mAllFileList = list;
        if (mAllFileList != null && mAllFileList.size() > 0) {
            for (int i = 0; i < mAllFileList.size(); i++) {
                HierarchyBean bean = mAllFileList.get(i);
                if (bean != null && !bean.isHidden()) {
                    mUnHiddenFileList.add(bean);
                }
            }
        }
        sort();
        notifyResult();
    }

    private void sort() {
        SortUtil.sortBy(mAllFileList, mSortType);
        SortUtil.sortBy(mUnHiddenFileList, mSortType);
    }

    private void notifyResult() {
        post(NOTIFIER);
    }
}
