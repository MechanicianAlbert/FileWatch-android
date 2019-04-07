package com.albertech.demo.func.image.mvp.impl;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.albertech.demo.R;
import com.albertech.demo.base.fragment.TitleFragment;
import com.albertech.demo.func.image.ImageBean;
import com.albertech.demo.func.image.adapter.ImageAdapter;
import com.albertech.demo.func.image.mvp.IImageContract;
import com.albertech.demo.util.Res;

import java.util.List;



public class ImageFragment extends TitleFragment implements IImageContract.IImageView {

    private final ImageAdapter ADAPTER = new ImageAdapter() {
        @Override
        public boolean onItemClick(int position, ImageBean imageBean) {
            return false;
        }
    };


    private RecyclerView mRvImage;

    private IImageContract.IImagePresenter mPresenter;


    @Override
    public String getTitle() {
        return Res.string(R.string.str_category_image);
    }

    @Override
    protected int layoutRese() {
        return R.layout.fragment_image;
    }

    @Override
    protected void initView(View root) {
        mRvImage = root.findViewById(R.id.rv_image);
        mRvImage.setLayoutManager(new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void initData() {
        mRvImage.setAdapter(ADAPTER);

        mPresenter = new ImagePreseneter();
        mPresenter.init(getContext(), this);
        mPresenter.show();
    }

    @Override
    protected void release() {
        if (mPresenter != null) {
            mPresenter.release();
            mPresenter = null;
        }
    }


    @Override
    public void onResult(String path, List<ImageBean> list) {
        ADAPTER.updateData(list);
    }

}
