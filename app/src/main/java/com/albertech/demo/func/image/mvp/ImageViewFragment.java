package com.albertech.demo.func.image.mvp;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.albertech.demo.R;
import com.albertech.demo.func.base.IFileContract;
import com.albertech.demo.func.base.impl.BaseFileViewFragment;
import com.albertech.demo.func.image.ImageBean;
import com.albertech.demo.func.image.adapter.ImageAdapter;
import com.albertech.demo.util.Res;


public class ImageViewFragment extends BaseFileViewFragment<ImageAdapter, ImageBean> {


    @Override
    public String getTitle() {
        return Res.string(R.string.str_category_image);
    }

    @Override
    protected IFileContract.IFilePresenter<ImageBean> createPresenter() {
        return new ImagePreseneter();
    }

    @Override
    protected ImageAdapter createAdapter() {
        return new ImageAdapter(this) {
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
