package com.albertech.demo.func.zip.mvp.impl;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.albertech.demo.R;
import com.albertech.demo.base.fragment.TitleFragment;
import com.albertech.demo.func.zip.ZipBean;
import com.albertech.demo.func.zip.adapter.ZipAdapter;
import com.albertech.demo.func.zip.mvp.IZipContract;
import com.albertech.demo.util.Res;

import java.util.List;


public class ZipFragment extends TitleFragment implements IZipContract.IZipView {

    private final ZipAdapter ADAPTER = new ZipAdapter();


    private RecyclerView mRvHierarchy;

    private IZipContract.IZipPresenter mPresenter;


    @Override
    public String getTitle() {
        return Res.string(R.string.str_title_hierarchy);
    }

    @Override
    protected int layoutRese() {
        return R.layout.fragment_doc;
    }

    @Override
    protected void initView(View root) {
        mRvHierarchy = root.findViewById(R.id.rv_doc);
        mRvHierarchy.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void initData() {
        mRvHierarchy.setAdapter(ADAPTER);

        mPresenter = new ZipPresenter();
        mPresenter.init(getContext(), this);
        mPresenter.load();
    }

    @Override
    public void onResult(String path, List<ZipBean> list) {
        ADAPTER.updateData(list);
    }

}
