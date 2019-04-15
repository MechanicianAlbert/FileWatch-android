package com.albertech.demo.func.image.mvp;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.albertech.demo.func.base.query.IBaseQueryContract;
import com.albertech.demo.func.base.query.impl.BaseQueryViewFragment;
import com.albertech.demo.func.image.ImageBean;
import com.albertech.demo.func.image.adapter.ImageAdapter;


public class ImageViewFragment extends BaseQueryViewFragment<ImageAdapter, ImageBean> {


    @Override
    protected IBaseQueryContract.IBaseQueryPresenter<ImageBean> createPresenter() {
        return new ImagePreseneter();
    }

    @Override
    protected ImageAdapter createAdapter() {
        return new ImageAdapter() {
            @Override
            public void onItemClickNotSelecting(int position, ImageBean imageBean) {

            }
        };
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false);
    }

}
